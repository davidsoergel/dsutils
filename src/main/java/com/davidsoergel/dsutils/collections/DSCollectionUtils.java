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

import com.davidsoergel.dsutils.DSArrayUtils;
import com.davidsoergel.dsutils.math.MersenneTwisterFast;
import com.google.common.base.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @version $Id$
 */
public class DSCollectionUtils extends org.apache.commons.collections15.CollectionUtils
	{
	// -------------------------- STATIC METHODS --------------------------

	/**
	 * Shallow-copy any Collection into an ArrayList.  Useful for making a local copy for iteration, to avoid
	 * ConcurrentModificationExceptions.
	 */
	public static <T> ArrayList<T> arrayList(Collection<T> c)
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
		return DSArrayUtils.equalWithinFPError(aa, bb);
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

	public static <K, V> Set<V> mapAll(Map<K, V> m, Iterable<K> keys)
		{
		Set<V> result = new HashSet<V>();
		for (K key : keys)
			{
			result.add(m.get(key));
			}
		return result;
		}

	public static <K, V> Set<V> mapAll(Function<K, V> f, Iterable<K> keys)
		{
		Set<V> result = new HashSet<V>();
		for (K key : keys)
			{
			result.add(f.apply(key));
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


	public static <T> T getDominantFirstElement(Set<List<T>> theLists, int numberThatMustAgree)
		{
		WeightedSet<T> counts = new HashWeightedSet<T>();
		for (List<T> l : theLists)
			{
			/*if (l.isEmpty())
				{
				throw new IndexOutOfBoundsException("Can't get first element from an empty list.");
				}*/

			// if a list is exhausted, just ignore it.
			// If that turns out to make the count less than it needs to be, then we'll throw the appropriate exception below.
			if (!l.isEmpty())
				{
				counts.add(l.get(0), 1, 1);
				}
			}
		T result = counts.getDominantKey();
		if (counts.get(result) < numberThatMustAgree)
			{
			throw new NoSuchElementException();
			}
		return result;
		}

	public static <T> boolean allFirstElementsEqual(Set<List<? extends T>> theLists) //, boolean ignoreNull)
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
				/*	if (ignoreNull)
				   {
				   continue;
				   }
			   else
				   {*/
				return false;
				//	}
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

	public static <T> Set<List<T>> filterByAndRemoveFirstElement(Set<List<T>> theLists, T firstElement)
		{

		// this doesn't work, maybe because the list contents are changing and that screws up the HashSet?? (eg hashcode issues)

		/*
		T o = null;
		for (Iterator<List<T>> iter = theLists.iterator(); iter.hasNext();)
			{
			List<T> theAncestorList = iter.next();
			if (theAncestorList.isEmpty() || (theAncestorList.get(0) != firstElement))
				{
				iter.remove();
				}
			else
				{
				theAncestorList.remove(0);
				}
			}*/

		//just do the slow way for now
		Set<List<T>> result = new HashSet<List<T>>();
		T o = null;
		for (Iterator<List<T>> iter = theLists.iterator(); iter.hasNext();)
			{
			List<T> theAncestorList = iter.next();
			if (theAncestorList.isEmpty() || (theAncestorList.get(0) != firstElement))
				{
				//iter.remove();
				}
			else
				{
				theAncestorList.remove(0);
				result.add(theAncestorList);
				}
			}
		return result;
		}

	public static <T> T removeAllFirstElements(Set<List<? extends T>> theLists) //, boolean ignoreEmpty)
		{
		T o = null;
		for (List<? extends T> l : theLists)
			{
			if (l.isEmpty())
				{
				if (l.isEmpty())
					{
					/*if (ignoreEmpty)
						{
						continue;
						}
					else
						{*/
					throw new IndexOutOfBoundsException("Can't remove first element from an empty list.");
					//	}
					}
				}
			o = l.remove(0);
			}
		return o;
		}

	public static <T> Collection<T> getAllFirstElements(Set<List<T>> theLists)
		{
		Set<T> result = new HashSet<T>();
		for (List<T> l : theLists)
			{
			if (l.isEmpty())
				{
				throw new IndexOutOfBoundsException("Can't get first element from an empty list.");
				}
			result.add(l.get(0));
			}
		return result;
		}

	public static <T> T chooseRandom(Collection<T> coll)
		{
		T[] ar = (T[]) coll.toArray();
		return ar[MersenneTwisterFast.randomInt(ar.length)];
		}

	public static <T> T getFirst(Collection<T> coll)
		{
		// like coll.iterator().next(), but we hope this is faster since it doesn't instantiate hashsets
		// oops, yes it does
		//return (T)coll.toArray()[0];
		return coll.iterator().next();
		}

	public static <T> Set<T> setOf(T... things)
		{
		return new HashSet<T>(Arrays.asList(things));
		}

	public static <T> List<T> listOf(T... things)
		{
		return new ArrayList<T>(Arrays.asList(things));  // copy so that we return a mutable array
		}

	public static <K, V> boolean isEqualMap(Map<K, V> mapA, Map<K, V> mapB)
		{
		if (mapA.size() != mapB.size())
			{
			return false;
			}

		for (Map.Entry<K, V> entry : mapA.entrySet())
			{
			if (!(entry.getValue().equals(mapB.get(entry.getKey()))))
				{
				return false;
				}
			}

		return true;
		}


	public static boolean allElementsEqual(Collection list, Object o)
		{
		if (list == null)
			{
			return o == null;
			}
		for (Object p : list)
			{
			if (!p.equals(o))
				{
				return false;
				}
			}
		return true;
		}


	public static double sum(Iterable<Double> a)
		{
		double result = 0.0;
		for (double d : a)
			{
			result += d;
			}
		return result;
		}
/*
	public static double sum(Iterable<Integer> a)
		{
		double result = 0.0;
		for (double d : a)
			{
			result += d;
			}
		return result;
		}*/

	public static <T> Set<T> intersectionSet(Collection<T> a, Collection<T> b)
		{
		return new HashSet<T>(intersection(a, b));
		}

	public static String[] mapToString(final Collection os)
		{
		List<String> result = new ArrayList<String>(os.size());
		for (Object o : os)
			{
			result.add(o.toString());
			}
		return result.toArray(DSArrayUtils.EMPTY_STRING_ARRAY);
		}
	}
