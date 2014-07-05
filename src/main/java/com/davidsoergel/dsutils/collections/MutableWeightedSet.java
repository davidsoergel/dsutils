/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import java.util.Map;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface MutableWeightedSet<T> extends WeightedSet<T>
	{
	/**
	 * Increment the indicated value in this WeightedSet by the values in the argument.  Does not alter the number of
	 * items; use incrementItems() for that (since there may be multiple add() calls for a single item).
	 *
	 * @param increment
	 */

/*	void add(T key, double increment);

	void incrementItems();

	void decrementItems();
*/

	void add(Map<T, Double> weights);

	void put(Map<T, Double> weights);

	void add(Map<T, Double> weights, int items);

	void put(Map<T, Double> weights, int items);

	void add(T key, double increment, int items);

	void put(T key, double val, int items);

	public void incrementItemCount(int i);

	/**
	 * Decrement the indicated value in this WeightedSet by the values in the argument.  Does not alter the number of
	 * items; use incrementItems() for that (since there may be multiple add() calls for a single item).
	 *
	 * @param decrement
	 */
	void remove(T key, double decrement);

	/**
	 * Increment all the values in this WeightedSet by the values in the argument, and increase the number of items
	 * accordingly.
	 *
	 * @param increment
	 */
	void addAll(WeightedSet<T> increment);

	/**
	 * Increment all the values in this WeightedSet by the values in the argument, and increase the number of items
	 * accordingly.
	 *
	 * @param increment
	 */
	void addAll(WeightedSet<T> increment, double weight);

	/**
	 * Decrement all the values in this WeightedSet by the values in the argument, and decrease the number of items
	 * accordingly.
	 *
	 * @param increment
	 */
	void removeAll(WeightedSet<T> increment);

	void clear();
	}
