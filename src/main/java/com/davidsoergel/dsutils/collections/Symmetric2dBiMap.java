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
