package com.davidsoergel.dsutils.tuples;

import com.google.common.base.Function;

import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SelectColumns extends SimpleTupleFunction
	{

	public SelectColumns(final List<String> fromDimensions, final List<String> toDimensions) throws TupleException
		{
		this.fromDimensions = fromDimensions;
		this.toDimensions = toDimensions;

		final int toSize = toDimensions.size();

		final int[] selectPositionMap = new int[toSize];

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

		function = new Function<Object[], Object[]>()
		{
		public Object[] apply(final Object[] from)
			{
			Object[] result = new Object[toSize];
			for (int i = 0; i < toSize; i++)
				{
				result[i] = from[selectPositionMap[i]];
				}
			return result;
			}
		};
		}
	}
