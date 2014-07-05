/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import com.google.common.collect.Multiset;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A WeightedSet backed by a HashMap.  Note this is not thread-safe in any way (i.e., it does not use AtomicInteger and
 * so forth the way the Google Multiset does).
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class ConcurrentHashWeightedSet<T> extends AbstractWeightedSet<T>
		implements MutableWeightedSet<T> //extends HashMap<T, Double>
	{

	private final Integer initialCapacity;

	//private double weightSum = 0;

	// this is really an int, but we could store it as a double to avoid casting all the time in getNormalized()
	// note the itemCount is different from the sum of the weights

	public ConcurrentHashWeightedSet(@NotNull final Map<? extends T, Double> map, final int items)
		{
		backingMap = new ConcurrentHashMap<T, Double>();
		this.initialCapacity = 16;
		itemCount = items;
		for (@NotNull final Map.Entry<? extends T, Double> entry : map.entrySet())
			{
			add(entry.getKey(), entry.getValue());
			}
		}

	public ConcurrentHashWeightedSet(@NotNull final Map<? extends T, Double> map)
		{
		backingMap = new ConcurrentHashMap<T, Double>();

		this.initialCapacity = 16;
		itemCount = 1;
		for (@NotNull final Map.Entry<? extends T, Double> entry : map.entrySet())
			{
			add(entry.getKey(), entry.getValue());
			}
		}

	public ConcurrentHashWeightedSet(@NotNull final Multiset<T> m)
		{
		backingMap = new ConcurrentHashMap<T, Double>();

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

	public ConcurrentHashWeightedSet(final int initialCapacity)
		{
		backingMap = new ConcurrentHashMap<T, Double>();

		this.initialCapacity = initialCapacity;
		itemCount = 0;
		}

	public ConcurrentHashWeightedSet()
		{
		backingMap = new ConcurrentHashMap<T, Double>();

		this.initialCapacity = 16;
		itemCount = 0;
		}

	public synchronized void clear()
		{
		backingMap = new HashMap<T, Double>(initialCapacity);

		itemCount = 0;
		//	weightSum = 0;

		}

	public void incrementItemCount(int i)
		{
		itemCount+=i;
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

	public synchronized void addAll(@NotNull final WeightedSet<T> increment)
		{
		itemCount += increment.getItemCount();
		for (@NotNull final Map.Entry<T, Double> entry : increment.entrySet())
			{
			Double val = backingMap.get(entry.getKey());
			if (val == null)
				{
				val = 0.;
				}
			val = val + entry.getValue();
			//weightSum += entry.getValue();
			T key = entry.getKey();
			assert key != null;
			backingMap.put(key, val);
			}
		}

	public synchronized void addAll(@NotNull final WeightedSet<T> increment, final double weight)
		{
		itemCount += increment.getItemCount();
		for (@NotNull final Map.Entry<T, Double> entry : increment.entrySet())
			{
			Double val = backingMap.get(entry.getKey());
			if (val == null)
				{
				val = 0.;
				}
			@NotNull final Double d = entry.getValue() * weight;
			val = val + d;
			//weightSum += d;

			T key = entry.getKey();
			assert key != null;
			backingMap.put(key, val);
			}
		}

	public synchronized void removeAll(@NotNull final WeightedSet<T> increment)
		{
		itemCount -= increment.getItemCount();
		for (@NotNull final Map.Entry<T, Double> entry : increment.entrySet())
			{
			Double val = backingMap.get(entry.getKey());
			if (val == null)
				{
				val = 0.;
				}
			val = val - entry.getValue();
			//weightSum -= entry.getValue();

			T key = entry.getKey();
			assert key != null;
			backingMap.put(key, val);
			}
		}

	private void add(@NotNull final T key, final double addVal)
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

	private void put(@NotNull final T key, final double val)
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

	public synchronized void add(@NotNull final Map<T, Double> weights)
		{
		add(weights, 1);
		}

	public synchronized void add(@NotNull final Map<T, Double> weights, final int items)
		{
		for (@NotNull final Map.Entry<T, Double> entry : weights.entrySet())
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

	public synchronized void put(@NotNull final Map<T, Double> weights)
		{
		put(weights, 1);
		}

	public synchronized void put(@NotNull final Map<T, Double> weights, final int items)
		{
		for (@NotNull final Map.Entry<T, Double> entry : weights.entrySet())
			{
			T key = entry.getKey();
			assert key != null;
			put(key, entry.getValue() * items);
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

	public synchronized void put(@NotNull final T key, final double increment, final int items)
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

	public synchronized void remove(@NotNull final T key, final double remVal)
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
