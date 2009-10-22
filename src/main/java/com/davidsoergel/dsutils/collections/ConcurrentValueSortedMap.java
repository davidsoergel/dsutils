package com.davidsoergel.dsutils.collections;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
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

	private final Map<K, V> map = new ConcurrentHashMap<K, V>();

	private final SortedSet<OrderedPair<K, V>> sortedPairs =
			new ConcurrentSkipListSet<OrderedPair<K, V>>(new OrderedPair.ValuesPrimaryComparator());

	/**
	 * The keys and values themselves are not cloned
	 *
	 * @param cloneFrom
	 */
	public ConcurrentValueSortedMap(final ConcurrentValueSortedMap<K, V> cloneFrom)
		{
		map.putAll(cloneFrom.map);

		// PERF does this spend a lot of time re-sorting an already sorted set?
		sortedPairs.addAll(cloneFrom.sortedPairs);
		}

	public ConcurrentValueSortedMap()
		{
		}

	public SortedSet<OrderedPair<K, V>> getSortedPairs()
		{
		return sortedPairs;
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

	public V get(final K key)
		{
		return map.get(key);
		}


	// should be synchronized?
	public void put(final K key, final V val)
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

	public synchronized void remove(final K key)
		{
		V val = map.get(key);
		if (val != null)
			{
			remove(key, val);
			}
		}

	public synchronized void remove(final K key, final V val)
		{
		//V val = map.get(key);
		//if (val != null)
		//	{
		map.remove(key);
		sortedPairs.remove(new OrderedPair<K, V>(key, val));
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

	public ConcurrentSkipListSet<Map.Entry<K, V>> entriesCopy()
		{
		return new ConcurrentSkipListSet<Map.Entry<K, V>>(entrySet());
		}

/*	public Set<Iterator<Map.Entry<K, V>>> entryBlockIterators(final int i)
		{

		}*/
	}
