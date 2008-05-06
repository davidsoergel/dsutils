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



package com.davidsoergel.dsutils;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA. User: lorax Date: Dec 12, 2006 Time: 4:29:46 PM To change this template use File | Settings
 * | File Templates.
 */
public class MultiIntervalIntersectionTest//extends TestCase
	{
	// -------------------------- OTHER METHODS --------------------------

	@Test
	public void multiIntervalIntersectionOfDoublesWorks()
		{
		Set<Set<BasicInterval>> a = new HashSet<Set<BasicInterval>>();
		Set<BasicInterval> as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(0.123, 0.456, true, true));
		as.add(new BasicInterval(2.2, 3.3, true, true));
		as.add(new BasicInterval(4.4, 5.5, true, true));
		a.add(as);

		as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(0.1, 0.5, true, true));
		as.add(new BasicInterval(2.4, 3.1, true, true));
		as.add(new BasicInterval(4.4, 5.5, true, true));
		a.add(as);

		as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(0., 1., true, true));
		as.add(new BasicInterval(2.2, 2.3, true, true));
		as.add(new BasicInterval(5.45, 5.5, true, true));
		a.add(as);

		Set<Interval> result = new MultiIntervalIntersection(a);

		assert result.size() == 2;
		assert result.contains(new BasicInterval(0.123, 0.456, true, true));
		assert result.contains(new BasicInterval(5.45, 5.5, true, true));
		}

	@Test
	public void multiIntervalClosedIntersectionOfLongRationalsWorks()
		{
		Set<Set<BasicInterval>> a = new HashSet<Set<BasicInterval>>();
		Set<BasicInterval> as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(new LongRational(2, 7000), new LongRational(3, 7000), true, true));
		as.add(new BasicInterval(new LongRational(1, 5), new LongRational(2, 5), true, true));
		as.add(new BasicInterval(new LongRational(5, 6), new LongRational(1, 1), true, true));
		a.add(as);

		as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(new LongRational(0, 5), new LongRational(4, 7000), true, true));
		as.add(new BasicInterval(new LongRational(4, 10), new LongRational(1, 2), true, true));
		as.add(new BasicInterval(new LongRational(118, 119), new LongRational(234, 234), true, true));
		a.add(as);

		as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(new LongRational(0, 5), new LongRational(3, 7000), true, true));
		as.add(new BasicInterval(new LongRational(2, 5), new LongRational(1, 2), true, true));
		as.add(new BasicInterval(new LongRational(2, 3), new LongRational(3, 3), true, true));
		a.add(as);

		MultiIntervalIntersection<LongRational> result = new MultiIntervalIntersection<LongRational>(a);

		assert result.size() == 2;
		assert result.contains(new BasicInterval(new LongRational(2, 7000), new LongRational(3, 7000), true, true));
		assert result.contains(new BasicInterval(new LongRational(118, 119), new LongRational(1, 1), true, true));

		assert result.encompassesValue(new LongRational(2, 7000));
		}

	@Test
	public void multiIntervalOpenIntersectionOfLongRationalsWorks()
		{
		Set<Set<BasicInterval>> a = new HashSet<Set<BasicInterval>>();
		Set<BasicInterval> as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(new LongRational(2, 7000), new LongRational(3, 7000), false, false));
		as.add(new BasicInterval(new LongRational(1, 5), new LongRational(2, 5), false, false));
		as.add(new BasicInterval(new LongRational(5, 6), new LongRational(1, 1), false, false));
		a.add(as);

		as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(new LongRational(0, 5), new LongRational(4, 7000), false, false));
		as.add(new BasicInterval(new LongRational(4, 10), new LongRational(1, 2), false, false));
		as.add(new BasicInterval(new LongRational(118, 119), new LongRational(234, 234), false, false));
		a.add(as);

		as = new HashSet<BasicInterval>();
		as.add(new BasicInterval(new LongRational(0, 5), new LongRational(3, 7000), false, false));
		as.add(new BasicInterval(new LongRational(2, 5), new LongRational(1, 2), false, false));
		as.add(new BasicInterval(new LongRational(2, 3), new LongRational(3, 3), false, false));
		a.add(as);

		MultiIntervalIntersection<LongRational> result = new MultiIntervalIntersection<LongRational>(a);

		assert result.size() == 2;
		assert result.contains(new BasicInterval(new LongRational(2, 7000), new LongRational(3, 7000), false, false));
		assert result.contains(new BasicInterval(new LongRational(118, 119), new LongRational(1, 1), false, false));
		assert !result.contains(new BasicInterval(new LongRational(2, 7000), new LongRational(3, 7000), true, true));
		assert !result.contains(new BasicInterval(new LongRational(118, 119), new LongRational(1, 1), true, true));

		assert !result.encompassesValue(new LongRational(2, 7000));
		}
	}
