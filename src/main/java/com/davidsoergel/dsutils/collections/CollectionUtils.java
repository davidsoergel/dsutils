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

import com.davidsoergel.dsutils.ArrayUtils;
import com.davidsoergel.dsutils.math.MersenneTwisterFast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author lorax
 * @version 1.0
 */
public class CollectionUtils extends org.apache.commons.collections15.CollectionUtils
	{
	// -------------------------- STATIC METHODS --------------------------

	/**
	 * Shallow-copy any Collection into an ArrayList.  Useful for making a local copy for iteration, to avoid
	 * ConcurrentModificationExceptions.
	 */
	public static <T> ArrayList<T> ArrayList(Collection<T> c)
		{
		if (c instanceof ArrayList)
			{
			return (ArrayList<T>) c;
			}

		return new ArrayList<T>(c);
		}

	public static ArrayList<Double> plus(List<Number> a, List<Number> b)
		{
		if (a.size() != b.size())
			{
			throw new IndexOutOfBoundsException("Can't add arrays of different sizes");
			}
		ArrayList<Double> result = new ArrayList<Double>(a.size());


		for (int i = 0; i < a.size(); i++)
			{
			//logger.debug("Adding cells: " + i + ", " + j);
			result.add(a.get(i).doubleValue() + b.get(i).doubleValue());
			}
		return result;
		}

	public static ArrayList<Double> minus(List<Number> a, List<Number> b)
		{
		if (a.size() != b.size())
			{
			throw new IndexOutOfBoundsException("Can't add arrays of different sizes");
			}
		ArrayList<Double> result = new ArrayList<Double>(a.size());


		for (int i = 0; i < a.size(); i++)
			{
			//logger.debug("Adding cells: " + i + ", " + j);
			result.add(a.get(i).doubleValue() - b.get(i).doubleValue());
			}
		return result;
		}

	public static boolean deepEqualsWithinFPError(Set<Double> a, Set<Double> b)
		{
		Double[] aa = a.toArray(new Double[]{});
		Double[] bb = b.toArray(new Double[]{});
		Arrays.sort(aa);
		Arrays.sort(bb);
		return ArrayUtils.equalWithinFPError(aa, bb);
		}

	public static void retainRandom(List list, int numElements)
		{
		//inefficient...?
		while (list.size() > numElements)
			{
			list.remove(MersenneTwisterFast.randomInt(list.size()));
			}
		}


	public static void retainRandom(Collection set, int numElements)
		{
		//inefficient...?
		List list = new LinkedList(set);
		while (set.size() > numElements)
			{
			int pos = MersenneTwisterFast.randomInt(set.size());
			set.remove(list.get(pos));
			list.remove(pos);
			}
		}


	public static void retainRandom(Map map, int numElements)
		{
		//inefficient...?
		List list = new LinkedList(map.keySet());
		while (map.size() > numElements)
			{
			int pos = MersenneTwisterFast.randomInt(map.size());
			map.remove(list.get(pos));
			list.remove(pos);
			}
		}

	public static <K, V> Collection<V> mapAll(Map<K, V> m, Iterable<K> keys)
		{
		Set result = new HashSet<V>();
		for (K key : keys)
			{
			result.add(m.get(key));
			}
		return result;
		}

	public static <K, V> Collection<V> mapAllIgnoringNulls(Map<K, V> m, Iterator<? extends K> keys)
		{
		Set result = new HashSet<V>();
		while (keys.hasNext())
			{
			V value = m.get(keys.next());
			if (value != null)
				{
				result.add(value);
				}
			}
		return result;
		}


	public static <T> boolean allFirstElementsEqual(Set<List<T>> theLists)
		{
		Object o = null;
		if (theLists.isEmpty())
			{
			return false;
			}
		for (List l : theLists)
			{
			if (l.isEmpty())
				{
				return false;
				}
			if (o != null)
				{
				if (!o.equals(l.get(0)))
					{
					return false;
					}
				}
			else
				//if(o == null)
				{
				o = l.get(0);
				}

			if (o == null)
				{
				// the first list had null as its first element, that's no good
				return false;
				}
			}
		return true;
		}

	public static <T> T removeAllFirstElements(Set<List<T>> theLists)
		{
		T o = null;
		for (List<T> l : theLists)
			{
			if (l.isEmpty())
				{
				throw new IndexOutOfBoundsException("Can't remove first element from an empty list.");
				}
			o = l.remove(0);
			}
		return o;
		}

	public static <T> T chooseRandom(Collection<T> coll)
		{
		T[] ar = (T[]) coll.toArray();
		return ar[MersenneTwisterFast.randomInt(ar.length)];
		}
	}
