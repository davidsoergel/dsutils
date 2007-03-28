package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author lorax
 * @version 1.0
 */
public class MultiIntervalIntersection extends TreeSet<Interval>
	{
	private static Logger logger = Logger.getLogger(MultiIntervalIntersection.class);

	private SortedMap<Number, Integer> fullLeftRightMap = new TreeMap<Number, Integer>();

	//private Set<LongInterval> result = new HashSet<LongInterval>();

	public <T extends Interval> MultiIntervalIntersection(Set<Set<T>> intervalSets)
		{
		int numberOfConstraints = intervalSets.size();
		for (Set<T> intervalSet : intervalSets)
			{
			for (Interval i : intervalSet)
				{
				Number left = i.getLeft();
				Number right = i.getRight();

				Integer leftCount = fullLeftRightMap.get(left);
				Integer rightCount = fullLeftRightMap.get(right);

				fullLeftRightMap.put(left, leftCount == null ? 1 : leftCount + 1);
				fullLeftRightMap.put(right, rightCount == null ? -1 : rightCount - 1);
				}
			}

		int openParens = 0;
		BasicInterval currentInterval = null;
		for (Number position : fullLeftRightMap.keySet())// the positions must be sorted!
			{
			openParens += fullLeftRightMap.get(position);
			if (currentInterval == null)
				{
				if (openParens == numberOfConstraints)
					{
					currentInterval = new BasicInterval();
					currentInterval.setLeft(position);
					}
				}
			else
				{
				assert openParens < numberOfConstraints;
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

	public boolean encompassesValue(Number value)
		{
		new BasicInterval(value, value);
		floor(value)
		}
	}
