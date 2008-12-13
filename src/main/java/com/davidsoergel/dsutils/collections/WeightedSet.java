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

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

/**
 * A Set that associates a double value with each key.  This is something like a MultiSet, except that it maps to
 * doubles instead of ints; so it's effectively a bunch of convenience methods for managing a Map<T, Double>.  Also,
 * keeps track of the number of "items", for the purpose of normalizing the double values.
 * <p/>
 * The purpose of this is to be able to add up a bunch of items, each of which has a set of weighted properties, and
 * then to get the average weight for each property in the set of all the items.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface WeightedSet<T extends Comparable> //extends Map<T, Double>
	{
	/**
	 * Increment the indicated value in this WeightedSet by the values in the argument.  Does not alter the number of
	 * items; use incrementItems() for that (since there may be multiple add() calls for a single item).
	 *
	 * @param increment
	 */
	void add(T key, double increment);

	void incrementItems();

	void decrementItems();


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
	 * Decrement all the values in this WeightedSet by the values in the argument, and decrease the number of items
	 * accordingly.
	 *
	 * @param increment
	 */
	void removeAll(WeightedSet<T> increment);

	/**
	 * Given a key, returns the mean of the values provided for that key so far, normalized by the total number of items.
	 * If addAll() was called and provided no value for the requested key, that is considered as providing the value "0"
	 * with multiplicity given by the number of items in the WeightedSet being added, and so does reduce the mean.
	 *
	 * @param key
	 * @return
	 */
	double getNormalized(T key);

	/**
	 * Returns a map from keys to the mean of the values provided for that key so far, normalized by the total number of
	 * items.
	 *
	 * @return
	 */
	Map<T, Double> getItemNormalizedMap();

	/**
	 * Returns the number of items represented by this WeightedSet.  This may differ both from the number of keys and from
	 * the sum of the values.  When WeightedSets are aggregated via addAll, their item counts are added together.
	 *
	 * @return the itemCount
	 */
	int getItemCount();

	//	Map.Entry<T, Double> getDominantEntryInSet(Set<T> mutuallyExclusiveLabels);

	T getDominantKeyInSet(Set<T> keys);

	Set<Map.Entry<T, Double>> entrySet();

	double get(T s);

	//int size();

	double getWeightSum();

	Set<T> keySet();

	SortedSet<T> keysInDecreasingWeightOrder(Comparator secondarySort);

	void multiplyBy(int multiplier);

	//Map<String, Double> getBackingMap();
	}
