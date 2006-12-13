package com.davidsoergel.dsutils;

import junit.framework.TestCase;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: lorax
 * Date: Dec 12, 2006
 * Time: 4:29:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class MultiIntervalIntersectionTest extends TestCase
	{
	@Test
	public void multiIntervalIntersectionOfDoublesWorks()
		{
		Set<Set<BasicInterval>> a = new HashSet<Set<BasicInterval>>();
		Set<BasicInterval> as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(0.123, 0.456));
		as.add(new BasicInterval(2.2, 3.3));
		as.add(new BasicInterval(4.4, 5.5));
		a.add(as);

		as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(0.1, 0.5));
		as.add(new BasicInterval(2.4, 3.1));
		as.add(new BasicInterval(4.4, 5.5));
		a.add(as);

		as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(0., 1.));
		as.add(new BasicInterval(2.2, 2.3));
		as.add(new BasicInterval(5.45, 5.5));
		a.add(as);

		Set<Interval> result = new MultiIntervalIntersection(a);

		assert result.size() == 2;
		assert result.contains(new BasicInterval(0.123, 0.456));
		assert result.contains(new BasicInterval(5.45, 5.5));
		}


	@Test
	public void multiIntervalIntersectionOfLongRationalsWorks()
		{
		Set<Set<BasicInterval>> a = new HashSet<Set<BasicInterval>>();
		Set<BasicInterval> as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(new LongRational(2, 7000), new LongRational(3, 7000)));
		as.add(new BasicInterval(new LongRational(1, 5), new LongRational(2, 5)));
		as.add(new BasicInterval(new LongRational(5, 6), new LongRational(1, 1)));
		a.add(as);

		as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(new LongRational(0, 5), new LongRational(4, 7000)));
		as.add(new BasicInterval(new LongRational(4, 10), new LongRational(1, 2)));
		as.add(new BasicInterval(new LongRational(118, 119), new LongRational(234, 234)));
		a.add(as);

		as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(new LongRational(0, 5), new LongRational(3, 7000)));
		as.add(new BasicInterval(new LongRational(2, 5), new LongRational(1, 2)));
		as.add(new BasicInterval(new LongRational(2, 3), new LongRational(3, 3)));
		a.add(as);

		Set<Interval> result = new MultiIntervalIntersection(a);

		assert result.size() == 2;
		assert result.contains(new BasicInterval(new LongRational(2, 7000), new LongRational(3, 7000)));
		assert result.contains(new BasicInterval(new LongRational(118, 119), new LongRational(1, 1)));
		}
	}
