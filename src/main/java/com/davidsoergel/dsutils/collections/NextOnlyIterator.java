package com.davidsoergel.dsutils.collections;

import org.jetbrains.annotations.NotNull;

import java.util.NoSuchElementException;

/**
 * An iterator that lacks hasNext and remove.  Particularly useful for concurrency, where keeping state required for
 * hasNext makes a mess
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface NextOnlyIterator<T>
	{
	/**
	 * Returns the next object from the iterator.  Must be thread-safe, i.e. multiple threads should be able to poll
	 * simultaneously
	 */
	@NotNull
	T next() throws NoSuchElementException;
	}