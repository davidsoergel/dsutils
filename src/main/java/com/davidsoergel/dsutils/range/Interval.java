/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.range;


/**
 * An interval on a number line of type T.  Might better be called ContinuousRange<T>?
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public interface Interval<T extends Number & Comparable>
		extends Comparable<Interval<T>>, Range<T>//extends HierarchyNode
	{
	// -------------------------- OTHER METHODS --------------------------

	// this is a little questionable because Numbers are not necessarily Comparable, but let's just assume they are.

	/**
	 * Tells whether the given value is contained within the interval or not.
	 *
	 * @param value
	 * @return true if the value is within the interval (including the endpoints, if they are closed); false otherwise.
	 */
	boolean encompassesValue(T value);


	/**
	 * Returns the maximum value (the right endpoint) of the interval.
	 *
	 * @return the maximum value (the right endpoint) of the interval.
	 */
	T getMax();

	/**
	 * Returns the minimum value (the left endpoint) of the interval.
	 *
	 * @return the minimum value (the left endpoint) of the interval.
	 */
	T getMin();

	/**
	 * Tells whether the minimum value is to be considered inclusive or exclusive.
	 *
	 * @return true if the minimum value is inclusive; false otherwise.
	 */
	boolean isClosedLeft();

	/**
	 * Tells whether the maximum value is to be considered inclusive or exclusive.
	 *
	 * @return true if the maximum value is inclusive; false otherwise.
	 */
	boolean isClosedRight();
	}
