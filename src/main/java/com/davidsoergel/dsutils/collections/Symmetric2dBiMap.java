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

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A data structure that maps pairs of keys to values, and is queryable in both directions (i.e., also in the
 * value->keys direction).  The order of the keys is unimportant.  The key pairs are stored sorted by the associated
 * values, so eg. the pair with the lowest value can be queried.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class Symmetric2dBiMap<K extends Comparable<K>, V extends Comparable<V>>
	{
// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(Symmetric2dBiMap.class);

	// really we wanted a SortedBiMultimap or something, but lacking that, we just store the inverse map explicitly.

	//private TreeMultimap<V, KeyPair<K>> valueToKeyPair = new TreeMultimap<V, KeyPair<K>>();

	//private V smallestValue;
	//private KeyPair<K> keyPairWithSmallestValue;

	protected SimpleMultiMap<K, UnorderedPair<K>> keyToKeyPairs = new SimpleMultiMap<K, UnorderedPair<K>>();

	// for some reason the HashMultimap didn't seem to work right... occasionally gave the wrong size(), and such.  Maybe concurrency problems.
//	private Multimap<K, UnorderedPair<K>> keyToKeyPairs = HashMultimap.create();
	// Multimaps.synchronizedSetMultimap(HashMultimap.create());

	// we want a map sorted by value
	// simulate that using a regular map and a separate sorted set for the keysu
	// note that the key/value pair must be inserted into the regular map first, before the key is added to the sorted set

	private ValueSortedMap<UnorderedPair<K>, V> keyPairToValueSorted = new ValueSortedMap<UnorderedPair<K>, V>();


// -------------------------- OTHER METHODS --------------------------

	private synchronized V get(UnorderedPair<K> keyPair)
		{
		return keyPairToValueSorted.get(keyPair);
		}

	public synchronized V get(K key1, K key2)
		{
		return get(new UnorderedPair<K>(key1, key2)); //getKeyPair(key1, key2));
		}

	public synchronized Set<K> getActiveKeys()
		{
		return keyToKeyPairs.keySet();
		}

	public synchronized K getKey1WithSmallestValue()
		{
		return getKeyPairWithSmallestValue().getKey1();
		}

	public synchronized K getKey2WithSmallestValue()
		{
		return getKeyPairWithSmallestValue().getKey2();
		}

	public synchronized OrderedPair<UnorderedPair<K>, V> getKeyPairAndSmallestValue()
		{
		return keyPairToValueSorted.firstPair();//valueToKeyPair.get(getSmallestValue()).first();
		}

	public synchronized UnorderedPair<K> getKeyPairWithSmallestValue()
		{
		return keyPairToValueSorted.firstKey();//valueToKeyPair.get(getSmallestValue()).first();
		}

	public synchronized V getSmallestValue()
		{
		return keyPairToValueSorted.firstValue();
		//	..get(getKeyPairWithSmallestValue());//valueToKeyPair.keySet().first();// distanceToPair is sorted
		}

	// in many applications the matrix actually won't be complete
	public synchronized void matrixCompleteSanityCheck()
		{
		final int numKeys = numKeys();
		//if (numKeys > 2)
		//	{
		final int numKeyPairs = keyPairToValueSorted.size();

		//	assert numKeyPairs == keyPairToValueSorted.size();
		assert numKeyPairs == numKeys * (numKeys - 1) / 2;
		//	}
		}

	public synchronized int numPairs()
		{
		return keyPairToValueSorted.size();
		}

	/*SymmetricHashMap2D<LengthWeightHierarchyNode<T>, LengthWeightHierarchyNode<T>, Double> theDistanceMatrix =
							new SymmetricHashMap2D<LengthWeightHierarchyNode<T>, LengthWeightHierarchyNode<T>, Double>();*/


	public synchronized void put(K key1, K key2, V d)
		{
		sanityCheck();
		UnorderedPair<K> pair = new UnorderedPair<K>(key1, key2);
		putPairAndReSort(pair, d);
		keyToKeyPairs.put(key1, pair);
		keyToKeyPairs.put(key2, pair);
		sanityCheck();
		}

/*	private UnorderedPair getOrCreateKeyPair(K key1, K key2)
		{
		UnorderedPair<K> pair = getKeyPair(key1, key2);
		if (pair == null)
			{
			pair = new UnorderedPair(key1, key2);
			keyToKeyPairs.put(key1, pair);
			keyToKeyPairs.put(key2, pair);
			}
		return pair;
		}*/
/*
	private UnorderedPair<K> getKeyPair(K key1, K key2)
		{
		try
			{
			return //DSCollectionUtils
					Sets.intersection(keyToKeyPairs.get(key1), keyToKeyPairs.get(key2)).iterator().next();
			}
		catch (NoSuchElementException e)
			{
			return null;
			}
		}
*/

	/**
	 * Does not populate keyToKeyPairs!!
	 *
	 * @param keyPair
	 * @param d
	 */
	protected synchronized void putPairAndReSort(UnorderedPair<K> keyPair, V d)
		{
		//V oldValue = keyPairToValue.remove(keyPair);
		//if (oldValue != null)
		//	{


		//	keyPairToValue.remove(keyPair);
		//valueToKeyPair.remove(oldValue, keyPair);
		//	}

		keyPairToValueSorted.put(keyPair, d);

		// update the sort order
		//	keyPairsInValueOrder.remove(keyPair);
		//	keyPairsInValueOrder.add(keyPair);

		//valueToKeyPair.put(d, keyPair);
		}

	protected synchronized void sanityCheck()
		{
		final int numKeys = numKeys();
		//if (numKeys > 2)
		//	{
		final int numKeyPairs = keyPairToValueSorted.size();
		//	assert numKeyPairs == keyPairToValueSorted.size();
		assert numKeyPairs <= numKeys * (numKeys - 1) / 2;
		//	}
		}

	public synchronized int numKeys()
		{
		return keyToKeyPairs.numKeys();
		}

/*	public synchronized Collection<V> values()
		{
		return keyPairToValueSorted.values();
		}*/

	public synchronized void putAll(final Map<UnorderedPair<K>, V> result)
		{
		sanityCheck();
		for (Map.Entry<UnorderedPair<K>, V> entry : result.entrySet())
			{
			UnorderedPair<K> pair = entry.getKey();
			keyToKeyPairs.put(pair.getKey1(), pair);
			keyToKeyPairs.put(pair.getKey2(), pair);
			putPairAndReSort(pair, entry.getValue());
			}
		sanityCheck();
		}

	public synchronized void removeAll(final Collection<K> keys)
		{
		for (K key : keys)
			{
			remove(key);
			}
		}

	/**
	 * Remove all key pairs and values associated with the given key
	 *
	 * @param b
	 */
	public synchronized int remove(K b)
		{
		sanityCheck();
		int removed = 0;
		final Collection<UnorderedPair<K>> obsoletePairs = keyToKeyPairs.get(b);
		for (UnorderedPair<K> pair : obsoletePairs)
			{
			removed++;
			//keyPairsInValueOrder.remove(pair);
			keyPairToValueSorted.remove(pair);
			K a = pair.getKey1();
			if (a == b)
				{
				a = pair.getKey2();
				}
			keyToKeyPairs.get(a).remove(pair);

			// avoid ConcurrentModificationException by doung these all at once at the end
			//keyToKeyPairs.get(b).remove(pair);

			/*			try
			   {
			   SortedSet<KeyPair<K>> test = valueToKeyPair.get(oldValue);
			   test.remove(pair);
		   //	valueToKeyPair.remove(oldValue, pair);  // does not work; bug in StandardMultimap
			   }
		   catch (NullPointerException e)
			   {
			   logger.error("Error", e);
			   }*/
			sanityCheck();
			}
		keyToKeyPairs.removeAll(b);
		sanityCheck();
		return removed;
		}

// -------------------------- INNER CLASSES --------------------------

	protected class SimpleMultiMap<X, Y>
		{
// ------------------------------ FIELDS ------------------------------

		Map<X, Set<Y>> contents = new HashMap<X, Set<Y>>();


// -------------------------- OTHER METHODS --------------------------

		public synchronized Collection<Y> get(final X a)
			{
			Set<Y> ys = contents.get(a);
			if (ys == null)
				{
				ys = new HashSet<Y>();
				contents.put(a, ys);
				}
			return ys;
			}

		public synchronized Set<X> keySet()
			{
			return contents.keySet();
			}

		public synchronized int numKeys()
			{
			return contents.size();
			}

		public synchronized void put(final X key1, final Y val)
			{
			get(key1).add(val);
			}

		public synchronized void removeAll(final X b)
			{
			contents.remove(b);
			}
		}
	}
