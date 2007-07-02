/* $Id$ */

/*
 * Copyright (c) 2001-2007 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
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

package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author lorax
 * @version 1.0
 */
public class MultiIntervalIntersection<T extends Number> extends TreeSet<Interval<T>>
	{
	// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(MultiIntervalIntersection.class);

	private SortedMap<T, Integer> fullLeftRightMap = new TreeMap<T, Integer>();


	// --------------------------- CONSTRUCTORS ---------------------------

	//private Set<LongInterval> result = new HashSet<LongInterval>();

	public <U extends Interval<T>> MultiIntervalIntersection(Set<Set<U>> intervalSets)
		{
		int numberOfConstraints = intervalSets.size();
		for (Set<U> intervalSet : intervalSets)
			{
			for (Interval<T> i : intervalSet)
				{
				T left = i.getMin();
				T right = i.getMax();

				Integer leftCount = fullLeftRightMap.get(left);
				Integer rightCount = fullLeftRightMap.get(right);

				fullLeftRightMap.put(left, leftCount == null ? 1 : leftCount + 1);
				fullLeftRightMap.put(right, rightCount == null ? -1 : rightCount - 1);
				}
			}

		int openParens = 0;
		MutableBasicInterval<T> currentInterval = null;
		for (T position : fullLeftRightMap.keySet())// the positions must be sorted!
			{
			openParens += fullLeftRightMap.get(position);
			if (currentInterval == null)
				{
				if (openParens == numberOfConstraints)
					{
					currentInterval = new MutableBasicInterval<T>();
					currentInterval.setLeft(position);
					}
				}
			else
				{
				// assert openParens < numberOfConstraints;  // hogwash, each constraint may have multiple intervals
				currentInterval.setRight(position);
				if (!currentInterval.isZeroWidth())
					{
					this.add(currentInterval);
					}
				currentInterval = null;
				}
			}
		assert openParens == 0;
		}

	// -------------------------- OTHER METHODS --------------------------

	public boolean encompassesValue(T value)
		{
		//** efficient?
		try
			{
			Interval<T> f = headSet(new BasicInterval<T>(value, value)).last();
			return f.encompassesValue(value);
			}
		catch (NoSuchElementException e)
			{
			// the requested value is less than the smallest one in the set, so the headset is empty
			return false;
			}
		}
	}
