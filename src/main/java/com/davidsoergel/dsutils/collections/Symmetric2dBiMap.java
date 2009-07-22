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

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A data structure that maps pairs of keys to values, and is queryable in both directions (i.e., also in the
 * value->keys direction).  The order of the keys is unimportant.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class Symmetric2dBiMap<K, V extends Comparable>
	{
	private static final Logger logger = Logger.getLogger(Symmetric2dBiMap.class);

	// really we wanted a SortedBiMultimap or something, but lacking that, we just store the inverse map explicitly.

	//private TreeMultimap<V, KeyPair<K>> valueToKeyPair = new TreeMultimap<V, KeyPair<K>>();

	//private V smallestValue;
	//private KeyPair<K> keyPairWithSmallestValue;

	private Multimap<K, UnorderedPair<K>> keyToKeyPairs = HashMultimap.create();
	// Multimaps.synchronizedSetMultimap(HashMultimap.create());

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
			});


	/*SymmetricHashMap2D<LengthWeightHierarchyNode<T>, LengthWeightHierarchyNode<T>, Double> theDistanceMatrix =
						new SymmetricHashMap2D<LengthWeightHierarchyNode<T>, LengthWeightHierarchyNode<T>, Double>();*/


	public synchronized void put(K key1, K key2, V d)
		{
		UnorderedPair<K> pair = new UnorderedPair<K>(key1, key2);
		put(pair, d);
		keyToKeyPairs.put(key1, pair);
		keyToKeyPairs.put(key2, pair);
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
	private synchronized void put(UnorderedPair<K> keyPair, V d)
		{
		//V oldValue = keyPairToValue.remove(keyPair);
		//if (oldValue != null)
		//	{


		//	keyPairToValue.remove(keyPair);
		//valueToKeyPair.remove(oldValue, keyPair);
		//	}


		keyPairToValue.put(keyPair, d);

		// update the sort order
		keyPairsInValueOrder.remove(keyPair);
		keyPairsInValueOrder.add(keyPair);

		//valueToKeyPair.put(d, keyPair);
		}

	private UnorderedPair<K> getKeyPairWithSmallestValue()
		{
		return keyPairsInValueOrder.first();//valueToKeyPair.get(getSmallestValue()).first();
		}

	public V getSmallestValue()
		{
		return keyPairToValue
				.get(getKeyPairWithSmallestValue());//valueToKeyPair.keySet().first();// distanceToPair is sorted
		}

	public K getKey1WithSmallestValue()
		{
		return getKeyPairWithSmallestValue().getKey1();
		}

	public K getKey2WithSmallestValue()
		{
		return getKeyPairWithSmallestValue().getKey2();
		}

	public V get(K key1, K key2)
		{
		return get(new UnorderedPair<K>(key1, key2)); //getKeyPair(key1, key2));
		}

	private V get(UnorderedPair<K> keyPair)
		{
		return keyPairToValue.get(keyPair);
		}

	/**
	 * Remove all key pairs and values associated with the given key
	 *
	 * @param b
	 */
	public synchronized void remove(K b)
		{
		for (UnorderedPair<K> pair : keyToKeyPairs.get(b))
			{
			keyPairsInValueOrder.remove(pair);
			keyPairToValue.remove(pair);

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
			}
		keyToKeyPairs.removeAll(b);
		}

	public Set<K> getActiveKeys()
		{
		return keyToKeyPairs.keySet();
		}

	public int numKeys()
		{
		return keyToKeyPairs.keySet().size();
		}

	public int numPairs()
		{
		return keyPairsInValueOrder.size();
		}

	public Collection<V> values()
		{
		return keyPairToValue.values();
		}

	public synchronized void putAll(final Map<UnorderedPair<K>, V> result)
		{
		for (Map.Entry<UnorderedPair<K>, V> entry : result.entrySet())
			{
			UnorderedPair<K> pair = entry.getKey();
			keyToKeyPairs.put(pair.getKey1(), pair);
			keyToKeyPairs.put(pair.getKey2(), pair);
			put(pair, entry.getValue());
			}
		}
	}
