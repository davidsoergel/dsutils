package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: soergel
 * Date: Dec 6, 2006
 * Time: 4:58:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class FareyFractionUtils
	{

	private static Logger logger = Logger.getLogger(FareyFractionUtils.class);

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
