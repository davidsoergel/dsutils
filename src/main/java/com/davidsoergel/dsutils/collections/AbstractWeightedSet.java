package com.davidsoergel.dsutils.collections;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class AbstractWeightedSet<T> implements WeightedSet<T>
	{
	protected Map<T, Double> backingMap;
	protected int itemCount = 0;

	public Map<T, Double> getMap()
		{
		return backingMap;
		}

	public synchronized SortedSet<T> keysInDecreasingWeightOrder(final Comparator<T> secondarySort)
		{
		final SortedSet<T> result = new TreeSet<T>(new Comparator<T>()
		{
		public int compare(final T o1, final T o2)
			{
			int result = -backingMap.get(o1).compareTo(backingMap.get(o2));
			if (result == 0 && secondarySort != null)
				{
				result = secondarySort.compare(o1, o2);
				//	result = o1.compareTo(o2);
				}
			return result;
			}
		});
		result.addAll(backingMap.keySet());
		return result;
		}

	/**
	 * Given a key, returns the mean of the values provided for that key so far, normalized by the total number of entries
	 * (e.g., the number of addAll() calls).  If addAll() was called and provided no value for the requested key, that is
	 * considered as providing the value "0", and so does reduce the mean.
	 *
	 * @param key
	 * @return
	 */
	public synchronized double getNormalized(final T key)
		{
		return backingMap.get(key) / (double) itemCount;
		}

	public synchronized
	@NotNull
	Map<T, Double> getItemNormalizedMap()
		{
		if (itemCount == 0)
			{
			throw new NoSuchElementException("Can't normalize an empty HashWeightedSet");
			}
		final Map<T, Double> result = new HashMap<T, Double>(backingMap.size());
		final double dEntries = (double) itemCount;
		for (final Map.Entry<T, Double> entry : entrySet())
			{
			result.put(entry.getKey(), entry.getValue() / dEntries);
			}
		return result;
		}

	/*
	 public Map<T, Double> getBackingMap()
	 {

	 }
 */
	public int getItemCount()
		{
		return itemCount;
		}

	/*public Map.Entry<T, Double> getDominantEntryInSet(Set<T> mutuallyExclusiveLabels)
		{
		Map.Entry<T, Double> result = null;
		// PERF lots of different ways to do this, probably with different performance
		for (Map.Entry<T, Double> entry : entrySet())
			{
			if (mutuallyExclusiveLabels.contains(entry.getKey()))
				{
				if (result == null || entry.getValue() > result.getValue())
					{
					result = entry;
					}
				}
			}
		return result;
		}*/


	public T getDominantKeyInSet(@NotNull final Set<T> keys)
		{
		Map.Entry<T, Double> result = null;

		//Sets.intersection(keySet(), keys);

		// PERF lots of different ways to do this, probably with different performance
		for (final Map.Entry<T, Double> entry : entrySet())
			{
			if (keys.contains(entry.getKey()))
				{
				if (result == null || entry.getValue() > result.getValue())
					{
					result = entry;
					}
				}
			}
		if (result == null)
			{
			throw new NoSuchElementException();
			}
		return result.getKey();
		}


	public T getDominantKey()
		{
		Map.Entry<T, Double> result = null;
		// PERF lots of different ways to do this, probably with different performance
		for (final Map.Entry<T, Double> entry : entrySet())
			{
			if (result == null || entry.getValue() > result.getValue())
				{
				result = entry;
				}
			}
		if (result == null)
			{
			throw new NoSuchElementException();
			}
		return result.getKey();
		}

	public Set<Map.Entry<T, Double>> entrySet()
		{
		return backingMap.entrySet();
		}

	public double get(final T s)
		{
		final Double d = backingMap.get(s);

		return d == null ? 0.0 : d;
		}

	public Set<T> keySet()
		{
		return backingMap.keySet();
		}

	public boolean isEmpty()
		{
		return backingMap.isEmpty();
		}

	public SortedSet<T> keysInDecreasingWeightOrder()
		{
		return keysInDecreasingWeightOrder(null);
		}

	public List<Double> weightsInDecreasingOrder()
		{
		final List<Double> result = new ArrayList<Double>(backingMap.values());
		Collections.sort(result, Collections.reverseOrder());
		return result;
		}

	// dangerous: not immutable

	/*	public void retainKeys(Collection<T> okKeys)
		 {
		 backingMap = limitBackingMap(okKeys);
		 // leave the item count the same
		 }
 */

	private synchronized Map<T, Double> limitBackingMap(final Collection<T> okKeys)
		{
		final Map<T, Double> limitedMap = new HashMap<T, Double>();

		for (final T okKey : okKeys)
			{
			final Double val = backingMap.get(okKey);
			if (val != null && !val.equals(0.0))
				{
				limitedMap.put(okKey, val);
				}
			}
		return limitedMap;
		}

	public synchronized WeightedSet<T> extractWithKeys(final Collection<T> okKeys)
		{
		// leave the item count the same
		return new ConcurrentHashWeightedSet<T>(limitBackingMap(okKeys), itemCount);
		}

	@Override
	public String toString()
		{
		return backingMap.toString();
		}
	}
