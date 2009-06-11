package com.davidsoergel.dsutils.collections;

import java.util.NoSuchElementException;

/**
 * An Iterator that maps elements from an underlying iterator through some function on the fly.  Easily extended as an
 * anonymous class.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class MappingNextOnlyIterator<T, J> implements NextOnlyIterator<J>
	{
	NextOnlyIterator<T> i;

	public MappingNextOnlyIterator(NextOnlyIterator<T> i)
		{
		this.i = i;
		}

	public J next() throws NoSuchElementException
		{
		return function(i.next());
		}

	public abstract J function(T t);
	}
