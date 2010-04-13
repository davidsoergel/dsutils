package com.davidsoergel.dsutils.tuples;

import com.davidsoergel.dsutils.range.Range;
import org.apache.commons.lang.NotImplementedException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SimpleTupleFilterStream implements TupleFilterStream
	{
	TupleStream underlyingStream;
	Map<Integer, Range> constraintsByPosition;


	public SimpleTupleFilterStream(final TupleStream underlyingStream, final Map<String, Range> constraints)
			throws TupleException
		{
		this.underlyingStream = underlyingStream;
		constraintsByPosition = mapConstraintsToPositions(underlyingStream.getDimensions(), constraints);
		}

	public static Map<Integer, Range> mapConstraintsToPositions(List<String> dimensions, Map<String, Range> constraints)
			throws TupleException
		{
		Map<Integer, Range> result = new HashMap<Integer, Range>();

		for (Map.Entry<String, Range> entry : constraints.entrySet())
			{
			int pos = dimensions.indexOf(entry.getKey());
			if (pos == -1)
				{
				throw new TupleException("Can't constrain on nonexistent dimension " + entry.getKey());
				}
			result.put(pos, entry.getValue());
			}
		return result;
		}

	public List<String> getDimensions()
		{
		return underlyingStream.getDimensions();
		}

	public Iterator<Object[]> iterator() throws TupleException
		{

		return new ConstraintIterator(underlyingStream.iterator(), constraintsByPosition);
		}

	private class ConstraintIterator implements Iterator<Object[]>
		{
		Object[] nextAccepted;
		Iterator<Object[]> dataIter;
		private Map<Integer, Range> constraintsByPosition;

		public ConstraintIterator(final Iterator<Object[]> dataIter, final Map<Integer, Range> constraintsByPosition)
			{
			this.dataIter = dataIter;
			this.constraintsByPosition = constraintsByPosition;
			loadNext();
			}

		private void loadNext()
			{
			nextAccepted = null;
			while (nextAccepted == null && dataIter.hasNext())
				{
				Object[] candidate = dataIter.next();
				for (Map.Entry<Integer, Range> entry : constraintsByPosition.entrySet())
					{
					final Object value = candidate[entry.getKey()];
					final Range range = entry.getValue();
					try
						{
						if (!range.encompassesValue(value))
							{
							// point fails the test
							candidate = null;
							break;
							}
						}
					catch (ClassCastException e)
						{
						// point fails the test
						candidate = null;
						break;
						}
					}
				// if candidate != null, point passes the test
				nextAccepted = candidate;
				}
			}


		public boolean hasNext()
			{
			return nextAccepted != null;
			}

		public Object[] next()
			{
			Object[] result = nextAccepted;
			loadNext();
			return result;
			}

		public void remove()
			{
			throw new NotImplementedException();
			}
		}
	}
