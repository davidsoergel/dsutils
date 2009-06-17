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

package com.davidsoergel.dsutils.collections;

import com.google.common.collect.Multiset;
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
import java.util.concurrent.ConcurrentHashMap;

/**
 * A WeightedSet backed by a HashMap.  Note this is not thread-safe in any way (i.e., it does not use AtomicInteger and
 * so forth the way the Google Multiset does).
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class HashWeightedSet<T> implements WeightedSet<T> //extends HashMap<T, Double>
	{
	private Map<T, Double> backingMap = new ConcurrentHashMap<T, Double>();

	private int itemCount = 0;

	//private double weightSum = 0;

	// this is really an int, but we could store it as a double to avoid casting all the time in getNormalized()
	// note the itemCount is different from the sum of the weights

	public HashWeightedSet(final Map<? extends T, Double> map, final int items)
		{
		this.initialCapacity = 16;
		itemCount = items;
		for (final Map.Entry<? extends T, Double> entry : map.entrySet())
			{
			add(entry.getKey(), entry.getValue());
			}
		}

	public HashWeightedSet(final Map<? extends T, Double> map)
		{
		this.initialCapacity = 16;
		itemCount = 1;
		for (final Map.Entry<? extends T, Double> entry : map.entrySet())
			{
			add(entry.getKey(), entry.getValue());
			}
		}

	public HashWeightedSet(final Multiset<T> m)
		{
		this.initialCapacity = 16;
		itemCount = m.size();
		for (final T o : m.elementSet())
			{
			add(o, m.count(o));
			}
		}

	/*public double getWeightSum()
		{
		return weightSum;
		}
*/
	private final Integer initialCapacity;

	public HashWeightedSet(final int initialCapacity)
		{
		this.initialCapacity = initialCapacity;
		itemCount = 0;
		}

	public HashWeightedSet()
		{
		this.initialCapacity = 16;
		itemCount = 0;
		}

	public synchronized void clear()
		{
		backingMap = new HashMap<T, Double>(initialCapacity);

		itemCount = 0;
		//	weightSum = 0;

		}

	/*	@Override
	 public Double put(T key, Double value)
		 {
		 if (backingMap.containsKey(key))
			 {
			 itemCount--;
			 }
		 Double result = backingMap.put(key, value);
		 itemCount++;
		 return result;
		 }
 */

	/*	@Override
	 public void putAll(Map<? extends T, ? extends Double> m)
		 {
		 super.putAll(m);
		 }
 */

	public synchronized void addAll(final WeightedSet<T> increment)
		{
		itemCount += increment.getItemCount();
		for (final Map.Entry<T, Double> entry : increment.entrySet())
			{
			Double val = backingMap.get(entry.getKey());
			if (val == null)
				{
				val = 0.;
				}
			val = val + entry.getValue();
			//weightSum += entry.getValue();

			backingMap.put(entry.getKey(), val);
			}
		}

	public synchronized void addAll(final WeightedSet<T> increment, final double weight)
		{
		itemCount += increment.getItemCount();
		for (final Map.Entry<T, Double> entry : increment.entrySet())
			{
			Double val = backingMap.get(entry.getKey());
			if (val == null)
				{
				val = 0.;
				}
			final Double d = entry.getValue() * weight;
			val = val + d;
			//weightSum += d;

			backingMap.put(entry.getKey(), val);
			}
		}

	public synchronized void removeAll(final WeightedSet<T> increment)
		{
		itemCount -= increment.getItemCount();
		for (final Map.Entry<T, Double> entry : increment.entrySet())
			{
			Double val = backingMap.get(entry.getKey());
			if (val == null)
				{
				val = 0.;
				}
			val = val - entry.getValue();
			//weightSum -= entry.getValue();

			backingMap.put(entry.getKey(), val);
			}
		}

	private void add(final T key, final double addVal)
		{
		Double val = backingMap.get(key);

		if (val == null)
			{
			val = 0.;
			}
		val = val + addVal;
		//weightSum += addVal;

		backingMap.put(key, val);
		}

	private void put(final T key, final double val)
		{
		backingMap.put(key, val);
		}
/*
	public void incrementItems()
		{
		itemCount++;
		}

	public void decrementItems()
		{
		itemCount--;
		}
		*/

	public synchronized void add(final Map<T, Double> weights)
		{
		add(weights, 1);
		}

	public synchronized void add(final Map<T, Double> weights, final int items)
		{
		for (final Map.Entry<T, Double> entry : weights.entrySet())
			{
			add(entry.getKey(), entry.getValue() * items);
			}

		itemCount += items;
		}

	public synchronized void add(final T key, final double increment, final int items)
		{
		add(key, increment * items);
		itemCount += items;
		}

	public synchronized void put(final Map<T, Double> weights)
		{
		put(weights, 1);
		}

	public synchronized void put(final Map<T, Double> weights, final int items)
		{
		for (final Map.Entry<T, Double> entry : weights.entrySet())
			{
			put(entry.getKey(), entry.getValue() * items);
			}
		if (itemCount == 0)
			{
			itemCount = items;
			}
		else if (itemCount != items)
			{
			throw new CollectionRuntimeException("Can't update a HashWeightedSet with a different number of items");
			}
		}

	public synchronized void put(final T key, final double increment, final int items)
		{
		put(key, increment * items);
		if (itemCount == 0)
			{
			itemCount = items;
			}
		else if (itemCount != items)
			{
			throw new CollectionRuntimeException("Can't update a HashWeightedSet with a different number of items");
			}
		}

	public synchronized void remove(final T key, final double remVal)
		{
		Double val = backingMap.get(key);

		if (val == null)
			{
			val = 0.;
			}

		val = val - remVal;
		//weightSum -= remVal;

		backingMap.put(key, val);
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

	public synchronized WeightedSet<T> extractWithKeys(final Collection<T> okKeys)
		{
		// leave the item count the same
		return new HashWeightedSet<T>(limitBackingMap(okKeys), itemCount);
		}

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

	// dangerous: doesn't update itemCount
	/*
	public void multiplyBy(double multiplier)
		{
		for (Map.Entry<T, Double> entry : backingMap.entrySet())
			{
			entry.setValue(entry.getValue() * multiplier);
			}
		//weightSum *= multiplier;
		}*/
	}
