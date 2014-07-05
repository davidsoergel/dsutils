/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.math;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

/**
 * @version $Id$
 */
public class MathUtilsTest
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(MathUtilsTest.class);


	// -------------------------- OTHER METHODS --------------------------

	@Test
	public void approximateLogErrorIsLessThanOnePercent() throws MathUtilsException
		{
		MathUtils.initApproximateLog(-12, 12, 3, 100000);
		/*for (int power = -1; power > -5; power--)
			{
			double orderOfMagnitude = Math.pow(10, power);
			for (int i = 1; i < 10; i++)
				{
				checkLog(i + orderOfMagnitude);
				}
			}
		*/
		// brute force across the whole range
		for (int orderOfMagnitude = -15; orderOfMagnitude < 15; orderOfMagnitude++)
			{
			double scale = Math.pow(10, orderOfMagnitude);
			for (double x = 1; x < 10; x++)
				{
				// the worst case is near the top of each bin, right?
				// try the bottom and the middle too, just in case
				checkLog((x * scale) + (0.1 * (scale / 10)));
				checkLog((x * scale) + (0.5 * (scale / 10)));
				checkLog((x * scale) + (0.9 * (scale / 10)));
				}
			}
		}

	private void checkLog(double x)//throws MathUtilsException
		{
		double approximate = MathUtils.approximateLog(x);
		double correct = Math.log(x);
		if (Math.abs(approximate) < Math.abs(correct * 0.99))
			{
			logger.warn("log(" + (x) + ") = " + correct + "; approximation = " + approximate + "\n");
			assert false;
			}
		if (Math.abs(approximate) > Math.abs(correct * 1.01))
			{
			logger.warn("log(" + x + ") = " + correct + "; approximation = " + approximate + "\n");
			assert false;
			}
		}

	@Test
	public void gcdWorks()
		{
		assert MathUtils.GCD(2345, 7895) == 5;
		assert MathUtils.GCD(55986 * 2345, 55986 * 7895) == 55986 * 5;
		assert MathUtils.GCD(1, 7895) == 1;
		}

	/*	@Test(expectedExceptions = ArithmeticException.class)
	 public void gcdFailsOnZeroInput()
		 {
		 MathUtils.GCD(0, 7895);
		 }
 */
	/*	@Test(expectedExceptions = ArithmeticException.class)
   public void gcdFailsOnNegativeInput()
	   {
	   MathUtils.GCD(-1, 7895);
	   }*/

	@Test
	public void longDivisionGivesAbsoluteValueFloor()
		{
		long x = 11;
		long y = 2;
		long z = 5;
		assert (x / y == z);
		assert (-x / y == -z);
		assert (x / -y == -z);
		assert (x / y == z);
		assert (-x / -y == z);
		}
	}
