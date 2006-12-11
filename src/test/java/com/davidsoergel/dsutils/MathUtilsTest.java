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
import org.testng.annotations.Test;

/**
 * @author lorax
 * @version 1.0
 */
public class MathUtilsTest
	{
	private static Logger logger = Logger.getLogger(MathUtilsTest.class);

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
