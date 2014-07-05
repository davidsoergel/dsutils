/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * A Factory for new Iterators based on a List, where each new Iterator provides the contents in a random order.  The
 * shuffling is done in place on the underlying collection.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class PermutingCollectionIteratorFactory<T> extends CollectionIteratorFactory<T>
	{

	public PermutingCollectionIteratorFactory(List<T> underlyingList)
		{
		super(underlyingList);
		}


	public Iterator<T> next()
		{
		Collections.shuffle((List<T>) underlyingCollection);
		return super.next();
		}
	}
