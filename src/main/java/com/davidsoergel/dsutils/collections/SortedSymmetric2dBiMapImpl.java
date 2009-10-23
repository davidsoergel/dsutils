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

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * A data structure that maps pairs of keys to values, and is queryable in both directions (i.e., also in the
 * value->keys direction).  The order of the keys is unimportant.  The key pairs are stored sorted by the associated
 * values, so eg. the pair with the lowest value can be queried.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SortedSymmetric2dBiMapImpl<K extends Comparable<K> & Serializable, V extends Comparable<V> & Serializable>
		implements SortedSymmetric2dBiMap<K, V>
		//	implements Serializable
	{
// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(SortedSymmetric2dBiMapImpl.class);

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

	// public only for the sake of efficient serialization in HierarchicalClusteringStringDistanceMatrix

	public ConcurrentValueSortedMap<UnorderedPair<K>, V> keyPairToValueSorted =
			new ConcurrentValueSortedMap<UnorderedPair<K>, V>();

	public SortedSymmetric2dBiMapImpl(final SortedSymmetric2dBiMapImpl<K, V> cloneFrom)
		{
		keyToKeyPairs = new SimpleMultiMap<K, UnorderedPair<K>>(cloneFrom.keyToKeyPairs);
		keyPairToValueSorted = new ConcurrentValueSortedMap<UnorderedPair<K>, V>(cloneFrom.keyPairToValueSorted);
		}

	public SortedSymmetric2dBiMapImpl()
		{
		}


// -------------------------- OTHER METHODS --------------------------

	protected V get(UnorderedPair<K> keyPair)
		{
		return keyPairToValueSorted.get(keyPair);
		}

	public V get(K key1, K key2)
		{
		return get(new UnorderedPair<K>(key1, key2)); //getKeyPair(key1, key2));
		}

	public Set<K> getKeys()
		{
		return keyToKeyPairs.keySet();
		}

	public K getKey1WithSmallestValue()
		{
		return getKeyPairWithSmallestValue().getKey1();
		}

	public K getKey2WithSmallestValue()
		{
		return getKeyPairWithSmallestValue().getKey2();
		}

	public synchronized OrderedPair<UnorderedPair<K>, V> getKeyPairAndSmallestValue()
		{
		return keyPairToValueSorted.firstPair();//valueToKeyPair.get(getSmallestValue()).first();
		}

	public UnorderedPair<K> getKeyPairWithSmallestValue()
		{
		return keyPairToValueSorted.firstKey();//valueToKeyPair.get(getSmallestValue()).first();
		}

	public V getSmallestValue()
		{
		return keyPairToValueSorted.firstValue();
		//	..get(getKeyPairWithSmallestValue());//valueToKeyPair.keySet().first();// distanceToPair is sorted
		}

	// in many applications the matrix actually won't be complete
	public void matrixCompleteSanityCheck()
		{
		final int numKeys = numKeys();
		//if (numKeys > 2)
		//	{
		final int numKeyPairs = keyPairToValueSorted.size();

		//	assert numKeyPairs == keyPairToValueSorted.size();
		assert numKeyPairs == numKeys * (numKeys - 1) / 2;
		//	}
		}

	public int numPairs()
		{
		return keyPairToValueSorted.size();
		}

	/*SymmetricHashMap2D<LengthWeightHierarchyNode<T>, LengthWeightHierarchyNode<T>, Double> theDistanceMatrix =
							new SymmetricHashMap2D<LengthWeightHierarchyNode<T>, LengthWeightHierarchyNode<T>, Double>();*/


	public void put(K key1, K key2, V d)
		{
		assert !key1.equals(key2);
		//sanityCheck();
		UnorderedPair<K> pair = new UnorderedPair<K>(key1, key2);
		keyPairToValueSorted.put(pair, d);
		keyToKeyPairs.put(key1, pair);
		keyToKeyPairs.put(key2, pair);
		//sanityCheck();
		}

	/**
	 * Does not populate keyToKeyPairs!!
	 *
	 * @param keyPair
	 * @param d
	 */
	protected void put(UnorderedPair<K> keyPair, V d)
		{
		keyPairToValueSorted.put(keyPair, d);
		keyToKeyPairs.put(keyPair.getKey1(), keyPair);
		keyToKeyPairs.put(keyPair.getKey2(), keyPair);
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

	public synchronized void addKey(K key1)
		{
		keyToKeyPairs.get(key1);
		}

	public synchronized void putAll(final Map<UnorderedPair<K>, V> result)
		{
		sanityCheck();
		for (Map.Entry<UnorderedPair<K>, V> entry : result.entrySet())
			{
			UnorderedPair<K> pair = entry.getKey();

			final K key1 = pair.getKey1();
			final K key2 = pair.getKey2();

			assert !key1.equals(key2);

			keyToKeyPairs.put(key1, pair);
			keyToKeyPairs.put(key2, pair);
			keyPairToValueSorted.put(pair, entry.getValue());
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
		//sanityCheck();
		int removed = 0;
		final Collection<UnorderedPair<K>> obsoletePairs = new HashSet<UnorderedPair<K>>(keyToKeyPairs.get(b));
		for (UnorderedPair<K> pair : obsoletePairs)
			{
			removed++;
			//keyPairsInValueOrder.remove(pair);
			keyPairToValueSorted.remove(pair);

			// the pair may be in either order; we'll be removing b wholesale later, so
			// now make sure that a is the element of the pair that is not b
			K a = pair.getKey1();
			if (a.equals(b))
				{
				a = pair.getKey2();
				assert !a.equals(b);
				}

			keyToKeyPairs.get(a).remove(pair);
			//	keyToKeyPairs.get(b).remove(pair);

			// avoid ConcurrentModificationException by doing these all at once at the end
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
			// sanityCheck();
			}
		keyToKeyPairs.removeAll(b);

		sanityCheck();
		return removed;
		}

	public Set<Map.Entry<UnorderedPair<K>, V>> entrySet()
		{
		return keyPairToValueSorted.entrySet();
		}

	public void removalSanityCheck(final K b, final Collection<K> keys)
		{
		assert !getKeys().contains(b);
		for (Map.Entry<UnorderedPair<K>, V> entry : keyPairToValueSorted.entrySet())
			{
			final K k1 = entry.getKey().getKey1();
			final K k2 = entry.getKey().getKey2();

			assert !k1.equals(b);
			assert !k2.equals(b);

			assert keys.contains(k1);
			assert keys.contains(k2);
			}
		}


	public ConcurrentLinkedQueue<Map.Entry<UnorderedPair<K>, V>> entriesQueue()
		{
		return keyPairToValueSorted.entriesQueue();
		}

// -------------------------- INNER CLASSES --------------------------

	protected class SimpleMultiMap<X extends Serializable, Y extends Serializable> implements Serializable
		{
// ------------------------------ FIELDS ------------------------------

		private Map<X, Set<Y>> contents = new ConcurrentHashMap<X, Set<Y>>();

		public SimpleMultiMap()
			{
			}

		/**
		 * The keys themselves are not cloned
		 *
		 * @param cloneFrom
		 */
		public SimpleMultiMap(final SimpleMultiMap<X, Y> cloneFrom)
			{
			contents.putAll(cloneFrom.contents);
			}


// -------------------------- OTHER METHODS --------------------------

		public Collection<Y> get(final X a)
			{
			Set<Y> ys = contents.get(a);
			if (ys == null)
				{
				ys = new ConcurrentSkipListSet<Y>();
				contents.put(a, ys);
				}
			return ys;
			}

		public Set<X> keySet()
			{
			return contents.keySet();
			}

		public int numKeys()
			{
			return contents.size();
			}

		public void put(final X key1, final Y val)
			{
			get(key1).add(val);
			}

		public void removeAll(final X b)
			{
			contents.remove(b);
			}
		}
	}
