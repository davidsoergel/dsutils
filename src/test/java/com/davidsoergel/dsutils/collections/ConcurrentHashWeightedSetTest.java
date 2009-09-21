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

import com.davidsoergel.dsutils.math.MathUtils;
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
		WeightedSet<String> a = new ConcurrentHashWeightedSet(mapA, 1);

		assert a.getItemCount() == 1;
		assert a.get("a") == .1;
		assert a.get("b") == .2;
		assert a.get("e") == .5;
		assert a.get("f") == .06;

		Map<String, Double> mapAextracted = a.getItemNormalizedMap();

		assert DSCollectionUtils.isEqualMap(mapA, mapAextracted);
		}

	@Test
	public void addAllWorks()
		{
		MutableWeightedSet<String> a = new ConcurrentHashWeightedSet(mapA, 1);
		MutableWeightedSet<String> b = new ConcurrentHashWeightedSet(mapB, 1);

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
		MutableWeightedSet<String> a = new ConcurrentHashWeightedSet(mapA, 1);
		MutableWeightedSet<String> b = new ConcurrentHashWeightedSet(mapB, 1);

		a.addAll(b);

		Map<String, Double> mapAextracted = a.getItemNormalizedMap();


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
