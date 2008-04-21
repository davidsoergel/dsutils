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

/* $Id$ */

package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

/**
 * @author lorax
 * @version 1.0
 */
public class MathUtilsTest
	{
	// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(MathUtilsTest.class);


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
			System.err.println("log(" + (x) + ") = " + correct + "; approximation = " + approximate + "\n");
			assert false;
			}
		if (Math.abs(approximate) > Math.abs(correct * 1.01))
			{
			System.err.println("log(" + x + ") = " + correct + "; approximation = " + approximate + "\n");
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
