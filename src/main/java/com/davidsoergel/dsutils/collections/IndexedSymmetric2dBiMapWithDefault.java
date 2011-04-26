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

import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A data structure that maps pairs of keys to values, and is queryable in both directions (i.e., also in the
 * value->keys direction).  The order of the keys is unimportant.  The key pairs are stored sorted by the associated
 * values, so eg. the pair with the lowest value can be queried.  Internally each key is associated with an integer, and
 * integer pairs are associated with values; so it's easy to map old keys to new keys without disturbing the actual data
 * table.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id: Symmetric2dBiMap.java 539 2009-10-16 23:29:36Z soergel $
 */
public class IndexedSymmetric2dBiMapWithDefault<K extends Comparable<K> & Serializable, V extends Comparable<V> & Serializable>
		implements SortedSymmetric2dBiMap<K, V>
		//	implements Serializable
	{
// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(IndexedSymmetric2dBiMapWithDefault.class);

	// should be final, but for deserialization
	protected InsertionTrackingSet<K> keys;// = new InsertionTrackingSet<K>();
	//final BiMap<K,Integer> keys = new HashBiMap<K,Integer>();

	// should be final, but for clone constructor
	protected SortedSymmetric2dBiMapWithDefault<Integer, V> underlyingIntMap;

	public IndexedSymmetric2dBiMapWithDefault(final V defaultValue)
		{
		underlyingIntMap = new SortedSymmetric2dBiMapWithDefault<Integer, V>(defaultValue);
		keys = new InsertionTrackingSet<K>();
		}

	public IndexedSymmetric2dBiMapWithDefault()
		{
		underlyingIntMap = new SortedSymmetric2dBiMapWithDefault<Integer, V>();
		keys = new InsertionTrackingSet<K>();
		}

	public IndexedSymmetric2dBiMapWithDefault(@NotNull final IndexedSymmetric2dBiMapWithDefault<K, V> cloneFrom)
		{
		setDefaultValue(cloneFrom.getDefaultValue());
		keys = new InsertionTrackingSet<K>(cloneFrom.keys);
		underlyingIntMap = new SortedSymmetric2dBiMapWithDefault<Integer, V>(cloneFrom.underlyingIntMap);
		}

	public IndexedSymmetric2dBiMapWithDefault(final V defaultValue, final InsertionTrackingSet<K> keys)
		{
		underlyingIntMap = new SortedSymmetric2dBiMapWithDefault<Integer, V>(defaultValue);
		this.keys = new InsertionTrackingSet<K>(keys);
		}

	public IndexedSymmetric2dBiMapWithDefault<K, Float> copyDefaultAndKeys()
		{
		return new IndexedSymmetric2dBiMapWithDefault(getDefaultValue(), keys);
		}

	public void setDefaultValue(V defaultValue)
		{
		underlyingIntMap.setDefaultValue(defaultValue);
		}

	public V getDefaultValue()
		{
		return underlyingIntMap.getDefaultValue();
		}

	public K getKey1WithSmallestValue()
		{
		return keys.get(underlyingIntMap.getKey1WithSmallestValue());
		}

	public K getKey2WithSmallestValue()
		{
		return keys.get(underlyingIntMap.getKey2WithSmallestValue());
		}

	@NotNull
	public synchronized OrderedPair<UnorderedPair<K>, V> getKeyPairAndSmallestValue()
		{
		final OrderedPair<UnorderedPair<Integer>, V> op = underlyingIntMap.getKeyPairAndSmallestValue();
		final UnorderedPair<Integer> p = op.getKey1();
		final K id1 = keys.get(p.getKey1());
		final K id2 = keys.get(p.getKey2());
		if (id1 == null || id2 == null)
			{
			sanityCheck();
			//throw new CollectionRuntimeException("")
			}
		assert id1 != null;
		assert id2 != null;
		return new OrderedPair<UnorderedPair<K>, V>(new UnorderedPair<K>(id1, id2), op.getKey2());
		}

	@NotNull
	public synchronized UnorderedPair<K> getKeyPairWithSmallestValue()
		{
		final UnorderedPair<Integer> p = underlyingIntMap.getKeyPairWithSmallestValue();
		final K id1 = keys.get(p.getKey1());
		final K id2 = keys.get(p.getKey2());
		assert id1 != null;
		assert id2 != null;
		return new UnorderedPair<K>(id1, id2);
		}

	public V getSmallestValue()
		{
		return underlyingIntMap.getSmallestValue();
		}

	public V get(@NotNull final K key1, @NotNull final K key2)
		{
		return underlyingIntMap.get(keys.indexOf(key1), keys.indexOf(key2));
		}

	public InsertionTrackingSet<K> getKeys()
		{
		return keys;
		}

	public int numPairs()
		{
		return underlyingIntMap.numPairs();
		}

	public boolean isEmpty()
		{
		return underlyingIntMap.isEmpty();
		}

	public V get(@NotNull final UnorderedPair<K> keypair)
		{
		return underlyingIntMap.get(keys.indexOf(keypair.getKey1()), keys.indexOf(keypair.getKey2()));
		}

	public synchronized void put(@NotNull final UnorderedPair<K> keypair, @NotNull final V d)
		{
		final Integer i1 = keys.indexOfWithAdd(keypair.getKey1());
		final Integer i2 = keys.indexOfWithAdd(keypair.getKey2());
		underlyingIntMap.put(i1, i2, d);
		}

	public synchronized void put(@NotNull final K key1, @NotNull final K key2, @NotNull final V d)
		{
		final Integer i1 = keys.indexOfWithAdd(key1);
		final Integer i2 = keys.indexOfWithAdd(key2);
		underlyingIntMap.put(i1, i2, d);
		}

	public int numKeys()
		{
		return keys.size();
		}

	public void addKey(@NotNull final K key1)
		{
		keys.add(key1);
		}

	public void putAll(@NotNull final Map<UnorderedPair<K>, V> map)
		{
		for (@NotNull Map.Entry<UnorderedPair<K>, V> entry : map.entrySet())
			{
			UnorderedPair<K> p = entry.getKey();
			V v = entry.getValue();
			put(p.getKey1(), p.getKey2(), v);
			}
		}

	/**
	 * Add a value entry using the integer indexes; just assume that the matching keys exist
	 *
	 * @param map
	 */
	public void putAllInt(@NotNull final Map<UnorderedPair<Integer>, V> map)
		{
		for (@NotNull Map.Entry<UnorderedPair<Integer>, V> entry : map.entrySet())
			{
			UnorderedPair<Integer> p = entry.getKey();
			V v = entry.getValue();
			putInt(p, v);
			}
		}


	/**
	 * Add a value entry using the integer indexes; just assume that the matching keys exist.  This is not synchronized, on
	 * the theory that the underlying map should handle that.
	 */
	public void putInt(@NotNull final UnorderedPair<Integer> pair, final V v)
		{
		// PERF
		assert keys.containsIndex(pair.getKey1());
		assert keys.containsIndex(pair.getKey2());
		underlyingIntMap.put(pair, v);
		}

	public void removeAll(@NotNull final Collection<K> keys)
		{
		for (@NotNull K key : keys)
			{
			remove(key);
			}
		}

	public synchronized int remove(@NotNull final K b)
		{
		sanityCheck();

		Integer i = keys.indexOf(b);
		if (i == null)
			{
			logger.warn("requested removal of nonexistent cluster" + i + ":" + b);
			sanityCheck();
			}
		if (i != null)
			{
			int removed = underlyingIntMap.remove(i);

			underlyingIntMap.removalSanityCheck(i, underlyingIntMap.getKeys());

			boolean keyRemoved = keys.remove(b);
			assert keyRemoved;

			underlyingIntMap.removalSanityCheck(i, keys.getIndexes());
			sanityCheck();
			return removed;
			}
		assert keys.indexOf(b) == null;
		return 0;
		}

	@NotNull
	public Set<Map.Entry<UnorderedPair<K>, V>> entrySet()
		{
		throw new NotImplementedException();
		}

	public int getNextId()
		{
		return keys.getNextId();
		}

	@NotNull
	public Set<Iterator<Map.Entry<UnorderedPair<K>, V>>> entryBlockIterators(final int i)
		{
		throw new NotImplementedException();
		}


	@NotNull
	public ConcurrentLinkedQueue<Map.Entry<UnorderedPair<Integer>, V>> integerEntriesQueue()
		{
		return underlyingIntMap.entriesQueue();
		}

	public synchronized void sanityCheck()
		{
		underlyingIntMap.sanityCheck();

		assert keys.getIndexes().equals(underlyingIntMap.getKeys());

		Set<Integer> referencedKeys = new HashSet<Integer>();

		for (Map.Entry<UnorderedPair<Integer>, V> entry : underlyingIntMap.entrySet())
			{
			UnorderedPair<Integer> p = entry.getKey();
			referencedKeys.add(p.getKey1());
			referencedKeys.add(p.getKey2());
			}

		for (Integer key : referencedKeys)
			{
			assert keys.containsIndex(key);
			assert keys.get(key) != null;
			}
		}
	}
