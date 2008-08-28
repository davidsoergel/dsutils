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
import org.apache.log4j.Logger;

/**
 * @author lorax
 * @version 1.0
 */
public class BasicLongRationalInterval extends BasicInterval<LongRational>
	{
	// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(BasicLongRationalInterval.class);

	// --------------------------- CONSTRUCTORS ---------------------------

	public BasicLongRationalInterval(LongRational left, LongRational right, boolean closedLeft, boolean closedRight)
		{
		super(left, right, closedLeft, closedRight);
		}

	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface Comparable ---------------------

	public int compareTo(Interval<LongRational> o)
		{
		// assume we're using Comparable Numbers; ClassCastException if not
		int result = left.compareTo(o.getMin());
		if (result == 0)
			{
			if (closedLeft && !o.isClosedLeft())
				{
				result = -1;
				}
			else if (!closedLeft && o.isClosedLeft())
				{
				result = 1;
				}
			}
		return result;
		}

	// --------------------- Interface Interval ---------------------

	// assumes open interval, i.e. includes endpoints

	public boolean encompassesValue(LongRational value)
		{
		int leftCompare = LongRational.overflowSafeCompare(left, value);
		int rightCompare = LongRational.overflowSafeCompare(right, value);

		if (closedLeft && leftCompare == 0)
			{
			return true;
			}
		if (closedRight && rightCompare == 0)
			{
			return true;
			}

		return leftCompare < 0 && rightCompare > 0;
		}
	}
