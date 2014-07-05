/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SortedSymmetric2dBiMapWithDefault<K extends Comparable<K> & Serializable, V extends Comparable<V> & Serializable>
		extends SortedSymmetric2dBiMapImpl<K, V>
	{

	protected V defaultValue;  // should be final but custom deserialization doesn't allow it

	public SortedSymmetric2dBiMapWithDefault()
		{
		}

	public SortedSymmetric2dBiMapWithDefault(final V defaultValue)
		{
		this.defaultValue = defaultValue;
		}

	public SortedSymmetric2dBiMapWithDefault(final V defaultValue, Collection<K> keys)
		{
		this.defaultValue = defaultValue;
		keyToKeyPairs.addKeys(keys);
		}

	public SortedSymmetric2dBiMapWithDefault(@NotNull SortedSymmetric2dBiMapWithDefault<K, V> cloneFrom)
		{
		super(cloneFrom);
		this.defaultValue = cloneFrom.defaultValue;
		}


	@Override
	public V get(@NotNull final K key1, @NotNull final K key2)
		{
		V result = super.get(key1, key2);
		if (result == null)
			{
			result = defaultValue;
			}
		return result;
		}

	@Override
	public synchronized void put(@NotNull final K key1, @NotNull final K key2, @NotNull final V d)
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
	public synchronized void putAll(@NotNull final Map<UnorderedPair<K>, V> result)
		{
		// sanityCheck();
		for (@NotNull Map.Entry<UnorderedPair<K>, V> entry : result.entrySet())
			{
			final V value = entry.getValue();
			UnorderedPair<K> pair = entry.getKey();
			@NotNull final K key1 = pair.getKey1();
			@NotNull final K key2 = pair.getKey2();

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
				keyPairToValueSorted.put(pair, value);
				}
			}
		// 	sanityCheck();
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

	public void setDefaultValue(final V defaultValue)
		{
		this.defaultValue = defaultValue;
		}

	public void addKeys(final Collection<K> keys)
		{
		keyToKeyPairs.addKeys(keys);
		}
	}
