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
 * @version $Id$
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
	public void doubleArrayDeepEqualsWorks()
		{
		double[] a1 = {
				0.1,
				0.2,
				0.3,
				0.4
		};
		double[] a2 = {
				0.1,
				0.2,
				0.3,
				0.4
		};
		double[] a3 = {
				0.1,
				0.2,
				0.3,
				0.4004
		};
		assert DSArrayUtils.equalWithinFPError(a1, a2);
		assert !DSArrayUtils.equalWithinFPError(a1, a3);
		}

	@Test
	public void doubleObjectArrayDeepEqualsWorks()
		{
		Double[] a1 = {
				0.1,
				0.2,
				0.3,
				0.4
		};
		Double[] a2 = {
				0.1,
				0.2,
				0.3,
				0.4
		};
		Double[] a3 = {
				0.1,
				0.2,
				0.3,
				0.4004
		};
		assert DSArrayUtils.equalWithinFPError(a1, a2);
		assert !DSArrayUtils.equalWithinFPError(a1, a3);
		}

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


	@Test
	public void twoDimensionalDoubleArrayPlusWorks()
		{
		assert false;
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


	@Test
	public void byteArrayPrefixReturnsFirstNBytes()
		{
		byte[] fourBytes = new byte[]{
				34,
				65,
				123,
				75
		};
		byte[] threeBytes = new byte[]{
				34,
				65,
				123
		};
		byte[] twoBytes = new byte[]{
				34,
				65
		};
		assert Arrays.equals(DSArrayUtils.prefix(fourBytes, 3), threeBytes);

		assert Arrays.equals(DSArrayUtils.prefix(fourBytes, 2), twoBytes);
		}


	@Test
	public void byteArraySuffixOfLengthReturnsLastNBytes()
		{
		byte[] fourBytes = new byte[]{
				34,
				65,
				123,
				75
		};
		byte[] threeBytes = new byte[]{

				65,
				123,
				75
		};
		byte[] twoBytes = new byte[]{
				123,
				75
		};
		assert Arrays.equals(DSArrayUtils.suffixOfLength(fourBytes, 3), threeBytes);

		assert Arrays.equals(DSArrayUtils.suffixOfLength(fourBytes, 2), twoBytes);
		}

	@Test
	public void byteArraySuffixReturnsLastBytesFromPos()
		{
		byte[] fourBytes = new byte[]{
				34,
				65,
				123,
				75
		};
		byte[] threeBytes = new byte[]{

				65,
				123,
				75
		};
		byte[] twoBytes = new byte[]{
				123,
				75
		};
		assert Arrays.equals(DSArrayUtils.suffix(fourBytes, 1), threeBytes);

		assert Arrays.equals(DSArrayUtils.suffix(fourBytes, 2), twoBytes);
		}


	@Test(expectedExceptions = IndexOutOfBoundsException.class)
	public void byteArrayPrefixThrowsExceptionForTooLongRequest()
		{
		DSArrayUtils.prefix(new byte[]{
				34,
				65,
				123,
				75
		}, 5);
		}

	@Test
	public void bytePrependWorks()
		{
		byte[] fourBytes = new byte[]{
				34,
				65,
				123,
				75
		};
		byte[] threeBytes = new byte[]{

				65,
				123,
				75
		};
		assert Arrays.equals(DSArrayUtils.prepend((byte) 34, threeBytes), fourBytes);
		}

	@Test
	public void byteAppendWorks()
		{
		byte[] fourBytes = new byte[]{
				34,
				65,
				123,
				75
		};
		byte[] threeBytes = new byte[]{
				34,
				65,
				123
		};
		assert Arrays.equals(DSArrayUtils.append(threeBytes, (byte) 75), fourBytes);
		}

	@Test
	public void positionsWorks()
		{
		int[] aa = new int[]{
				22,
				45,
				7,
				5,
				45,
				7,
				345,
				66,
				8,
				8,
				345,
				7,
				45,
				7
		};
		int[] result = new int[]{
				2,
				5,
				11,
				13
		};
		assert Arrays.equals(DSArrayUtils.positions(aa, 7), result);
		}

	@Test
	public void argMinReturnsFirstPositionOfMinimumValue()
		{
		double[] aa = new double[]{
				22,
				45,
				7,
				5,
				45,
				7,
				345,
				66,
				8,
				5,
				345,
				7,
				5,
				7
		};

		assert DSArrayUtils.argmin(aa) == 3;
		}

	@Test
	public void argMaxReturnsFirstPositionOfMaximumValue()
		{
		double[] aa = new double[]{
				22,
				45,
				7,
				5,
				45,
				7,
				345,
				66,
				8,
				5,
				345,
				7,
				5,
				7
		};

		assert DSArrayUtils.argmax(aa) == 6;
		}

	@Test
	public void weightedDecrementWorks()
		{
		assert false;
		}

	@Test
	public void weightedIncrementWorks()
		{
		assert false;
		}

	@Test
	public void stubTest()
		{
		assert false;
		}
	}