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

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
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

	private Multimap<K, KeyPair<K>> keyToKeyPairs = Multimaps.newHashMultimap();

	private Map<KeyPair<K>, V> keyPairToValue = new HashMap<KeyPair<K>, V>();

	private SortedSet<KeyPair<K>> keyPairsInValueOrder = new TreeSet<KeyPair<K>>(new Comparator<KeyPair<K>>()
	{
	public int compare(KeyPair<K> o1, KeyPair<K> o2)
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
		return v1.compareTo(v2);
		}
	});


	/*SymmetricHashMap2D<LengthWeightHierarchyNode<T>, LengthWeightHierarchyNode<T>, Double> theDistanceMatrix =
						new SymmetricHashMap2D<LengthWeightHierarchyNode<T>, LengthWeightHierarchyNode<T>, Double>();*/


	public void put(K key1, K key2, V d)
		{
		put(getOrCreateKeyPair(key1, key2), d);
		}

	private KeyPair getOrCreateKeyPair(K key1, K key2)
		{
		KeyPair<K> pair = getKeyPair(key1, key2);
		if (pair == null)
			{
			pair = new KeyPair(key1, key2);
			keyToKeyPairs.put(key1, pair);
			keyToKeyPairs.put(key2, pair);
			}
		return pair;
		}

	private KeyPair<K> getKeyPair(K key1, K key2)
		{
		try
			{
			return DSCollectionUtils.intersection(keyToKeyPairs.get(key1), keyToKeyPairs.get(key2)).iterator().next();
			}
		catch (NoSuchElementException e)
			{
			return null;
			}
		}

	private void put(KeyPair keyPair, V d)
		{
		V oldValue = keyPairToValue.get(keyPair);
		if (oldValue != null)
			{
			keyPairsInValueOrder.remove(keyPair);
			keyPairToValue.remove(keyPair);
			//valueToKeyPair.remove(oldValue, keyPair);
			}
		keyPairToValue.put(keyPair, d);
		keyPairsInValueOrder.add(keyPair);

		//valueToKeyPair.put(d, keyPair);
		}

	private KeyPair<K> getKeyPairWithSmallestValue()
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
		return get(getKeyPair(key1, key2));
		}

	private V get(KeyPair keyPair)
		{
		return keyPairToValue.get(keyPair);
		}

	/**
	 * Remove all key pairs and values associated with the given key
	 *
	 * @param b
	 */
	public void remove(K b)
		{
		for (KeyPair<K> pair : keyToKeyPairs.get(b))
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
			   logger.debug(e);
			   e.printStackTrace();
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


	/**
	 * Represent a pair of keys, guaranteeing that node1 <= node2 for the sake of symmetry
	 */
	private class KeyPair<K> implements Comparable
		{
		private K key1;
		private K key2;

		private KeyPair(K key1, K key2)
			{
			if (key1.hashCode() <= key2.hashCode())
				//if (node1.getValue().compareTo(node2.getValue()) <= 0)
				{
				this.key1 = key1;
				this.key2 = key2;
				}
			else
				{
				this.key1 = key2;
				this.key2 = key1;
				}
			}

		public boolean equals(Object o)
			{
			if (this == o)
				{
				return true;
				}
			if (!(o instanceof KeyPair))
				{
				return false;
				}

			KeyPair keyPair = (KeyPair) o;

			if (key1 != null ? !key1.equals(keyPair.key1) : keyPair.key1 != null)
				{
				return false;
				}
			if (key2 != null ? !key2.equals(keyPair.key2) : keyPair.key2 != null)
				{
				return false;
				}

			return true;
			}

		public int hashCode()
			{
			int result;
			result = (key1 != null ? key1.hashCode() : 0);
			result = 31 * result + (key2 != null ? key2.hashCode() : 0);
			return result;
			}

		public K getKey1()
			{
			return key1;
			}

		public K getKey2()
			{
			return key2;
			}

		public int compareTo(Object o)
			{
			return key1.toString().compareTo(o.toString());
			}

		public String toString()
			{
			return "[" + key1 + ", " + key2 + "]";
			}
		}
	}
