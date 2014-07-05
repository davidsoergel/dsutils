/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface Symmetric2dBiMap<K extends Comparable<K> & Serializable, V extends Comparable<V> & Serializable>
	{
	V get(K key1, K key2);

	Collection<K> getKeys();

	int numPairs();

	void put(K key1, K key2, V d);

	int numKeys();

	void addKey(K key1);

	void putAll(Map<UnorderedPair<K>, V> result);

	void removeAll(Collection<K> keys);

	int remove(K b);

	Set<Map.Entry<UnorderedPair<K>, V>> entrySet();
	}
