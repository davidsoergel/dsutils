/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
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
 * <p/>
 * Note there is no requirement or expectation that the keys be mutually exclusive and thus that the double values can
 * be normalized to proabilities.  On the contrary, the values for each key are totally independent.  In a way it should
 * be thought of as a Map<T, Collection<Double>> that just provides the mean of the collection, weighted according to
 * the number of "items".
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface WeightedSet<T> //extends Map<T, Double>
	{

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
	@NotNull
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

	//T getSecondKeyInSet(Set<T> keys);

	Set<Map.Entry<T, Double>> entrySet();

	double get(T s);

	//int size();

	//double getWeightSum();

	Set<T> keySet();

	@NotNull
	SortedSet<T> keysInDecreasingWeightOrder();

	@NotNull
	SortedSet<T> keysInDecreasingWeightOrder(Comparator<T> secondarySort);

	//void multiplyBy(int multiplier);

//	void multiplyBy(double multiplier);

	//Map<String, Double> getBackingMap();

	boolean isEmpty();

	T getDominantKey();

	@NotNull
	List<Double> weightsInDecreasingOrder();

	// void retainKeys(Collection<T> okKeys);


	@NotNull
	WeightedSet<T> extractWithKeys(Collection<T> okKeys);

	Map<T, Double> getMap();
	}
