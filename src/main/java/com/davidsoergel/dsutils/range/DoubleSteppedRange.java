/*
 * Copyright (c) 2007 Regents of the University of California
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
 *     * Neither the name of the University of California, Berkeley nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
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

import com.davidsoergel.dsutils.math.MathUtils;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @version $Id: DoubleSteppedRange.java 601 2009-04-24 01:45:53Z soergel $
 */
public class DoubleSteppedRange extends AbstractSteppedRange<Double>
	{
	public DoubleSteppedRange(final Double min, final Double max, final Double step)
		{
		super(min, max, step);
		}

	protected DoubleSetRange asSetRange()
		{
		return new DoubleSetRange(getValues());
		}
	// -------------------------- OTHER METHODS --------------------------

	public int compareTo(Interval<Double> o)
		{
		return min.compareTo(o.getMin());
		}

	public boolean encompassesValue(Double value)
		{
		assert step != null;
		assert step != 0;
		Double multiplier = (value - min) / step;
		return MathUtils.equalWithinFPError(multiplier, Math.floor(multiplier));
		}


	public SortedSet<Double> getValues()
		{
		assert step != null;
		assert step != 0;
		SortedSet<Double> result = new TreeSet<Double>();
		for (double d = min; d <= max; d += step)
			{
			result.add(d);
			}

		return result;
		}

	public int size()
		{
		assert step != null;
		assert step != 0;
		return (int) ((max - min) / step);
		}
	}
