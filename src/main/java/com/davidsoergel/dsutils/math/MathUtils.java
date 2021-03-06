/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.math;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;


/**
 * @version $Id$
 */
public class MathUtils
	{

	// ------------------------------ FIELDS ------------------------------

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

	private static int logLevels;
	private static double[] logBinIncrement;
	private static double[] logBinLimit;

	private static final Logger logger = Logger.getLogger(MathUtils.class);
	private static final int FACTORIAL_LIMIT = 100;
	@NotNull
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
	static double LOG_TWO_PI = Math.log(2.0 * Math.PI);

	private static double[][] logTable;
	public static final double LOGTWO = Math.log(2);
	public static final double LOGTEN = Math.log(10);


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
		double result = 1;

		// this was supposed to be faster than doing the multiplication explicitly, but it has all kinds of Infinity issues etc.
		//result = factorial(n) / (factorial(m) * factorial(n - m));

		for (int i = n; i > n - m; i--)
			{
			result *= i;
			}
		result /= factorial(m);

		return (long) result;
		}

	public static double factorial(int n) throws ArithmeticException
		{
		if (n > FACTORIAL_LIMIT)
			{
			return stirlingFactorial(n);
			}
		if (factorials[n] == 0)
			{
			factorials[n] = n * factorial(n - 1);
			}
		return factorials[n];
		}

	public static double stirlingFactorial(int n)
		{
		if (n >= 144)
			{
			throw new ArithmeticException("Factorials greater than 144 don't fit in Double.");
			}
		double result = Math.sqrt(2.0 * Math.PI * n) * Math.pow(n, n) * Math.pow(Math.E, -n);
		return result;
		}

	static
		{
		factorials[0] = 1;
		factorials[1] = 1;
		}

	/**
	 * log(n!) =~ n * log(n) - n
	 *
	 * @param d
	 * @return
	 */
	public static double stirlingLogFactorial(double d)
		{
		// double d = n;// just to be sure
		// use the real log here, not the approximate one
		return ((d + 0.5) * Math.log(d)) - d + (0.5 * LOG_TWO_PI);
		}

	public static double approximateLog(double x)
		{


		if (x <= logBinIncrement[0])
			{
			// x is too small

			if (x < 0)
				{
				throw new Error("Can't take log < 0");
				}

			return Math.log(x);
			}

		// REVIEW Is there a faster way to decide which bin to use?  e.g. binary tree instead of linear?
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

	public static double approximateLog2(double d)
		{
		return approximateLog(d) / LOGTWO;
		}

	/**
	 * log(sum(exp(args)))
	 *
	 * @param x
	 * @param y
	 */
	public static double logsum(double x, double y)
		{
		if (x == Double.NEGATIVE_INFINITY)
			{
			return y;
			}
		if (y == Double.NEGATIVE_INFINITY)
			{
			return x;
			}

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
		if (x == Double.NEGATIVE_INFINITY)
			{
			return logsum(y, z);
			}
		if (y == Double.NEGATIVE_INFINITY)
			{
			return logsum(x, z);
			}
		if (z == Double.NEGATIVE_INFINITY)
			{
			return logsum(x, y);
			}
		// scale all the log probabilities up to avoid underflow.

		double B = MAX_EXPONENT - Math.log(3) - Math.max(x, Math.max(y, z));

		double result = Math.log(Math.exp(x + B) + Math.exp(y + B) + Math.exp(z + B)) - B;
		if (Double.isNaN(result))
			{
			result = Double.NEGATIVE_INFINITY;
			//xklogger.info("Log sum produced NaN: " + x + " + " + y + " + " + z + " = " + result + "   (Scale factor: " + B + ")", new Exception());
			//logger.debug("Log sum produced NaN!");
			//logger.error("Error", new Throwable());
			}

		//	logger.debug("Log sum: " + x + " + " + y + " + " + z + " = " + result + "   (Scale factor: " + B + ")");

		//		if (result > 0)
		//			{
		//			throw new Error("Positive log probability not allowed!");
		//			}

		return result;
		}

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

	public static boolean equalWithinFPError(double a, double b)
		{
		if (a == b)
			{
			return true;
			}// covers Infinity cases

		/*		double u = 100.*Math.ulp(a);

				if (b >= a - u && b <= a + u)
					{
					return true;
					}

				return false;*/

		double nearlyZero = a - b;
		// these errors are generally in the vicinity of 1e-15
		// let's be extra permissive, 1e-10 is good enough anyway
		return -1e-10 < nearlyZero && nearlyZero < 1e-10;
		}

	/**
	 * Extended GCD algorithm; solves the linear Diophantine equation ax + by = c. This clever implementation comes from
	 * http://www.cs.utsa.edu/~wagner/laws/fav_alg.html, who in turn adapted it from D. Knuth.
	 *
	 * @param x
	 * @param y
	 * @return an array of long containing {a, b, c}
	 * @throws ArithmeticException if either argument is negative or zero
	 */
	@NotNull
	public static long[] extendedGCD(long x, long y) throws ArithmeticException
		{
		/*
				  if (x <= 0 || y <= 0)
					  {
					  throw new ArithmeticException("Can take GCD only of positive numbers");
					  }
					  */

		@NotNull long[] u = {1, 0, x}, v = {0, 1, y}, t = new long[3];
		while (v[2] != 0)
			{
			long q = u[2] / v[2];

			//	logger.debug("" + x + "(" + u[0] + ") + " + y + "(" + u[1] + ") = " + u[2] + "     [" + q + "]");

			for (int i = 0; i < 3; i++)
				{
				t[i] = u[i] - v[i] * q;
				u[i] = v[i];
				v[i] = t[i];
				}
			}
		//	logger.debug("" + x + "(" + u[0] + ") + " + y + "(" + u[1] + ") = " + u[2] + "     [DONE]");
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
	 * Extended GCD algorithm; solves the linear Diophantine equation ax + by = c with the constraints that a and c must be
	 * positive. To achieve this, we first apply the standard GCD algorithm, and then adjust as needed by replacing a with
	 * (a+ny) and b with (b-nx), since (a+ny)x + (b-nx)y = c
	 *
	 * @param x
	 * @param y
	 * @return an array of long containing {a, b, c}
	 * @throws ArithmeticException if either argument is negative or zero
	 */
	@NotNull
	public static long[] extendedGCDPositive(long x, long y) throws ArithmeticException
		{
		@NotNull long[] u = extendedGCD(x, y);
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

	// running mean is obvious; running stddev from http://en.wikipedia.org/wiki/Standard_deviation

	public static double runningMean(int sampleCount, double priorMean, double value)
		{
		double d = sampleCount;  // cast only once
		return priorMean + (value - priorMean) / d;
		}

	public static double runningStddevQ(int sampleCount, double priorMean, double priorQ, double value)
		{
		double d = value - priorMean;
		return priorQ + ((sampleCount - 1) * d * d / sampleCount);
		}

	@NotNull
	public static double[] runningStddevQtoStddev(@NotNull double[] stddevQ, int sampleCount)
		{
		@NotNull double[] result = new double[stddevQ.length];
		double d = sampleCount;  // cast only once
		for (int i = 0; i < result.length; i++)
			{
			result[i] = Math.sqrt(stddevQ[i] / d);
			}
		return result;
		}
	}
