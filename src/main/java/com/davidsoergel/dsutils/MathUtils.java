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

import org.apache.log4j.Logger;

/**
 * @author lorax
 * @version 1.0
 */
public class MathUtils
	{

	private static Logger logger = Logger.getLogger(MathUtils.class);

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

	private static double[][] logTable;
	//	private static double[] logTableBelowOne;
	//	private static double[] logTableAboveOne;

	//	public static int logbins;
	//public static double logResolution;
	//	public static double maxLogArg;

	//public static int logLevels;
	public static int logMinOrderOfMagnitude;
	public static int logMaxOrderOfMagnitude;
	public static int logOrdersOfMagnitudePerLevel;
	public static int logBinsPerLevel;

	public static int logLevels;
	public static double[] logBinIncrement;
	public static double[] logBinLimit;

	public static void initApproximateLog(int minOrderOfMagnitude, int maxOrderOfMagnitude,
	                                      int ordersOfMagnitudePerLevel, int binsPerLevel)
		{
		minOrderOfMagnitude += ordersOfMagnitudePerLevel;// since we use the order to define the top of the bin

		logMinOrderOfMagnitude = minOrderOfMagnitude;
		logMaxOrderOfMagnitude = maxOrderOfMagnitude;
		logOrdersOfMagnitudePerLevel = ordersOfMagnitudePerLevel;
		logBinsPerLevel = binsPerLevel;

		logLevels = ((maxOrderOfMagnitude - minOrderOfMagnitude) / ordersOfMagnitudePerLevel) + 1;

		logTable = new double[logLevels][binsPerLevel];
		logBinIncrement = new double[logLevels];
		logBinLimit = new double[logLevels];

		int level = 0;
		for (int order = minOrderOfMagnitude; order <= maxOrderOfMagnitude; order += ordersOfMagnitudePerLevel)
			{
			//logTable[level] = new double[binsPerLevel];
			logBinLimit[level] = Math.pow(10, order);
			logBinIncrement[level] = logBinLimit[level] / binsPerLevel;
			for (int i = 0; i < binsPerLevel; i++)
				{
				logTable[level][i] = Math.log((double) (i * Math.pow(10, order)) / (double) binsPerLevel);
				}
			level++;
			}
		logLevels = level;
		}

	public static double approximateLog(double x)
		{
		if (x <= logBinIncrement[0])
			{
			// x is too small
			return Math.log(x);
			}
		for (int level = 0; level < logLevels; level++)
			{
			if (x < logBinLimit[level])
				{
				return logTable[level][(int) (x / logBinIncrement[level])];
				}
			}
		// x is too big
		return Math.log(x);
		}

	public static boolean equalWithinFPError(double a, double b)
		{
		double nearlyZero = a - b;
		// these errors are generally in the vicinity of 1e-15
		// let's be extra permissive, 1e-10 is good enough anyway
		return -1e-10 < nearlyZero && nearlyZero < 1e-10;
		}

	/**
	 * Greatest Common Denominator.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static long GCD(long x, long y)
		{
		return extendedGCD(x, y)[2];
		}

	/**
	 * Extended GCD algorithm; solves the linear Diophantine equation ax + by = c.
	 * This clever implementation comes from http://www.cs.utsa.edu/~wagner/laws/fav_alg.html,
	 * who in turn adapted it from D. Knuth.
	 *
	 * @param x
	 * @param y
	 * @return an array of long containing {a, b, c}
	 * @throws ArithmeticException if either argument is negative or zero
	 */
	public static long[] extendedGCD(long x, long y) throws ArithmeticException
		{
		/*
		if (x <= 0 || y <= 0)
			{
			throw new ArithmeticException("Can take GCD only of positive numbers");
			}
			*/

		long[] u = {
				1,
				0,
				x
		}, v = {
				0,
				1,
				y
		}, t = new long[3];
		while (v[2] != 0)
			{
			long q = u[2] / v[2];

			logger.debug("" + x + "(" + u[0] + ") + " + y + "(" + u[1] + ") = " + u[2] + "     [" + q + "]");

			for (int i = 0; i < 3; i++)
				{
				t[i] = u[i] - v[i] * q;
				u[i] = v[i];
				v[i] = t[i];
				}
			}
		logger.debug("" + x + "(" + u[0] + ") + " + y + "(" + u[1] + ") = " + u[2] + "     [DONE]");
		/*
			 * The result is inverted if necessary to guarantee that the GCD (c) is non-negative.
	 *
	 * If one of the arguments is 0, the other argument is returned (0 can't have a common divisor with anything except itself).

		 */
		/*if (u[2] < 0)
			{
			u[0] = -u[0];
			u[1] = -u[1];
			u[2] = -u[2];
			}*/
		return u;
		}

	/**
	 * Extended GCD algorithm; solves the linear Diophantine equation ax + by = c with the constraints that a and c must be positive.
	 * To achieve this, we first apply the standard GCD algorithm, and then adjust as needed by replacing a with (a+ny)
	 * and b with (b-nx), since (a+ny)x + (b-nx)y = c
	 *
	 * @param x
	 * @param y
	 * @return an array of long containing {a, b, c}
	 * @throws ArithmeticException if either argument is negative or zero
	 */
	public static long[] extendedGCDPositive(long x, long y) throws ArithmeticException
		{
		long[] u = extendedGCD(x, y);
		if (u[2] < 0)
			{
			u[0] *= -1;
			u[1] *= -1;
			u[2] *= -1;
			}
		if (u[0] > 0)
			{
			return u;
			}

		long n = -u[0] / y;

		// long division gives floor for positive, ceil for negative
		// but we want the reverse
		// so if y was positive, we got the floor, so we need to increment it, and vice versa
		if (y > 0)
			{
			n++;
			}
		else
			{
			n--;
			}


		u[0] += n * y;
		u[1] -= n * x;

		// brute force variant
		/*while(u[0] < 0)
			{
			u[0] += y;
			u[1] -= x;
			}
		*/
		return u;
		}
	}
