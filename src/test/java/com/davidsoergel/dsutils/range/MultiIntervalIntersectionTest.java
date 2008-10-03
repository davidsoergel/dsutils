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


package com.davidsoergel.dsutils.range;

import com.davidsoergel.dsutils.math.LongRational;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;


/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class MultiIntervalIntersectionTest//extends TestCase
	{
	// -------------------------- OTHER METHODS --------------------------

	@Test
	public void multiIntervalIntersectionOfDoublesWorks()
		{
		Set<Set<BasicInterval<Double>>> a = new HashSet<Set<BasicInterval<Double>>>();
		Set<BasicInterval<Double>> as = new HashSet<BasicInterval<Double>>();
		as.add(new BasicInterval<Double>(0.123, 0.456, true, true));
		as.add(new BasicInterval<Double>(2.2, 3.3, true, true));
		as.add(new BasicInterval<Double>(4.4, 5.5, true, true));
		a.add(as);

		as = new HashSet<BasicInterval<Double>>();
		as.add(new BasicInterval<Double>(0.1, 0.5, true, true));
		as.add(new BasicInterval<Double>(2.4, 3.1, true, true));
		as.add(new BasicInterval<Double>(4.4, 5.5, true, true));
		a.add(as);

		as = new HashSet<BasicInterval<Double>>();
		as.add(new BasicInterval<Double>(0., 1., true, true));
		as.add(new BasicInterval<Double>(2.2, 2.3, true, true));
		as.add(new BasicInterval<Double>(5.45, 5.5, true, true));
		a.add(as);

		Set<Interval<Double>> result = new MultiIntervalIntersection<Double>(a);

		assert result.size() == 2;
		assert result.contains(new BasicInterval<Double>(0.123, 0.456, true, true));
		assert result.contains(new BasicInterval<Double>(5.45, 5.5, true, true));
		}

	@Test
	public void multiIntervalIntersectionOfDoublesDealsWithOverlappingInputsCorrectly()
		{
		Set<Set<BasicInterval<Double>>> a = new HashSet<Set<BasicInterval<Double>>>();
		Set<BasicInterval<Double>> as = new HashSet<BasicInterval<Double>>();
		as.add(new BasicInterval<Double>(0.123, 0.456, true, true));
		as.add(new BasicInterval<Double>(.4, 5., true, true));
		as.add(new BasicInterval<Double>(4.4, 5.5, true, true));
		a.add(as);

		as = new HashSet<BasicInterval<Double>>();
		as.add(new BasicInterval<Double>(0.1, 0.5, true, true));
		as.add(new BasicInterval<Double>(0.5, 3.1, false, true));
		as.add(new BasicInterval<Double>(4.4, 5.5, true, false));
		a.add(as);

		as = new HashSet<BasicInterval<Double>>();
		as.add(new BasicInterval<Double>(0., 1., false, false));
		as.add(new BasicInterval<Double>(1., 2.3, false, true));
		as.add(new BasicInterval<Double>(5.45, 5.5, true, true));
		a.add(as);

		Set<Interval<Double>> result = new MultiIntervalIntersection<Double>(a);

		assert result.size() == 3;
		assert result.contains(new BasicInterval<Double>(0.123, 1., true, false));
		assert result.contains(new BasicInterval<Double>(1., 2.3, false, true));
		assert result.contains(new BasicInterval<Double>(5.45, 5.5, true, false));
		}

	@Test
	public void multiIntervalClosedIntersectionOfLongRationalsWorks()
		{
		Set<Set<BasicInterval<LongRational>>> a = new HashSet<Set<BasicInterval<LongRational>>>();
		Set<BasicInterval<LongRational>> as = new HashSet<BasicInterval<LongRational>>();
		as.add(new BasicInterval<LongRational>(new LongRational(2, 7000), new LongRational(3, 7000), true, true));
		as.add(new BasicInterval<LongRational>(new LongRational(1, 5), new LongRational(2, 5), true, true));
		as.add(new BasicInterval<LongRational>(new LongRational(5, 6), new LongRational(1, 1), true, true));
		a.add(as);

		as = new HashSet<BasicInterval<LongRational>>();
		as.add(new BasicInterval<LongRational>(new LongRational(0, 5), new LongRational(4, 7000), true, true));
		as.add(new BasicInterval<LongRational>(new LongRational(4, 10), new LongRational(1, 2), true, true));
		as.add(new BasicInterval<LongRational>(new LongRational(118, 119), new LongRational(234, 234), true, true));
		a.add(as);

		as = new HashSet<BasicInterval<LongRational>>();
		as.add(new BasicInterval<LongRational>(new LongRational(0, 5), new LongRational(3, 7000), true, true));
		as.add(new BasicInterval<LongRational>(new LongRational(2, 5), new LongRational(1, 2), true, true));
		as.add(new BasicInterval<LongRational>(new LongRational(2, 3), new LongRational(3, 3), true, true));
		a.add(as);

		MultiIntervalIntersection<LongRational> result = new MultiIntervalIntersection<LongRational>(a);

		assert result.size() == 3;
		assert result.contains(
				new BasicInterval<LongRational>(new LongRational(2, 7000), new LongRational(3, 7000), true, true));
		assert result.contains(
				new BasicInterval<LongRational>(new LongRational(118, 119), new LongRational(1, 1), true, true));
		assert result
				.contains(new BasicInterval<LongRational>(new LongRational(2, 5), new LongRational(2, 5), true, true));


		assert result.encompassesValue(new LongRational(2, 7000));
		assert result.encompassesValue(new LongRational(3, 7000));
		assert result.encompassesValue(new LongRational(2, 5));
		assert result.encompassesValue(new LongRational(118, 119));
		assert result.encompassesValue(new LongRational(1, 1));
		}

	@Test
	public void multiIntervalOpenIntersectionOfLongRationalsWorks()
		{
		Set<Set<BasicInterval<LongRational>>> a = new HashSet<Set<BasicInterval<LongRational>>>();
		Set<BasicInterval<LongRational>> as = new HashSet<BasicInterval<LongRational>>();
		as.add(new BasicInterval<LongRational>(new LongRational(2, 7000), new LongRational(3, 7000), false, false));
		as.add(new BasicInterval<LongRational>(new LongRational(1, 5), new LongRational(2, 5), false, false));
		as.add(new BasicInterval<LongRational>(new LongRational(5, 6), new LongRational(1, 1), false, false));
		a.add(as);

		as = new HashSet<BasicInterval<LongRational>>();
		as.add(new BasicInterval<LongRational>(new LongRational(0, 5), new LongRational(4, 7000), false, false));
		as.add(new BasicInterval<LongRational>(new LongRational(4, 10), new LongRational(1, 2), false, false));
		as.add(new BasicInterval<LongRational>(new LongRational(118, 119), new LongRational(234, 234), false, false));
		a.add(as);

		as = new HashSet<BasicInterval<LongRational>>();
		as.add(new BasicInterval<LongRational>(new LongRational(0, 5), new LongRational(3, 7000), false, false));
		as.add(new BasicInterval<LongRational>(new LongRational(2, 5), new LongRational(1, 2), false, false));
		as.add(new BasicInterval<LongRational>(new LongRational(2, 3), new LongRational(3, 3), false, false));
		a.add(as);

		MultiIntervalIntersection<LongRational> result = new MultiIntervalIntersection<LongRational>(a);

		assert result.size() == 2;
		assert result.contains(
				new BasicInterval<LongRational>(new LongRational(2, 7000), new LongRational(3, 7000), false, false));
		assert result.contains(
				new BasicInterval<LongRational>(new LongRational(118, 119), new LongRational(1, 1), false, false));
		assert !result.contains(
				new BasicInterval<LongRational>(new LongRational(2, 7000), new LongRational(3, 7000), true, true));
		assert !result.contains(
				new BasicInterval<LongRational>(new LongRational(118, 119), new LongRational(1, 1), true, true));
		assert !result
				.contains(new BasicInterval<LongRational>(new LongRational(2, 5), new LongRational(2, 5), true, true));

		assert !result.encompassesValue(new LongRational(2, 7000));
		assert !result.encompassesValue(new LongRational(3, 7000));
		assert !result.encompassesValue(new LongRational(2, 5));
		assert !result.encompassesValue(new LongRational(118, 119));
		assert !result.encompassesValue(new LongRational(1, 1));
		}

	@Test
	public void multiIntervalMixedOpenClosedIntersectionOfLongRationalsWorks()
		{
		Set<Set<BasicInterval<LongRational>>> a = new HashSet<Set<BasicInterval<LongRational>>>();
		Set<BasicInterval<LongRational>> as = new HashSet<BasicInterval<LongRational>>();
		as.add(new BasicInterval<LongRational>(new LongRational(2, 7000), new LongRational(3, 7000), false, true));
		as.add(new BasicInterval<LongRational>(new LongRational(1, 5), new LongRational(2, 5), false, false));
		as.add(new BasicInterval<LongRational>(new LongRational(5, 6), new LongRational(1, 1), false, false));
		a.add(as);

		as = new HashSet<BasicInterval<LongRational>>();
		as.add(new BasicInterval<LongRational>(new LongRational(0, 5), new LongRational(4, 7000), false, false));
		as.add(new BasicInterval<LongRational>(new LongRational(4, 10), new LongRational(1, 2), true, false));
		as.add(new BasicInterval<LongRational>(new LongRational(118, 119), new LongRational(234, 234), true, false));
		a.add(as);

		as = new HashSet<BasicInterval<LongRational>>();
		as.add(new BasicInterval<LongRational>(new LongRational(0, 5), new LongRational(3, 7000), false, true));
		as.add(new BasicInterval<LongRational>(new LongRational(2, 5), new LongRational(1, 2), true, false));
		as.add(new BasicInterval<LongRational>(new LongRational(2, 3), new LongRational(3, 3), false, false));
		a.add(as);

		MultiIntervalIntersection<LongRational> result = new MultiIntervalIntersection<LongRational>(a);

		assert result.size() == 2;
		assert result.contains(
				new BasicInterval<LongRational>(new LongRational(2, 7000), new LongRational(3, 7000), false, true));
		assert result.contains(
				new BasicInterval<LongRational>(new LongRational(118, 119), new LongRational(1, 1), true, false));
		assert !result.contains(
				new BasicInterval<LongRational>(new LongRational(2, 7000), new LongRational(3, 7000), true, true));
		assert result.contains(
				new BasicInterval<LongRational>(new LongRational(118, 119), new LongRational(1, 1), true, false));
		assert !result
				.contains(new BasicInterval<LongRational>(new LongRational(2, 5), new LongRational(2, 5), true, true));

		assert !result.encompassesValue(new LongRational(2, 7000));
		assert result.encompassesValue(new LongRational(3, 7000));
		assert !result.encompassesValue(new LongRational(2, 5));
		assert result.encompassesValue(new LongRational(118, 119));
		assert !result.encompassesValue(new LongRational(1, 1));
		}
	}
