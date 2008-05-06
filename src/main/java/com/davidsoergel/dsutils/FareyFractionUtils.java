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

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA. User: soergel Date: Dec 6, 2006 Time: 4:58:36 PM To change this template use File |
 * Settings | File Templates.
 */
public class FareyFractionUtils
	{
	// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(FareyFractionUtils.class);


	// -------------------------- STATIC METHODS --------------------------

	/**
	 * Calculate the right bound of a Farey fraction interval given the left bound
	 *
	 * @param lft the left bound of a Farey interval
	 * @return the associated right bound
	 * @throws ArithmeticException if the resolution of the Farey fraction system is exhausted (due to numerical overflow)
	 */
	public static LongRational rgt(LongRational lft) throws ArithmeticException
		{
		// Tropashko suggests a brute-force search
		/* for (long d = 1; d < lft.denominator; d++)
			{
			if ((lft.numerator * d + 1) % lft.denominator == 0)
				{
				return new LongRational((lft.numerator * d + 1) / lft.denominator, d);
				}
			}
			*/

		// but using the extended Euclid algorithm should be much faster.
		// We want the smallest positive integers d,x that satisfy (num*d + 1)/den = x
		// rearranging, den*x + (-num)*d = 1

		// be very careful about signs here

		/*logger.debug("MathUtils.extendedGCDPositive(" + lft.denominator + ", -" + lft.numerator + ") =" + Arrays
				.toString(MathUtils.extendedGCDPositive(lft.denominator, -lft.numerator)));
*/

		// the normal GCD could make x and d negative, so we use the guaranteed-positive version
		long[] u = MathUtils.extendedGCDPositive(lft.denominator, -lft.numerator);

		long rgtDenominator = u[1];
		//MathUtils.extendedGCD(lft.denominator, lft.numerator)[1];

		// be careful about long multiplication overflow
		try
			{
			long mult = SafeIntegerArithmetic.Mul(lft.numerator, rgtDenominator);
			long rgtNumerator = (mult + 1) / lft.denominator;
			return new LongRational(rgtNumerator, rgtDenominator);
			}
		catch (SafeIntegerArithmetic.IllegalArithArgsException e)
			{
			throw new ArithmeticException("Exhausted resolution of Farey fractions looking for rgt(" + lft + ")");
			}
		}
	}
