/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import org.apache.commons.lang.NotImplementedException;

import java.util.Collection;
import java.util.Iterator;


/**
 * A Factory for new Iterators based on a Collection.  Each provided Iterator is a new, independent object, iterating in
 * whatever order the underlying Collection provides (which may or may not be defined).
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class CollectionIteratorFactory<T> implements Iterator<Iterator<T>>
	{
	protected Collection<T> underlyingCollection;

	public CollectionIteratorFactory(Collection<? extends T> underlyingCollection)
		{
		this.underlyingCollection = (Collection<T>) underlyingCollection;
		}

	public boolean hasNext()
		{
		return true;
		}

	public Iterator<T> next()
		{
		return underlyingCollection.iterator();
		}

	public void remove()
		{
		throw new NotImplementedException();
		}
	}
