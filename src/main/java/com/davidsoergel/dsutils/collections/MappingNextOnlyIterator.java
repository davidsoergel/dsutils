/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import org.jetbrains.annotations.NotNull;

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

	@NotNull
	public J next() throws NoSuchElementException
		{
		return function(i.next());
		}

	@NotNull
	public abstract J function(T t);
	}
