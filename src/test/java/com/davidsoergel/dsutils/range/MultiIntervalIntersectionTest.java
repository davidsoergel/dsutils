/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.range;

import com.davidsoergel.dsutils.math.LongRational;
import org.jetbrains.annotations.NotNull;
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
		@NotNull Set<Set<BasicInterval<Double>>> a = new HashSet<Set<BasicInterval<Double>>>();
		@NotNull Set<BasicInterval<Double>> as = new HashSet<BasicInterval<Double>>();
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

		@NotNull Set<Interval<Double>> result = new MultiIntervalIntersection<Double>(a);

		assert result.size() == 2;
		assert result.contains(new BasicInterval<Double>(0.123, 0.456, true, true));
		assert result.contains(new BasicInterval<Double>(5.45, 5.5, true, true));
		}

	@Test
	public void multiIntervalIntersectionOfDoublesDealsWithOverlappingInputsCorrectly()
		{
		@NotNull Set<Set<BasicInterval<Double>>> a = new HashSet<Set<BasicInterval<Double>>>();
		@NotNull Set<BasicInterval<Double>> as = new HashSet<BasicInterval<Double>>();
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

		@NotNull Set<Interval<Double>> result = new MultiIntervalIntersection<Double>(a);

		assert result.size() == 3;
		assert result.contains(new BasicInterval<Double>(0.123, 1., true, false));
		assert result.contains(new BasicInterval<Double>(1., 2.3, false, true));
		assert result.contains(new BasicInterval<Double>(5.45, 5.5, true, false));
		}

	@Test
	public void multiIntervalClosedIntersectionOfLongRationalsWorks()
		{
		@NotNull Set<Set<BasicInterval<LongRational>>> a = new HashSet<Set<BasicInterval<LongRational>>>();
		@NotNull Set<BasicInterval<LongRational>> as = new HashSet<BasicInterval<LongRational>>();
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

		@NotNull MultiIntervalIntersection<LongRational> result = new MultiIntervalIntersection<LongRational>(a);

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
		@NotNull Set<Set<BasicInterval<LongRational>>> a = new HashSet<Set<BasicInterval<LongRational>>>();
		@NotNull Set<BasicInterval<LongRational>> as = new HashSet<BasicInterval<LongRational>>();
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

		@NotNull MultiIntervalIntersection<LongRational> result = new MultiIntervalIntersection<LongRational>(a);

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
		@NotNull Set<Set<BasicInterval<LongRational>>> a = new HashSet<Set<BasicInterval<LongRational>>>();
		@NotNull Set<BasicInterval<LongRational>> as = new HashSet<BasicInterval<LongRational>>();
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

		@NotNull MultiIntervalIntersection<LongRational> result = new MultiIntervalIntersection<LongRational>(a);

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
