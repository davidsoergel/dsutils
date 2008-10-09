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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A WeightedSet backed by a HashMap.  Note this is not thread-safe in any way (i.e., it does not use AtomicInteger and
 * so forth the way the Google Multiset does).
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class HashWeightedSet<T> extends HashMap<T, Double> implements WeightedSet<T>
	{
	//Map<T, Double> backingMap = new HashMap<T, Double>();

	int itemCount;// this is really an int, but we could store it as a double to avoid casting all the time in getNormalized()

	public HashWeightedSet(Map<? extends T, ? extends Double> map)
		{
		super(map);
		this.itemCount = 1;
		}


	public HashWeightedSet()
		{
		super();
		this.itemCount = 0;
		}

	public void addAll(WeightedSet<T> increment)
		{
		itemCount += increment.getItemCount();
		for (Map.Entry<T, Double> entry : increment.entrySet())
			{
			Double val = get(entry.getKey());
			if (val == null)
				{
				val = 0.;
				}
			val = val + entry.getValue();

			put(entry.getKey(), val);
			}
		}

	public void removeAll(WeightedSet<T> increment)
		{
		itemCount -= increment.getItemCount();
		for (Map.Entry<T, Double> entry : increment.entrySet())
			{
			Double val = get(entry.getKey());
			if (val == null)
				{
				val = 0.;
				}
			val = val - entry.getValue();

			put(entry.getKey(), val);
			}
		}

	public void add(T key, double addVal)
		{
		itemCount++;

		Double val = get(key);

		if (val == null)
			{
			val = 0.;
			}
		val = val + addVal;

		put(key, val);
		}


	public void remove(T key, double remVal)
		{
		itemCount++;

		Double val = get(key);

		if (val == null)
			{
			val = 0.;
			}

		val = val - remVal;

		put(key, val);
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
		return get(key) / (double) itemCount;
		}

	public Map<T, Double> getNormalizedMap()
		{
		Map<T, Double> result = new HashMap<T, Double>(this.size());
		double dEntries = (double) itemCount;
		for (Map.Entry<T, Double> entry : entrySet())
			{
			result.put(entry.getKey(), entry.getValue() / dEntries);
			}
		return result;
		}

	public int getItemCount()
		{
		return itemCount;
		}

	public Map.Entry<T, Double> getDominantEntryInSet(Set<T> mutuallyExclusiveLabels)
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
		}
	}
