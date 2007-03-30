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
	private static Logger logger = Logger.getLogger(MultiIntervalIntersection.class);

	private SortedMap<T, Integer> fullLeftRightMap = new TreeMap<T, Integer>();

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
