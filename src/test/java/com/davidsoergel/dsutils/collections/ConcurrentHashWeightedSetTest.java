/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import com.davidsoergel.dsutils.math.MathUtils;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class ConcurrentHashWeightedSetTest
	{
	Map<String, Double> mapA;
	Map<String, Double> mapB;


	@BeforeMethod
	public void setUp()
		{
		mapA = new HashMap<String, Double>();
		mapA.put("a", .1);
		mapA.put("b", .2);
		mapA.put("e", .5);
		mapA.put("f", .06);


		mapB = new HashMap<String, Double>();
		mapB.put("c", .3);
		mapB.put("d", .4);
		mapB.put("f", .6);
		}

	@Test
	public void atomicConstructorWorks()
		{
		@NotNull WeightedSet<String> a = new ConcurrentHashWeightedSet(mapA, 1);

		assert a.getItemCount() == 1;
		assert a.get("a") == .1;
		assert a.get("b") == .2;
		assert a.get("e") == .5;
		assert a.get("f") == .06;

		@NotNull Map<String, Double> mapAextracted = a.getItemNormalizedMap();

		assert DSCollectionUtils.isEqualMap(mapA, mapAextracted);
		}

	@Test
	public void addAllWorks()
		{
		@NotNull MutableWeightedSet<String> a = new ConcurrentHashWeightedSet(mapA, 1);
		@NotNull MutableWeightedSet<String> b = new ConcurrentHashWeightedSet(mapB, 1);

		a.addAll(b);

		assert a.getItemCount() == 2;

		assert a.get("a") == .1;
		assert a.get("b") == .2;
		assert a.get("c") == .3;
		assert a.get("d") == .4;
		assert a.get("e") == .5;
		assert MathUtils.equalWithinFPError(a.get("f"), .66);
		}


	@Test
	public void addAndGetNormalizedWorks()
		{
		@NotNull MutableWeightedSet<String> a = new ConcurrentHashWeightedSet(mapA, 1);
		@NotNull MutableWeightedSet<String> b = new ConcurrentHashWeightedSet(mapB, 1);

		a.addAll(b);

		@NotNull Map<String, Double> mapAextracted = a.getItemNormalizedMap();


		assert mapAextracted.get("a") == (.1 * 1 + 0. * 1) / 2.;
		assert mapAextracted.get("b") == (.2 * 1 + 0. * 1) / 2.;
		assert mapAextracted.get("c") == (.0 * 1 + .3 * 1) / 2.;
		assert mapAextracted.get("d") == (.0 * 1 + .4 * 1) / 2.;
		assert mapAextracted.get("e") == (.5 * 1 + 0. * 1) / 2.;
		assert mapAextracted.get("f") == (.06 * 1 + .6 * 1) / 2.;

		a.addAll(b);
		a.addAll(b);

		//		Map<String, Double>
		mapAextracted = a.getItemNormalizedMap();

		assert mapAextracted.get("a") == (.1 * 1 + 0. * 3) / 4.;
		assert mapAextracted.get("b") == (.2 * 1 + 0. * 3) / 4.;
		assert mapAextracted.get("c") == (.0 * 1 + .3 * 3) / 4.;
		assert mapAextracted.get("d") == (.0 * 1 + .4 * 3) / 4.;
		assert mapAextracted.get("e") == (.5 * 1 + 0. * 3) / 4.;
		assert mapAextracted.get("f") == (.06 * 1 + .6 * 3) / 4.;
		}
	}
