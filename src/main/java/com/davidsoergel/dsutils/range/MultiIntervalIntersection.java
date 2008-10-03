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

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @version $Id$
 */
public class MultiIntervalIntersection<T extends Number & Comparable> extends TreeSet<Interval<T>>
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(MultiIntervalIntersection.class);


	// --------------------------- CONSTRUCTORS ---------------------------

	//private Set<LongInterval> result = new HashSet<LongInterval>();

	public <U extends Interval<T>> MultiIntervalIntersection(Set<Set<U>> possiblyOverlappingIntervalSets)
		{
		Set<Set<U>> intervalSets = new HashSet<Set<U>>();

		// guarantee that each constraint contains no internal overlaps
		for (Set<U> intervalSet : possiblyOverlappingIntervalSets)
			{
			intervalSets.add(new MultiIntervalUnion(intervalSet));
			}

		//private
		SortedMap<T, Integer> fullLeftRightMap = new TreeMap<T, Integer>();
		//SortedMap<T, Integer> closedLeftRightMap = new TreeMap<T, Integer>();
		//Set<T> allEndpoints = new HashSet<T>();

		//	SortedMap<T, Integer> openEndpointMap = new TreeMap<T, Integer>();

		Set<T> excludedEndpoints = new HashSet<T>();
		Multiset<T> includedEndpoints = new HashMultiset<T>();

		int numberOfConstraints = intervalSets.size();
		for (Set<U> intervalSet : intervalSets)
			{
			for (Interval<T> i : intervalSet)
				{
				T left = i.getMin();
				T right = i.getMax();

				Integer leftCount = fullLeftRightMap.get(left);
				fullLeftRightMap.put(left, leftCount == null ? 1 : leftCount + 1);

				Integer rightCount = fullLeftRightMap.get(right);
				fullLeftRightMap.put(right, rightCount == null ? -1 : rightCount - 1);

				if (i.isClosedLeft())
					{
					includedEndpoints.add(left);
					}
				else

					{
					excludedEndpoints.add(left);
					}

				if (i.isClosedRight())
					{
					includedEndpoints.add(right);
					}
				else
					{
					excludedEndpoints.add(right);
					}
				}
			}


		int openParens = 0;
		MutableBasicInterval<T> currentInterval = null;
		for (T position : fullLeftRightMap.keySet())// the positions must be sorted!
			{
			/*Integer openDelta = openLeftRightMap.get(position);
			int openDeltaI = openDelta == null ? 0 : openDelta;

			Integer closedDelta = closedLeftRightMap.get(position);
			int closedDeltaI = closedDelta == null ? 0 : closedDelta;
*/
			int parenDelta = fullLeftRightMap.get(position);
			//openDeltaI + closedDeltaI;

			if (parenDelta != 0)//alternatively, could remove these entries from the map first
				{
				openParens += parenDelta;
				if (currentInterval == null)
					{
					if (openParens == numberOfConstraints)
						{
						currentInterval = new MutableBasicInterval<T>();
						currentInterval.setLeft(position);
						currentInterval.setClosedLeft(!excludedEndpoints.contains(position));
						}
					}
				else
					{
					assert openParens < numberOfConstraints;
					// note we guaranteed previously that each constraint contains no overlaps

					currentInterval.setRight(position);
					currentInterval.setClosedRight(!excludedEndpoints.contains(position));


					//if (!currentInterval.isZeroWidth())
					//	{
					add(currentInterval);
					//	}
					currentInterval = null;
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

		// include any remaining closed endpoints that are not already included, by adding a zero-width interval

		for (T position : includedEndpoints)
			{
			// the only points we're interested in are those that are closed endpoints in every constraint.
			// note we guaranteed previously that each constraint contains no overlaps, so includedEndpoints.count(position)
			// should contain at most one count for each constraint.

			// if all of the constraints share a closed left or right endpoint, then the postition is already encompassed.
			// this is fr points that are sometimes closed right and sometimes closed left

			if (includedEndpoints.count(position) == numberOfConstraints && !encompassesValue(position))
				{
				currentInterval = new MutableBasicInterval<T>();
				currentInterval.setLeft(position);
				currentInterval.setClosedLeft(true);
				currentInterval.setRight(position);
				currentInterval.setClosedRight(true);
				add(currentInterval);
				}
			}
		}


	// -------------------------- OTHER METHODS --------------------------

	public final boolean encompassesValue(T value)
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
