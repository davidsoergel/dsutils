/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

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
