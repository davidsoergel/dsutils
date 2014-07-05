/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.range;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

	public <U extends Interval<T>> MultiIntervalIntersection(@NotNull Set<Set<U>> possiblyOverlappingIntervalSets)
		{
		@NotNull Set<Set<U>> intervalSets = new HashSet<Set<U>>();

		// guarantee that each constraint contains no internal overlaps
		for (@NotNull Set<U> intervalSet : possiblyOverlappingIntervalSets)
			{
			intervalSets.add(new MultiIntervalUnion(intervalSet));
			}

		//private
		@NotNull SortedMap<T, Integer> fullLeftRightMap = new TreeMap<T, Integer>();
		//SortedMap<T, Integer> closedLeftRightMap = new TreeMap<T, Integer>();
		//Set<T> allEndpoints = new HashSet<T>();

		//	SortedMap<T, Integer> openEndpointMap = new TreeMap<T, Integer>();

		@NotNull Set<T> excludedEndpoints = new HashSet<T>();
		Multiset<T> includedEndpoints = HashMultiset.create();

		int numberOfConstraints = intervalSets.size();
		for (@NotNull Set<U> intervalSet : intervalSets)
			{
			for (@NotNull Interval<T> i : intervalSet)
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
		@Nullable MutableBasicInterval<T> currentInterval = null;
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
