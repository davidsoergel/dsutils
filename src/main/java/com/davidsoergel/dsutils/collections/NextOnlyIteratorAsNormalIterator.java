/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import org.apache.commons.lang.NotImplementedException;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class NextOnlyIteratorAsNormalIterator<T> implements Iterator<T>
	{
	NextOnlyIterator<T> it;

	public NextOnlyIteratorAsNormalIterator(final NextOnlyIterator<T> it)
		{
		this.it = it;
		}

	private boolean hasNext = true;

	public boolean hasNext()
		{
		return hasNext;
		}

	@NotNull
	public T next()
		{
		try
			{
			return it.next();
			}
		catch (NoSuchElementException e)
			{
			hasNext = false;
			throw e;
			}
		}

	public void remove()
		{
		throw new NotImplementedException();
		}
	}
