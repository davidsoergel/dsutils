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

			backingMap.put(entry.getKey(), val);
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

			backingMap.put(entry.getKey(), val);
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
