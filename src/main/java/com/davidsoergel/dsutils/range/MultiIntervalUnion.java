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

import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @version $Id$
 */
public class MultiIntervalUnion<T extends Number & Comparable> extends TreeSet<Interval<T>>
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(MultiIntervalIntersection.class);


	// --------------------------- CONSTRUCTORS ---------------------------

	//private Set<LongInterval> result = new HashSet<LongInterval>();

	/**
	 * Find the minimal set of intervals that spans exactly the space given by the input set.  Merges overlapping
	 * intervals, as well as immeditely adjacent intervals (unless both endpoints are open).
	 *
	 * @param intervalSet
	 * @return
	 */
	public <U extends Interval<T>> MultiIntervalUnion(Set<U> intervalSet)
		{
		//private
		SortedMap<T, Integer> fullLeftRightMap = new TreeMap<T, Integer>();
		Set<T> includedEndpoints = new HashSet<T>();

		//int numberOfConstraints = intervalSets.size();
		//	for (Set<U> intervalSet : intervalSets)
		//		{
		for (Interval<T> i : intervalSet)
			{
			T left = i.getMin();
			T right = i.getMax();

			Integer leftCount = fullLeftRightMap.get(left);
			Integer rightCount = fullLeftRightMap.get(right);

			fullLeftRightMap.put(left, leftCount == null ? 1 : leftCount + 1);
			fullLeftRightMap.put(right, rightCount == null ? -1 : rightCount - 1);

			// if any bound in ever inclusive, that overrides any exclusive bound at the same point
			if (i.isClosedLeft())
				{
				includedEndpoints.add(left);
				}
			if (i.isClosedRight())
				{
				includedEndpoints.add(right);
				}
			}
		//		}

		int openParens = 0;
		MutableBasicInterval<T> currentInterval = null;
		for (Map.Entry<T, Integer> entry : fullLeftRightMap.entrySet())// the positions must be sorted!
			{
			T position = entry.getKey();
			Integer parenDelta = entry.getValue();
			if (parenDelta != 0)
				{
				openParens += parenDelta;
				if (currentInterval == null)
					{
					if (openParens > 0)
						{
						currentInterval = new MutableBasicInterval<T>();
						currentInterval.setLeft(position);
						currentInterval.setClosedLeft(includedEndpoints.contains(position));
						}
					}
				else
					{
					if (openParens == 0)
						{
						currentInterval.setRight(position);
						currentInterval.setClosedRight(includedEndpoints.contains(position));
						//if (!currentInterval.isZeroWidth())
						//	{
						add(currentInterval);
						//	}
						currentInterval = null;
						}
					}
				}
			else if (!includedEndpoints.contains(position))
				{
				// there is an excluded point
				assert currentInterval != null;

				currentInterval.setRight(position);
				currentInterval.setClosedRight(false);

				add(currentInterval);


				currentInterval = new MutableBasicInterval<T>();
				currentInterval.setLeft(position);
				currentInterval.setClosedLeft(false);
				}
			}
		assert openParens == 0;
		}

	// -------------------------- OTHER METHODS --------------------------

	public boolean encompassesValue(T value)
		{
		// efficient?
		try
			{
			Interval<T> f = headSet(new BasicInterval<T>(value, value, false, false)).last();
			return f.encompassesValue(value);
			}
		catch (NoSuchElementException e)
			{
			// the requested value is less than the smallest one in the set, so the headset is empty
			return false;
			}
		}
	}