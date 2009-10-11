package com.davidsoergel.dsutils.collections;

import java.util.Map;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class Symmetric2dBiMapWithDefault<K extends Comparable<K>, V extends Comparable<V>>
		extends Symmetric2dBiMap<K, V>
	{
	private final V defaultValue;

	public Symmetric2dBiMapWithDefault(final V defaultValue)
		{
		this.defaultValue = defaultValue;
		}

	@Override
	public V get(final K key1, final K key2)
		{
		V result = super.get(key1, key2);
		if (result == null)
			{
			result = defaultValue;
			}
		return result;
		}

	@Override
	public void put(final K key1, final K key2, final V d)
		{
		if (d.equals(defaultValue))
			{
			//ensure that the mapping exists even if it's empty
			keyToKeyPairs.get(key1);
			keyToKeyPairs.get(key2);
			}
		else
			{
			super.put(key1, key2, d);
			}
		}

	@Override


	public synchronized void putAll(final Map<UnorderedPair<K>, V> result)
		{
		sanityCheck();
		for (Map.Entry<UnorderedPair<K>, V> entry : result.entrySet())
			{
			final V value = entry.getValue();
			UnorderedPair<K> pair = entry.getKey();
			final K key1 = pair.getKey1();
			final K key2 = pair.getKey2();

			if (value.equals(defaultValue))
				{
				//ensure that the mapping exists even if it's empty
				keyToKeyPairs.get(key1);
				keyToKeyPairs.get(key2);
				}
			else
				{
				keyToKeyPairs.put(key1, pair);
				keyToKeyPairs.put(key2, pair);
				putPairAndReSort(pair, value);
				}
			}
		sanityCheck();
		}
	}
