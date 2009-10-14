package com.davidsoergel.dsutils.collections;

import java.io.Serializable;
import java.util.Map;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class Symmetric2dBiMapWithDefault<K extends Comparable<K> & Serializable, V extends Comparable<V> & Serializable>
		extends Symmetric2dBiMap<K, V>
	{

	protected V defaultValue;  // should be final but custom deserialization doesn't allow it

	public Symmetric2dBiMapWithDefault()
		{
		}

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
	public synchronized void put(final K key1, final K key2, final V d)
		{
		if (d.equals(defaultValue))
			{
			//ensure that the mapping exists even if it's empty
			addKey(key1);
			addKey(key2);
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

			assert !key1.equals(key2);

			if (value.equals(defaultValue))
				{
				//ensure that the mapping exists even if it's empty
				addKey(key1);
				addKey(key2);
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

	public V getDefaultValue()
		{
		return defaultValue;
		}

	int maxId = 0;

	public void setMaxId(final int maxId)
		{
		this.maxId = maxId;
		}

	public int getMaxId()
		{
		return maxId;
		}
	}
