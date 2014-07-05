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
 * @version $Id: IntegerSteppedRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class IntegerSteppedRange extends AbstractSteppedRange<Integer>
	{
	public IntegerSteppedRange(final Integer min, final Integer max, final Integer step)
		{
		super(min, max, step);
		}

	@NotNull
	protected IntegerSetRange asSetRange()
		{
		return new IntegerSetRange(getValues());
		}

	public boolean encompassesValue(Integer value)
		{
		assert step != null;
		assert step != 0;
		@NotNull Integer multiplier = (value - min) / step;
		return MathUtils.equalWithinFPError(multiplier, Math.floor(multiplier));
		}


	@NotNull
	public SortedSet<Integer> getValues()
		{
		assert step != null;
		assert step != 0;
		@NotNull SortedSet<Integer> result = new TreeSet<Integer>();
		for (int d = min; d <= max; d += step)
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
