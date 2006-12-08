package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author lorax
 * @version 1.0
 */
public class MultiIntervalIntersection extends HashSet<Interval>
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
				fullLeftRightMap.put(i.getLeft(), 1);
				fullLeftRightMap.put(i.getRight(), -1);
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
		}
	}
