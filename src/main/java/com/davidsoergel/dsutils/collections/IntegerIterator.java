package com.davidsoergel.dsutils.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class IntegerIterator implements Iterator<Integer>
	{
	int trav;
	final int maxExclusive;

	public IntegerIterator(final int minInclusive, final int maxExclusive)
		{
		trav = minInclusive;
		this.maxExclusive = maxExclusive;
		}

	public boolean hasNext()
		{
		return trav < maxExclusive;
		}

	public Integer next()
		{
		if (!hasNext())
			{
			throw new NoSuchElementException();
			}
		final int result = trav;
		trav++;
		return result;
		}

	public void remove()
		{
		throw new UnsupportedOperationException();
		}
	}
