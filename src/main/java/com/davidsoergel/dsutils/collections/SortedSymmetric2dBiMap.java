package com.davidsoergel.dsutils.collections;

import java.io.Serializable;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface SortedSymmetric2dBiMap<K extends Comparable<K> & Serializable, V extends Comparable<V> & Serializable>
		extends Symmetric2dBiMap<K, V>
	{
	K getKey1WithSmallestValue();

	K getKey2WithSmallestValue();

	OrderedPair<UnorderedPair<K>, V> getKeyPairAndSmallestValue();

	UnorderedPair<K> getKeyPairWithSmallestValue();

	V getSmallestValue();
	}
