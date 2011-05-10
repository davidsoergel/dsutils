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
import com.davidsoergel.dsutils.EquivalenceDefinition;
import com.davidsoergel.dsutils.math.MersenneTwisterFast;
import com.google.common.base.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

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
	@NotNull
	public static <T> ArrayList<T> arrayList(@NotNull Collection<T> c)
		{
		if (c instanceof ArrayList)
			{
			return (ArrayList<T>) c;
			}

		return new ArrayList<T>(c);
		}

	@NotNull
	public static ArrayList<Double> plus(@NotNull List<Number> a, @NotNull List<Number> b)
		{
		if (a.size() != b.size())
			{
			throw new IndexOutOfBoundsException("Can't add arrays of different sizes");
			}
		@NotNull ArrayList<Double> result = new ArrayList<Double>(a.size());


		for (int i = 0; i < a.size(); i++)
			{
			//logger.debug("Adding cells: " + i + ", " + j);
			result.add(a.get(i).doubleValue() + b.get(i).doubleValue());
			}
		return result;
		}

	@NotNull
	public static ArrayList<Double> minus(@NotNull List<Number> a, @NotNull List<Number> b)
		{
		if (a.size() != b.size())
			{
			throw new IndexOutOfBoundsException("Can't add arrays of different sizes");
			}
		@NotNull ArrayList<Double> result = new ArrayList<Double>(a.size());


		for (int i = 0; i < a.size(); i++)
			{
			//logger.debug("Adding cells: " + i + ", " + j);
			result.add(a.get(i).doubleValue() - b.get(i).doubleValue());
			}
		return result;
		}

	public static boolean deepEqualsWithinFPError(@NotNull Set<Double> a, @NotNull Set<Double> b)
		{
		Double[] aa = a.toArray(new Double[]{});
		Double[] bb = b.toArray(new Double[]{});
		Arrays.sort(aa);
		Arrays.sort(bb);
		return DSArrayUtils.equalWithinFPError(aa, bb);
		}

	public static void retainRandom(@NotNull List list, int numElements)
		{
		//inefficient...?
		while (list.size() > numElements)
			{
			list.remove(MersenneTwisterFast.randomInt(list.size()));
			}
		}


	public static void retainRandom(@NotNull Collection set, int numElements)
		{
		//inefficient...?
		@NotNull List list = new LinkedList(set);
		while (set.size() > numElements)
			{
			int pos = MersenneTwisterFast.randomInt(set.size());
			set.remove(list.get(pos));
			list.remove(pos);
			}
		}


	public static void retainRandom(@NotNull Map map, int numElements)
		{
		//inefficient...?
		@NotNull List list = new LinkedList(map.keySet());
		while (map.size() > numElements)
			{
			int pos = MersenneTwisterFast.randomInt(map.size());
			map.remove(list.get(pos));
			list.remove(pos);
			}
		}

	@NotNull
	public static <K, V> List<V> mapAll(@NotNull Map<K, V> m, @NotNull Iterable<K> keys)
		{
		@NotNull List<V> result = new ArrayList<V>(); //new HashSet<V>();
		for (K key : keys)
			{
			result.add(m.get(key));
			}
		return result;
		}

	@NotNull
	public static <K, V> List<V> mapAll(@NotNull Function<K, V> f, @NotNull Iterable<K> keys)
		{
		@NotNull List<V> result = new ArrayList<V>(); //new HashSet<V>();
		for (K key : keys)
			{
			result.add(f.apply(key));
			}
		return result;
		}

	@NotNull
	public static <K, V> List<V> mapAllIgnoringNulls(@NotNull Map<K, V> m, @NotNull Iterator<? extends K> keys)
		{
		@NotNull List<V> result = new ArrayList<V>(); //new HashSet<V>();
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


	public static <T> T getDominantFirstElement(@NotNull Set<List<T>> theLists, int numberThatMustAgree)
		{
		@NotNull MutableWeightedSet<T> counts = new ConcurrentHashWeightedSet<T>();
		for (@NotNull List<T> l : theLists)
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

	public static <T> boolean allFirstElementsEqual(@NotNull Set<List<? extends T>> theLists) //, boolean ignoreNull)
	{
	@Nullable Object o = null;
	if (theLists.isEmpty())
		{
		return false;
		}
	for (@NotNull List l : theLists)
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

	@NotNull
	public static <T> Set<List<T>> filterByAndRemoveFirstElement(@NotNull Set<List<T>> theLists, T firstElement)
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
		@NotNull Set<List<T>> result = new HashSet<List<T>>();
		@Nullable T o = null;
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

	@Nullable
	public static <T> T removeAllFirstElements(@NotNull Set<List<? extends T>> theLists) //, boolean ignoreEmpty)
	{
	@Nullable T o = null;
	for (@NotNull List<? extends T> l : theLists)
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

	@NotNull
	public static <T> Collection<T> getAllFirstElements(@NotNull Set<List<T>> theLists)
		{
		@NotNull Set<T> result = new HashSet<T>();
		for (@NotNull List<T> l : theLists)
			{
			if (l.isEmpty())
				{
				throw new IndexOutOfBoundsException("Can't get first element from an empty list.");
				}
			result.add(l.get(0));
			}
		return result;
		}

	public static <T> T chooseRandom(@NotNull Collection<T> coll)
		{
		//PERF ?
		@NotNull T[] ar = (T[]) coll.toArray();
		return ar[MersenneTwisterFast.randomInt(ar.length)];
		}

	public static <T> T getFirst(@NotNull Collection<T> coll)
		{
		// like coll.iterator().next(), but we hope this is faster since it doesn't instantiate hashsets
		// oops, yes it does
		//return (T)coll.toArray()[0];
		return coll.iterator().next();
		}

	@NotNull
	public static <T> Set<T> setOf(T... things)
		{
		return new HashSet<T>(Arrays.asList(things));
		}

	@NotNull
	public static <T> List<T> listOf(T... things)
		{
		return new ArrayList<T>(Arrays.asList(things));  // copy so that we return a mutable array
		}

	public static <K, V> boolean isEqualMap(@NotNull Map<K, V> mapA, @NotNull Map<K, V> mapB)
		{
		if (mapA.size() != mapB.size())
			{
			return false;
			}

		for (@NotNull Map.Entry<K, V> entry : mapA.entrySet())
			{
			if (!(entry.getValue().equals(mapB.get(entry.getKey()))))
				{
				return false;
				}
			}

		return true;
		}


	public static boolean allElementsEqual(@Nullable Collection list, @Nullable Object o)
		{
		if (list == null)
			{
			return o == null;
			}
		for (@NotNull Object p : list)
			{
			if (!p.equals(o))
				{
				return false;
				}
			}
		return true;
		}


	public static boolean allElementsNaN(@Nullable final Collection<Double> list)
		{
		if (list == null)
			{
			return true;
			}
		for (@NotNull Double p : list)
			{
			if (!p.isNaN())
				{
				return false;
				}
			}
		return true;
		}


	public static double sum(@NotNull Iterable<Double> a)
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

	@NotNull
	public static <T> Set<T> intersectionSet(Collection<T> a, Collection<T> b)
		{
		return new HashSet<T>(intersection(a, b));
		}

	public static <T> Set<T> intersectionSlow(Collection<T> a, Collection<T> b, Comparator<T> comp)
		{
		Set<T> result = new HashSet<T>();

		for (T t : a)
			{
			for (T t1 : b)
				{
				if (comp.compare(t, t1) == 0)
					{
					result.add(t);
					break;
					}
				}
			}
		return result;
		}

	/**
	 * Intersection defining equality only by the natural comparator (where 0 => equal). The comparator must provide a
	 * stable sort.  equals() and hashCode are ignored.
	 *
	 * @param a
	 * @param b
	 * @param comp
	 * @param <T>
	 * @return
	 */
	public static <T extends Comparable<T>> Set<T> intersectionUsingCompare(Set<T> a, Set<T> b)
		{
		Comparator<T> comp = new Comparator<T>()
		{
		public int compare(final T o1, final T o2)
			{
			return o1.compareTo(o2);
			}
		};
		return intersectionFast(a, b, comp);
		}

	public static <T extends Comparable<T>> Set<T> unionUsingCompare(Set<T> a, Set<T> b)
		{
		Comparator<T> comp = new Comparator<T>()
		{
		public int compare(final T o1, final T o2)
			{
			return o1.compareTo(o2);
			}
		};

		return unionFast(a, b, comp);
		}


	/**
	 * Intersection defining equality only by the provided comparator (where 0 => equal).  The sort order is otherwise
	 * ignored; all elemets are compared exhaustively.  equals() and hashCode are ignored.  THe elements returned are those
	 * from the first collection.
	 *
	 * @param a
	 * @param b
	 * @param equiv
	 * @param <T>
	 * @return
	 */
	public static <T> Set<T> intersectionExhaustive(Collection<T> a, Collection<T> b, EquivalenceDefinition<T> equiv)
		{
		Set<T> result = new HashSet<T>();

		/*SortedSet<T> as = new TreeSet<T>(comp);
		as.addAll(a);
		SortedSet<T> bs = new TreeSet<T>(comp);
		bs.addAll(b);*/
		Set<T> bs = new HashSet<T>(b);

		for (T at : a)
			{
			for (T bt : bs)
				{
				if (equiv.areEquivalent(at, bt))
					{
					result.add(at);
					bs.remove(bt);
					break;
					}
				// try all the b's up to the point that they exceed the a in question
				// since they're sorted, no later b's can possibly match
				//** except in the case of unstable sort
				/*if (compare <= 0)
					{
					break;
					}*/
				}
			}
		;
		return result;
		}

	/**
	 * Intersection defining equality only by the provided comparator (where 0 => equal). The comparator must provide a
	 * stable sort.  equals() and hashCode are ignored.  THe elements returned are those from the first collection.
	 *
	 * @param a
	 * @param b
	 * @param comp
	 * @param <T>
	 * @return
	 */
	public static <T> Set<T> intersectionFast(Set<T> a, Set<T> b, Comparator<T> comp)
		{
		Set<T> result = new HashSet<T>();

		SortedSet<T> as = new TreeSet<T>(comp);
		as.addAll(a);
		SortedSet<T> bs = new TreeSet<T>(comp);
		bs.addAll(b);

		for (T at : as)
			{
			for (T bt : bs.tailSet(at))
				{
				int compare = comp.compare(at, bt);
				if (compare == 0)
					{
					result.add(at);
					bs.remove(bt);
					}
				// try all the b's up to the point that they exceed the a in question
				// since they're sorted, no later b's can possibly match
				if (compare <= 0)
					{
					break;
					}
				}
			}

		return result;
		}

	/**
	 * Intersection defining equality only by the provided comparator (where 0 => equal). The comparator must provide a
	 * stable sort.  equals() and hashCode are ignored.  THe elements returned are those from the first collection.
	 *
	 * @param a
	 * @param b
	 * @param comp
	 * @param <T>
	 * @return
	 */
	public static <T extends Comparable<T>> Set<T> intersectionFastUsingCompare(SortedSet<T> a, SortedSet<T> b)
		{
		Set<T> result = new HashSet<T>();

		for (T at : a)
			{
			for (T bt : b.tailSet(at))
				{
				int compare = at.compareTo(bt);
				if (compare == 0)
					{
					result.add(at);
					}
				// try all the b's up to the point that they exceed the a in question
				// since they're sorted, no later b's can possibly match
				if (compare <= 0)
					{
					break;
					}
				}
			}
		;
		return result;
		}


	/**
	 * Intersection defining equality only by the provided comparator (where 0 => equal). The comparator must provide a
	 * stable sort.  equals() and hashCode are ignored.
	 *
	 * @param a
	 * @param b
	 * @param comp
	 * @param <T>
	 * @return
	 */
	public static <T> Set<T> unionExhaustive(Set<T> a, Set<T> b, EquivalenceDefinition<T> comp)
		{
		Set<T> result = subtractExhaustive(a, b, comp);
		result.addAll(b);
		return result;
		}


	public static <T> Set<T> unionFast(Set<T> a, Set<T> b, Comparator<T> comp)
		{
		Set<T> result = subtractFast(a, b, comp);
		result.addAll(b);
		return result;
		}

	public static <T extends Comparable> Set<T> unionFastUsingCompare(SortedSet<T> a, SortedSet<T> b)
		{
		Set<T> result = subtractFastUsingCompare(a, b);
		result.addAll(b);
		return result;
		}


	public static <T> Set<T> subtractExhaustive(Set<T> a, Set<T> b, EquivalenceDefinition<T> equiv)
		{
		Set<T> result = new HashSet<T>(a);

		/*SortedSet<T> as = new TreeSet<T>(equiv);
		as.addAll(a);
		SortedSet<T> bs = new TreeSet<T>(equiv);
		bs.addAll(b);*/

		Set<T> bs = new HashSet<T>(b);
		for (T at : a)
			{
			for (T bt : bs)
				{
				if (equiv.areEquivalent(at, bt))
					{
					result.remove(at);
					bs.remove(bt);  // don't bother testing future a's against this
					break;
					}

				// try all the b's up to the point that they exceed the a in question
				// since they're sorted, no later b's can possibly match
				//** except in the case of unstable sort
				/*if (compare <= 0)
					{
					break;
					}*/
				}
			}
		return result;
		}

	public static <T> Set<T> subtractFast(Set<T> a, Set<T> b, Comparator<T> comp)
		{
		Set<T> result = new HashSet<T>(a);

		SortedSet<T> as = new TreeSet<T>(comp);
		as.addAll(a);
		SortedSet<T> bs = new TreeSet<T>(comp);
		bs.addAll(b);

		for (T at : as)
			{
			for (T bt : bs.tailSet(at))
				{
				int compare = comp.compare(at, bt);
				if (compare == 0)
					{
					result.remove(at);
					bs.remove(bt);  // don't bother testing future a's against this
					}

				// try all the b's up to the point that they exceed the a in question
				// since they're sorted, no later b's can possibly match
				if (compare <= 0)
					{
					break;
					}
				}
			}
		return result;
		}

	public static <T extends Comparable<T>> Set<T> subtractFastUsingCompare(SortedSet<T> a, SortedSet<T> b)
		{
		Set<T> result = new HashSet<T>(a);

		for (T at : a)
			{
			for (T bt : b.tailSet(at))
				{
				int compare = at.compareTo(bt);
				if (compare == 0)
					{
					result.remove(at);
					}

				// try all the b's up to the point that they exceed the a in question
				// since they're sorted, no later b's can possibly match
				if (compare <= 0)
					{
					break;
					}
				}
			}
		return result;
		}


	public static String[] mapToString(@NotNull final Collection os)
		{
		@NotNull List<String> result = new ArrayList<String>(os.size());
		for (@NotNull Object o : os)
			{
			result.add(o.toString());
			}
		return result.toArray(DSArrayUtils.EMPTY_STRING_ARRAY);
		}

	@NotNull
	public static <T> Set<Set<T>> subsetsOfSize(@NotNull final Set<T> entries, final int i)
		{
		@NotNull Set<Set<T>> result = new HashSet<Set<T>>();
		Iterator<T> it = entries.iterator();
		while (it.hasNext())
			{
			@NotNull Set<T> block = new HashSet<T>();
			try
				{
				for (int j = 0; j < i; j++)
					{
					block.add(it.next());
					}
				}
			catch (NoSuchElementException e)
				{
				// OK we're done
				}
			result.add(block);
			}
		return result;
		}
	}
