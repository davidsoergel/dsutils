package com.davidsoergel.dsutils.range;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class MultiIntervalUnionTest
	{
	@Test
	public void multiIntervalUnionOfDoublesDealsWithOverlappingInputsCorrectly()
		{
		Set<BasicInterval<Double>> as = new HashSet<BasicInterval<Double>>();
		as.add(new BasicInterval<Double>(0.123, 0.456, true, true));
		as.add(new BasicInterval<Double>(.4, 5., true, true));
		as.add(new BasicInterval<Double>(4.4, 5.5, true, true));

		MultiIntervalUnion<Double> result = new MultiIntervalUnion<Double>(as);
		assert result.size() == 1;
		assert result.contains(new BasicInterval<Double>(0.123, 5.5, true, true));

		as = new HashSet<BasicInterval<Double>>();
		as.add(new BasicInterval<Double>(0.1, 0.5, true, true));
		as.add(new BasicInterval<Double>(0.5, 3.1, false, true));
		as.add(new BasicInterval<Double>(4.4, 5.5, true, false));
		result = new MultiIntervalUnion<Double>(as);

		assert result.size() == 2;
		assert result.contains(new BasicInterval<Double>(0.1, 3.1, true, true));
		assert result.contains(new BasicInterval<Double>(4.4, 5.5, true, false));

		as = new HashSet<BasicInterval<Double>>();
		as.add(new BasicInterval<Double>(0., 1., false, false));
		as.add(new BasicInterval<Double>(1., 2.3, false, true));
		as.add(new BasicInterval<Double>(5.45, 5.5, true, true));
		result = new MultiIntervalUnion<Double>(as);

		assert result.size() == 3;
		assert result.contains(new BasicInterval<Double>(0., 1., false, false));
		assert result.contains(new BasicInterval<Double>(1., 2.3, false, true));
		assert result.contains(new BasicInterval<Double>(5.45, 5.5, true, true));
		}
	}
