/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.range;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	public <U extends Interval<T>> MultiIntervalUnion(@NotNull Set<U> intervalSet)
		{
		//private
		@NotNull SortedMap<T, Integer> fullLeftRightMap = new TreeMap<T, Integer>();
		@NotNull Set<T> includedEndpoints = new HashSet<T>();

		//int numberOfConstraints = intervalSets.size();
		//	for (Set<U> intervalSet : intervalSets)
		//		{
		for (@NotNull Interval<T> i : intervalSet)
			{
			T left = i.getMin();
			T right = i.getMax();

			Integer leftCount = fullLeftRightMap.get(left);
			fullLeftRightMap.put(left, leftCount == null ? 1 : leftCount + 1);

            // if left and right are the same then we have to modify the count twice

            Integer rightCount = fullLeftRightMap.get(right);
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
		@Nullable MutableBasicInterval<T> currentInterval = null;
		for (@NotNull Map.Entry<T, Integer> entry : fullLeftRightMap.entrySet())// the positions must be sorted!
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
