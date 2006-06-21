/*
 * dsutils - a collection of generally useful utility classes
 *
 * Copyright (c) 2001-2006 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * USA.
 *
 */

package com.davidsoergel.dsutils;

/**
 * @author lorax
 * @version 1.0
 */
public class MathUtils
	{
// ------------------------------ FIELDS ------------------------------

	private static final int FACTORIAL_LIMIT = 100;
	private static double[] factorials = new double[FACTORIAL_LIMIT + 1];
// log(x+y)  =  log(x) + log [1 + exp[log(y) - log(x)]]
// for x >= y

/*	double logsum(double x, double y)
		{
		double largest = Math.max(x, y);
		double smallest = Math.min(x, y);

		return largest + Math.log(1.0 + Math.exp(smallest - largest));
		}
*/

// Still stuck on how to implement this:
// need to know which is bigger, exp(x) or exp(y)+exp(z)

	static double MAX_EXPONENT = Math.log(Double.MAX_VALUE);

// -------------------------- STATIC METHODS --------------------------

	public static double minmax(double min, double b, double max)
		{
		return Math.max(Math.min(b, max), min);
		}

	public static long choose(int n, int m)
		{
		if (m == 0)
			{
			return 1;
			}
		double result;
		result = factorial(n) / (factorial(m) * factorial(n - m));

		return (long) result;
		}

	public static double factorial(int n) throws ArithmeticException
		{
		if (n > FACTORIAL_LIMIT)
			{
			return StirlingFactorial(n);
			}
		if (factorials[n] == 0)
			{
			factorials[n] = n * factorial(n - 1);
			}
		return factorials[n];
		}

	public static double StirlingFactorial(int n)
		{
		double result = Math.sqrt(2.0 * Math.PI * n) * Math.pow(n, n) * Math.pow(Math.E, -n);
		return result;
		}

	static
		{
		factorials[0] = 1;
		factorials[1] = 1;
		}


	/**
	 * log(sum(exp(args)))
	 *
	 * @param x
	 * @param y
	 */
	public static double logsum(double x, double y)
		{
		// scale all the log probabilities up to avoid underflow.

		double B = MAX_EXPONENT - Math.log(3) - Math.max(x, y);


		double result = Math.log(Math.exp(x + B) + Math.exp(y + B)) - B;

		//	logger.debug("Log sum: " + x + " + " + y + " = " + result + "   (Scale factor: " + B + ")");

		return result;
		}

	/**
	 * log(sum(exp(args)))
	 *
	 * @param x
	 * @param y
	 * @param z
	 */
	public static double logsum(double x, double y, double z)
		{
		// scale all the log probabilities up to avoid underflow.

		double B = MAX_EXPONENT - Math.log(3) - Math.max(x, Math.max(y, z));

		double result = Math.log(Math.exp(x + B) + Math.exp(y + B) + Math.exp(z + B)) - B;
		if (Double.isNaN(result))
			{
			result = Double.NEGATIVE_INFINITY;
			//xklogger.info("Log sum produced NaN: " + x + " + " + y + " + " + z + " = " + result + "   (Scale factor: " + B + ")", new Exception());
			//logger.debug("Log sum produced NaN!");
//			try
//				{
//			throw new Exception("bogus");
//				}
//				catch(Exception e) { e.printStackTrace(); }
			}

		//	logger.debug("Log sum: " + x + " + " + y + " + " + z + " = " + result + "   (Scale factor: " + B + ")");

//		if (result > 0)
//			{
//			throw new Error("Positive log probability not allowed!");
//			}

		return result;
		}

	private static double[] logTableBelowOne;
	private static double[] logTableAboveOne;

	public static int logbins;
	public static double logResolution;
	public static double maxLogArg;


	public static void initApproximateLog(int bins, double max)
		{
		maxLogArg = max;
		logbins = bins;
		logResolution = (double)1/logbins;
		logTableBelowOne = new double[logbins];
		for (int i = 0; i < logbins; i++)
			{
			logTableBelowOne[i] = Math.log((double) i / (double) logbins);
			}
		logTableAboveOne = new double[logbins];
		for (int i = 0; i < logbins; i++)
			{
			logTableAboveOne[i] = Math.log((double) (i*maxLogArg) / (double) logbins);
			}
		}

	public static double approximateLog(double x) throws MathUtilsException
		{
		if (!(x > 0) && (x < maxLogArg))
			{
			throw new MathUtilsException("approximateLog accepts only 0 < x < " + maxLogArg +"; maybe init with different max");
			}
		if (x < .00001 || (x > .9999 && x < 1))
			{
			return Math.log(x);
			}
		if(x < 1)
			{
			return logTableBelowOne[(int) Math.floor(x * logbins)];
			}
		return logTableAboveOne[(int) Math.floor((x/maxLogArg) * logbins)];
		}
	}
