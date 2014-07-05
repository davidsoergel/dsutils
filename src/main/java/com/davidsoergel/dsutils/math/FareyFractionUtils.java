/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.math;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;


/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class FareyFractionUtils
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(FareyFractionUtils.class);


	// -------------------------- STATIC METHODS --------------------------

	/**
	 * Calculate the right bound of a Farey fraction interval given the left bound
	 *
	 * @param lft the left bound of a Farey interval
	 * @return the associated right bound
	 * @throws ArithmeticException if the resolution of the Farey fraction system is exhausted (due to numerical overflow)
	 */
	@NotNull
	public static LongRational rgt(@NotNull LongRational lft) throws ArithmeticException
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
		@NotNull long[] u = MathUtils.extendedGCDPositive(lft.denominator, -lft.numerator);

		long rgtDenominator = u[1];
		//MathUtils.extendedGCD(lft.denominator, lft.numerator)[1];

		// be careful about long multiplication overflow
		try
			{
			long mult = SafeIntegerArithmetic.mul(lft.numerator, rgtDenominator);
			long rgtNumerator = (mult + 1) / lft.denominator;
			return new LongRational(rgtNumerator, rgtDenominator);
			}
		catch (SafeIntegerArithmetic.IllegalArithArgsException e)
			{
			throw new ArithmeticException("Exhausted resolution of Farey fractions looking for rgt(" + lft + ")");
			}
		}
	}
