/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

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

	@NotNull
	protected SimpleMultiMap<K, UnorderedPair<K>> keyToKeyPairs = new SimpleMultiMap<K, UnorderedPair<K>>();

	// for some reason the HashMultimap didn't seem to work right... occasionally gave the wrong size(), and such.  Maybe concurrency problems.
//	private Multimap<K, UnorderedPair<K>> keyToKeyPairs = HashMultimap.create();
	// Multimaps.synchronizedSetMultimap(HashMultimap.create());

	// we want a map sorted by value
	// simulate that using a regular map and a separate sorted set for the keysu
	// note that the key/value pair must be inserted into the regular map first, before the key is added to the sorted set

	// public only for the sake of efficient serialization in HierarchicalClusteringStringDistanceMatrix

	@NotNull
	public ConcurrentValueSortedMap<UnorderedPair<K>, V> keyPairToValueSorted =
			new ConcurrentValueSortedMap<UnorderedPair<K>, V>();

	public SortedSymmetric2dBiMapImpl(@NotNull final SortedSymmetric2dBiMapImpl<K, V> cloneFrom)
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

	public V get(@NotNull K key1, @NotNull K key2)
		{
		return get(new UnorderedPair<K>(key1, key2)); //getKeyPair(key1, key2));
		}

	public Set<K> getKeys()
		{
		return keyToKeyPairs.keySet();
		}

	@NotNull
	public K getKey1WithSmallestValue()
		{
		return getKeyPairWithSmallestValue().getKey1();
		}

	@NotNull
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

	public boolean isEmpty()
		{
		return keyPairToValueSorted.isEmpty();
		}

	/*SymmetricHashMap2D<LengthWeightHierarchyNode<T>, LengthWeightHierarchyNode<T>, Double> theDistanceMatrix =
							new SymmetricHashMap2D<LengthWeightHierarchyNode<T>, LengthWeightHierarchyNode<T>, Double>();*/


	public void put(@NotNull K key1, @NotNull K key2, @NotNull V d)
		{
		assert !key1.equals(key2);
		//sanityCheck();
		@NotNull UnorderedPair<K> pair = new UnorderedPair<K>(key1, key2);
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
	protected void put(@NotNull UnorderedPair<K> keyPair, @NotNull V d)
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

	public synchronized void putAll(@NotNull final Map<UnorderedPair<K>, V> result)
		{
		// sanityCheck();
		for (@NotNull Map.Entry<UnorderedPair<K>, V> entry : result.entrySet())
			{
			UnorderedPair<K> pair = entry.getKey();

			@NotNull final K key1 = pair.getKey1();
			@NotNull final K key2 = pair.getKey2();

			assert !key1.equals(key2);

			keyToKeyPairs.put(key1, pair);
			keyToKeyPairs.put(key2, pair);
			keyPairToValueSorted.put(pair, entry.getValue());
			}
		// sanityCheck();
		}

	public synchronized void removeAll(@NotNull final Collection<K> keys)
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
		@NotNull final Collection<UnorderedPair<K>> obsoletePairs = new HashSet<UnorderedPair<K>>(keyToKeyPairs.get(b));
		for (@NotNull UnorderedPair<K> pair : obsoletePairs)
			{
			removed++;
			//keyPairsInValueOrder.remove(pair);
			keyPairToValueSorted.remove(pair);

			// the pair may be in either order; we'll be removing b wholesale later, so
			// now make sure that a is the element of the pair that is not b
			@NotNull K a = pair.getKey1();
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

		// sanityCheck();
		return removed;
		}

	public Set<Map.Entry<UnorderedPair<K>, V>> entrySet()
		{
		return keyPairToValueSorted.entrySet();
		}

	public void removalSanityCheck(final K removedKey, @NotNull final Collection<K> remainingKeys)
		{
		assert !getKeys().contains(removedKey);
		for (@NotNull Map.Entry<UnorderedPair<K>, V> entry : keyPairToValueSorted.entrySet())
			{
			@NotNull final K k1 = entry.getKey().getKey1();
			@NotNull final K k2 = entry.getKey().getKey2();

			assert !k1.equals(removedKey);
			assert !k2.equals(removedKey);

			assert remainingKeys.contains(k1);
			assert remainingKeys.contains(k2);
			}
		}


	@NotNull
	public ConcurrentLinkedQueue<Map.Entry<UnorderedPair<K>, V>> entriesQueue()
		{
		return keyPairToValueSorted.entriesQueue();
		}

// -------------------------- INNER CLASSES --------------------------

	protected class SimpleMultiMap<X extends Serializable, Y extends Serializable> implements Serializable
		{
// ------------------------------ FIELDS ------------------------------

		@NotNull
		private Map<X, Set<Y>> contents = new ConcurrentHashMap<X, Set<Y>>();

		public SimpleMultiMap()
			{
			}

		/**
		 * The keys themselves are not cloned
		 *
		 * @param cloneFrom
		 */
		public SimpleMultiMap(@NotNull final SimpleMultiMap<X, Y> cloneFrom)
			{
			contents.putAll(cloneFrom.contents);
			}


// -------------------------- OTHER METHODS --------------------------

		public void addKeys(final Collection<X> c)
			{
			for (X a : c)
				{
				Set<Y> ys = contents.get(a);
				if (ys == null)
					{
					ys = new ConcurrentSkipListSet<Y>();
					contents.put(a, ys);
					}
				}
			}

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

		public void put(@NotNull final X key1, @NotNull final Y val)
			{
			get(key1).add(val);
			}

		public void removeAll(final X b)
			{
			contents.remove(b);
			}
		}
	}
