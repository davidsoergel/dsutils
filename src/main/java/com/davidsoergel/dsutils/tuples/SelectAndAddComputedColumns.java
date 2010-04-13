package com.davidsoergel.dsutils.tuples;

import com.google.common.base.Function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Combining a select and a function step allows us use columns as function inputs that are not included in the outputs.
 * To get the same effect with a compute-only TupleFunction would require a subsequent filter; this ought to be a little
 * faster, since it doesn't require allocating the intermediate Object[]
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SelectAndAddComputedColumns extends SimpleTupleFunction
	{
	/**
	 * @param fromDimensions
	 * @param toDimensions    dimensions that should be selected from the input, not including the computed columns
	 * @param computedColumns
	 * @throws TupleException
	 */
	public SelectAndAddComputedColumns(final List<String> fromDimensions, final List<String> toDimensions,
	                                   final Map<String, Function<Object[], Object>> computedColumns)
			throws TupleException
		{
		this.fromDimensions = fromDimensions;
		this.toDimensions = toDimensions;

		final int selectToSize = toDimensions.size();

		final int[] selectPositionMap = new int[selectToSize];

		// prepare the selects

		int pos = 0;
		for (String toDimension : toDimensions)
			{
			final int fromIndex = fromDimensions.indexOf(toDimension);
			if (fromIndex < 0)
				{
				throw new TupleException("Dimension not found in input: " + toDimension);
				}
			selectPositionMap[pos] = fromIndex;
			pos++;
			}

		// prepare the functions; we'll add these onto the end of the tuples

		final Map<Integer, Function<Object[], Object>> functionMap = new HashMap<Integer, Function<Object[], Object>>();
		for (Map.Entry<String, Function<Object[], Object>> entry : computedColumns.entrySet())
			{
			final String toDimension = entry.getKey();
			toDimensions.add(toDimension);
			functionMap.put(pos, entry.getValue());
			pos++;
			}


		final int toTotalSize = toDimensions.size();

		function = new Function<Object[], Object[]>()
		{
		public Object[] apply(final Object[] from)
			{
			Object[] result = new Object[toTotalSize];
			for (int i = 0; i < selectToSize; i++)
				{
				result[i] = from[selectPositionMap[i]];
				}
			for (Map.Entry<Integer, Function<Object[], Object>> entry : functionMap.entrySet())
				{
				result[entry.getKey()] = entry.getValue().apply(from);
				}
			return result;
			}
		};
		}
	}
