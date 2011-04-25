package com.davidsoergel.dsutils.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

/**
 * An Iterator that maps elements from an underlying iterator through some function on the fly.  Easily extended as an
 * anonymous class.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class MappingIterator<T, J> implements Iterator<J>
	{
	Iterator<T> i;

	public MappingIterator(@NotNull Iterable<T> it)
		{
		this.i = it.iterator();
		}

	public MappingIterator(Iterator<T> i)
		{
		this.i = i;
		}

	public boolean hasNext()
		{
		return i.hasNext();
		}

	@NotNull
	public J next()
		{
		return function(i.next());
		}

	@NotNull
	public abstract J function(T t);

	public void remove()
		{
		// not implemented
		}
	}
