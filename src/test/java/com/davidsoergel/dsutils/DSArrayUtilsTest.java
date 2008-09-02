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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * @author lorax
 * @version 1.0
 */
public class DSArrayUtilsTest//extends TestCase
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(DSArrayUtilsTest.class);


	double[] a;
	double[] b;
	double[] c;


	// -------------------------- OTHER METHODS --------------------------

	@Test
	public void doubleArrayCloneIsDeepCopyAndEqualsIsElementwise()
		{
		double[] aclone = a.clone();
		assert aclone != a;
		assert Arrays.equals(a, aclone);
		}

	@Test
	public void doubleArrayDecrementWorks()
		{
		DSArrayUtils.decrementBy(c, b);

		assert DSArrayUtils.equalWithinFPError(a, c);
		}

	@Test
	public void doubleArrayIncrementWorks()
		{
		DSArrayUtils.incrementBy(a, b);

		assert Arrays.equals(a, c);
		}

	@Test
	public void doubleArrayMinusWorks()
		{
		double[] result = DSArrayUtils.minus(c, b);
		assert result != a;
		assert result != b;
		assert result != c;

		assert DSArrayUtils.equalWithinFPError(result, a);
		}

	@Test
	public void doubleArrayPlusWorks()
		{
		double[] result = DSArrayUtils.plus(a, b);
		assert result != a;
		assert result != b;
		assert result != c;

		assert Arrays.equals(result, c);
		}

	@BeforeMethod
	public void setUp()
		{
		a = new double[]{
				1.1,
				2.4,
				5,
				3
		};
		b = new double[]{
				4,
				7,
				6.2,
				1.3
		};
		c = new double[]{
				5.1,
				9.4,
				11.2,
				4.3
		};
		}
	}