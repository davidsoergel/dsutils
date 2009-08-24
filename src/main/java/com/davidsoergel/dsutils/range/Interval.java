/*
 * Copyright (c) 2001-2008 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * dev@davidsoergel.com
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the names of any contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
