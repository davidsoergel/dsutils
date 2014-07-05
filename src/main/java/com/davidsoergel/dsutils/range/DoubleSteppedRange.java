/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.range;

import com.davidsoergel.dsutils.math.MathUtils;
import org.jetbrains.annotations.NotNull;

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

	@NotNull
	protected DoubleSetRange asSetRange()
		{
		return new DoubleSetRange(getValues());
		}
	// -------------------------- OTHER METHODS --------------------------

	public int compareTo(@NotNull final Object x)
		{
		Interval<Double> o = (Interval<Double>) x;
		return min.compareTo(o.getMin());
		}

	public boolean encompassesValue(Double value)
		{
		assert step != null;
		assert step != 0;
		@NotNull Double multiplier = (value - min) / step;
		return MathUtils.equalWithinFPError(multiplier, Math.floor(multiplier));
		}


	@NotNull
	public SortedSet<Double> getValues()
		{
		assert step != null;
		assert step != 0;
		@NotNull SortedSet<Double> result = new TreeSet<Double>();
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
