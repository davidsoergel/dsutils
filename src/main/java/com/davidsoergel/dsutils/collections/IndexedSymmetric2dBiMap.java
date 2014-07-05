/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A data structure that maps pairs of keys to values, and is queryable in both directions (i.e., also in the
 * value->keys direction).  The order of the keys is unimportant.  The key pairs are stored sorted by the associated
 * values, so eg. the pair with the lowest value can be queried.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class IndexedSymmetric2dBiMap<K extends Comparable<K> & Serializable, V extends Comparable<V> & Serializable>
		//	implements Serializable
	{
// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(IndexedSymmetric2dBiMap.class);

	@NotNull
	List<K> keys = new ArrayList<K>();

	Symmetric2dBiMap<Integer, V> underlyingIntMap;
	}
