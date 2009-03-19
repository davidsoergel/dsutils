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
 * A WeightedSet backed by a HashMap.  Note this is not thread-safe in any way (i.e., it does not use AtomicInteger and
 * so forth the way the Google Multiset does).
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class HashWeightedSet<T> implements WeightedSet<T> //extends HashMap<T, Double>
	{
	Map<T, Double> backingMap = new HashMap<T, Double>();

	private int itemCount = 0;
	private double weightSum = 0;
	// this is really an int, but we could store it as a double to avoid casting all the time in getNormalized()
	// note the itemCount is different from the sum of the weights

	public HashWeightedSet(Map<? extends T, Double> map, int items)
		{
		itemCount = items;
		for (Map.Entry<? extends T, Double> entry : map.entrySet())
			{
			add(entry.getKey(), entry.getValue());
			}
		}

	public HashWeightedSet(Map<? extends T, Double> map)
		{
		itemCount = 1;
		for (Map.Entry<? extends T, Double> entry : map.entrySet())
			{
			add(entry.getKey(), entry.getValue());
			}
		}

	public HashWeightedSet(Multiset<T> m)
		{
		itemCount = m.size();
		for (T o : m.elementSet())
			{
			add(o, m.count(o));
			}
		}

	public double getWeightSum()
		{
		return weightSum;
		}

	public HashWeightedSet()
		{
		super();
		itemCount = 0;
		}

	public void clear()
		{
		backingMap = new HashMap<T, Double>();
		itemCount = 0;
		weightSum = 0;
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

	public void addAll(WeightedSet<T> increment)
		{
		itemCount += increment.getItemCount();
		for (Map.Entry<T, Double> entry : increment.entrySet())
			{
			Double val = backingMap.get(entry.getKey());
			if (val == null)
				{
				val = 0.;
				}
			val = val + entry.getValue();
			weightSum += entry.getValue();

			backingMap.put(entry.getKey(), val);
			}
		}

	public void addAll(WeightedSet<T> increment, double weight)
		{
		itemCount += increment.getItemCount();
		for (Map.Entry<T, Double> entry : increment.entrySet())
			{
			Double val = backingMap.get(entry.getKey());
			if (val == null)
				{
				val = 0.;
				}
			final Double d = entry.getValue() * weight;
			val = val + d;
			weightSum += d;

			backingMap.put(entry.getKey(), val);
			}
		}

	public void removeAll(WeightedSet<T> increment)
		{
		itemCount -= increment.getItemCount();
		for (Map.Entry<T, Double> entry : increment.entrySet())
			{
			Double val = backingMap.get(entry.getKey());
			if (val == null)
				{
				val = 0.;
				}
			val = val - entry.getValue();
			weightSum -= entry.getValue();

			backingMap.put(entry.getKey(), val);
			}
		}

	public void add(T key, double addVal)
		{
		Double val = backingMap.get(key);

		if (val == null)
			{
			val = 0.;
			}
		val = val + addVal;
		weightSum += addVal;

		backingMap.put(key, val);
		}

	public void incrementItems()
		{
		itemCount++;
		}

	public void decrementItems()
		{
		itemCount--;
		}

	public void remove(T key, double remVal)
		{
		Double val = backingMap.get(key);

		if (val == null)
			{
			val = 0.;
			}

		val = val - remVal;
		weightSum -= remVal;

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
	public double getNormalized(T key)
		{
		return backingMap.get(key) / (double) itemCount;
		}

	public Map<T, Double> getItemNormalizedMap()
		{
		if (itemCount == 0)
			{
			throw new NoSuchElementException("Can't normalize an empty HashWeightedSet");
			}
		Map<T, Double> result = new HashMap<T, Double>(backingMap.size());
		double dEntries = (double) itemCount;
		for (Map.Entry<T, Double> entry : entrySet())
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


	public T getDominantKeyInSet(@NotNull Set<T> keys)
		{
		Map.Entry<T, Double> result = null;
		// PERF lots of different ways to do this, probably with different performance
		for (Map.Entry<T, Double> entry : entrySet())
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
		for (Map.Entry<T, Double> entry : entrySet())
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

	public double get(T s)
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
		List<Double> result = new ArrayList<Double>(backingMap.values());
		Collections.sort(result, Collections.reverseOrder());
		return result;
		}

	public SortedSet<T> keysInDecreasingWeightOrder(final Comparator secondarySort)
		{
		SortedSet<T> result = new TreeSet<T>(new Comparator<T>()
		{
		public int compare(T o1, T o2)
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

	public void multiplyBy(double multiplier)
		{
		for (Map.Entry<T, Double> entry : backingMap.entrySet())
			{
			entry.setValue(entry.getValue() * multiplier);
			}
		weightSum *= multiplier;
		}
	}
