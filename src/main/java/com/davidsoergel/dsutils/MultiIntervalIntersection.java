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

	private SortedMap<Double, Integer> fullLeftRightMap = new TreeMap<Double, Integer>();

	//private Set<Interval> result = new HashSet<Interval>();

	public MultiIntervalIntersection(Set<Set<Interval>> intervalSets)
		{
		int numberOfConstraints = intervalSets.size();
		for (Set<Interval> intervalSet : intervalSets)
			{
			for (Interval i : intervalSet)
				{
				fullLeftRightMap.put(i.getLeft(), 1);
				fullLeftRightMap.put(i.getRight(), -1);
				}
			}

		int openParens = 0;
		BasicDoubleInterval currentInterval = null;
		for (Double position : fullLeftRightMap.keySet())// the positions must be sorted!
			{
			openParens += fullLeftRightMap.get(position);
			if (currentInterval == null)
				{
				if (openParens == numberOfConstraints)
					{
					currentInterval = new BasicDoubleInterval();
					currentInterval.setLeft(position);
					}
				}
			else
				{
				assert openParens < numberOfConstraints;
				currentInterval.setRight(position);
				this.add(currentInterval);
				currentInterval = null;
				}
			}
		}
	}
