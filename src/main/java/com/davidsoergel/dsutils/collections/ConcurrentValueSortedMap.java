/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class ConcurrentValueSortedMap<K extends Comparable<K>, V extends Comparable<V>> implements Serializable
	{
// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(ConcurrentValueSortedMap.class);
	/*
	private Map<UnorderedPair<K>, V> keyPairToValue = new HashMap<UnorderedPair<K>, V>();

	private SortedSet<UnorderedPair<K>> keyPairsInValueOrder =
			new TreeSet<UnorderedPair<K>>(new Comparator<UnorderedPair<K>>()
			{
			public int compare(UnorderedPair<K> o1, UnorderedPair<K> o2)
				{
				V v1 = keyPairToValue.get(o1);
				V v2 = keyPairToValue.get(o2);
				if (v1 == null)
					{
					if (v2 == null)
						{
						return 0;
						}
					return 1;
					}
				if (v2 == null)
					{
					return -1;
					}
				int result = v1.compareTo(v2);
				return result == 0 ? o1.compareTo(o2) : result;
				}
			});*/

	@NotNull
	private final ConcurrentHashMap<K, V> map;

	private final ConcurrentSkipListSet<OrderedPair<K, V>> sortedPairs;

	/**
	 * The keys and values themselves are not cloned
	 *
	 * @param cloneFrom
	 */
	public ConcurrentValueSortedMap(@NotNull final ConcurrentValueSortedMap<K, V> cloneFrom)
		{
		//map.putAll(cloneFrom.map);
		map = new ConcurrentHashMap<K, V>(cloneFrom.getMap());

		// PERF does this spend a lot of time re-sorting an already sorted set?
		//	sortedPairs.addAll(cloneFrom.sortedPairs);
		sortedPairs = cloneFrom.getSortedPairs().clone();
		}

	public ConcurrentValueSortedMap()
		{
		sortedPairs = new ConcurrentSkipListSet<OrderedPair<K, V>>(new OrderedPair.ValuesPrimaryComparator());
		map = new ConcurrentHashMap<K, V>();
		}

	public ConcurrentSkipListSet<OrderedPair<K, V>> getSortedPairs()
		{
		return sortedPairs;
		}

	@NotNull
	public ConcurrentHashMap<K, V> getMap()
		{
		return map;
		}
// -------------------------- OTHER METHODS --------------------------

	/*new Comparator<OrderedPair<K, V>>()
			{
			public int compare(final OrderedPair<K, V> o1, final OrderedPair<K, V> o2)
				{
				final V v1 = o1.getKey2();
				final V v2 = o2.getKey2();
				int i = v1.compareTo(v2);

				// never return 0 on the basis of the value alone, because equal items might be dropped from the TreeSet.
				if (i == 0)
					{
					final K k1 = o1.getKey1();
					final K k2 = o2.getKey1();
					i = k1.compareTo(k2);

					// well, OK, if both the key and the value are equal then so be it
					}
				return i;
				}
			});
*/

	public Set<Map.Entry<K, V>> entrySet()
		{
		return map.entrySet();
		}

	public K firstKey()
		{
		return sortedPairs.first().getKey1();
		}

	public OrderedPair<K, V> firstPair()
		{
		return sortedPairs.first();
		}

	public V firstValue()
		{
		return sortedPairs.first().getKey2();
		}

	public K lastKey()
		{
		return sortedPairs.last().getKey1();
		}

	public OrderedPair<K, V> lastPair()
		{
		return sortedPairs.last();
		}

	public V lastValue()
		{
		return sortedPairs.last().getKey2();
		}


	public V get(final K key)
		{
		return map.get(key);
		}


	// should be synchronized?
	public void put(@NotNull final K key, @NotNull final V val)
		{
		remove(key);

		//PERF debugging cruft
		//V oldValue =
		map.put(key, val);
		//boolean addedPair =
		sortedPairs.add(new OrderedPair<K, V>(key, val));
		//if (!addedPair)
		//	{
		//	logger.warn(sortedPairs.contains(new OrderedPair<K, V>(key, val)));
		//	}

		//	sanityCheck();
		}

	public synchronized void remove(@NotNull final K key)
		{
		V val = map.get(key);
		if (val != null)
			{
			remove(key, val);
			}
		}

	public synchronized void remove(@NotNull final K key, @NotNull final V val)
		{
		//V val = map.get(key);
		//if (val != null)
		//	{
		V removed = map.remove(key);
		assert removed != null;

		boolean sortedRemoved = sortedPairs.remove(new OrderedPair<K, V>(key, val));
		assert sortedRemoved;
		//	}
		//	sanityCheck();
		}

	private synchronized void sanityCheck()
		{
		assert map.size() == sortedPairs.size();
		}

	public int size()
		{
		return sortedPairs.size();
		}

	public boolean isEmpty()
		{
		return sortedPairs.isEmpty();
		}

	@NotNull
	public ConcurrentLinkedQueue<Map.Entry<K, V>> entriesQueue()
		{
		//ConcurrentHashMap<K,V> result = new ConcurrentHashMap<K, V>();
		//result.putAll(map);
		@NotNull ConcurrentLinkedQueue<Map.Entry<K, V>> result = new ConcurrentLinkedQueue<Map.Entry<K, V>>();
		result.addAll(map.entrySet());
		return result;
//		ConcurrentSkipListSet<Map.Entry<K, V>>(entrySet());
		}

/*	public Set<Iterator<Map.Entry<K, V>>> entryBlockIterators(final int i)
		{

		}*/
	}
