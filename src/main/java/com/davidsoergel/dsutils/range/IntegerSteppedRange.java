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
