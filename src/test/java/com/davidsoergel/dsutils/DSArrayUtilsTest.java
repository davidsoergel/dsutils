/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import com.davidsoergel.dsutils.math.MathUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

	double[] aPlusQuarterB;

	// -------------------------- OTHER METHODS --------------------------

	@Test
	public void doubleArrayDeepEqualsWorks()
		{
		@NotNull double[] a1 = {0.1, 0.2, 0.3, 0.4};
		@NotNull double[] a2 = {0.1, 0.2, 0.3, 0.4};
		@NotNull double[] a3 = {0.1, 0.2, 0.3, 0.4004};
		assert DSArrayUtils.equalWithinFPError(a1, a2);
		assert !DSArrayUtils.equalWithinFPError(a1, a3);
		}

	@Test
	public void doubleObjectArrayDeepEqualsWorks()
		{
		@NotNull Double[] a1 = {0.1, 0.2, 0.3, 0.4};
		@NotNull Double[] a2 = {0.1, 0.2, 0.3, 0.4};
		@NotNull Double[] a3 = {0.1, 0.2, 0.3, 0.4004};
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
	public void doubleArrayWeightedDecrementWorks()
		{
		DSArrayUtils.decrementByWeighted(aPlusQuarterB, b, .25);

		assert DSArrayUtils.equalWithinFPError(aPlusQuarterB, a);
		}

	@Test
	public void doubleArrayWeightedIncrementWorks()
		{
		DSArrayUtils.incrementByWeighted(a, b, .25);

		assert Arrays.equals(a, aPlusQuarterB);
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
		@NotNull double[][] aa = new double[3][];
		aa[0] = a;
		aa[1] = b;
		aa[2] = c;

		@NotNull double[][] bb = new double[3][];
		bb[0] = DSArrayUtils.times(a, 2.5);
		bb[1] = DSArrayUtils.times(b, 2.5);
		bb[2] = DSArrayUtils.times(c, 2.5);

		@NotNull double[][] aabb = DSArrayUtils.plus(aa, bb);

		assert DSArrayUtils.equalWithinFPError(aabb[0], DSArrayUtils.times(a, 3.5));
		assert DSArrayUtils.equalWithinFPError(aabb[1], DSArrayUtils.times(b, 3.5));
		assert DSArrayUtils.equalWithinFPError(aabb[2], DSArrayUtils.times(c, 3.5));
		}

	@Test
	public void twoDimensionalDoubleArraySumNColumnsWorks()
		{
		@NotNull double[][] aa = new double[3][];
		aa[0] = a;
		aa[1] = b;
		aa[2] = c;

		assert MathUtils.equalWithinFPError(DSArrayUtils.sumFirstNColumns(aa, 2), 29.);
		}


	@Test
	public void twoDimensionalDoubleArrayCopyWithMoreColumnsWorks()
		{
		@NotNull double[][] aa = new double[3][];
		aa[0] = a;
		aa[1] = b;
		aa[2] = c;

		@NotNull double[][] aax = DSArrayUtils.deepcopy(aa, 2, 5);

		assert aax[0].length == 6;

		assert MathUtils.equalWithinFPError(DSArrayUtils.sum(aa), 60.);
		assert MathUtils.equalWithinFPError(DSArrayUtils.sumFirstNColumns(aax, 4), 60.);
		assert MathUtils.equalWithinFPError(DSArrayUtils.sumFirstNColumns(aa, 2), 29.);
		assert MathUtils.equalWithinFPError(DSArrayUtils.sumFirstNColumns(aax, 2), 29.);
		assert MathUtils.equalWithinFPError(DSArrayUtils.sum(aax), 90.);
		}

	@Test
	public void twoDimensionalDoubleArrayCopyWithFewerColumnsWorks()
		{
		@NotNull double[][] aa = new double[3][];
		aa[0] = a;
		aa[1] = b;
		aa[2] = c;

		@Nullable double[][] aax = DSArrayUtils.deepcopy(aa, -2, 5);

		assert aax[0].length == 2;

		assert MathUtils.equalWithinFPError(DSArrayUtils.sum(aa), 60.);
		assert MathUtils.equalWithinFPError(DSArrayUtils.sumFirstNColumns(aa, 2), 29.);
		assert MathUtils.equalWithinFPError(DSArrayUtils.sumFirstNColumns(aax, 2), 29.);
		assert MathUtils.equalWithinFPError(DSArrayUtils.sum(aax), 29.);
		}

	@BeforeMethod
	public void setUp()
		{
		a = new double[]{1.1, 2.4, 5, 3};
		b = new double[]{4, 7, 6.2, 1.3};
		c = new double[]{5.1, 9.4, 11.2, 4.3};
		aPlusQuarterB = new double[]{2.1, 4.15, 6.55, 3.325};
		}


	@Test
	public void byteArrayPrefixReturnsFirstNBytes()
		{
		@NotNull byte[] fourBytes = new byte[]{34, 65, 123, 75};
		@NotNull byte[] threeBytes = new byte[]{34, 65, 123};
		@NotNull byte[] twoBytes = new byte[]{34, 65};
		assert Arrays.equals(DSArrayUtils.prefix(fourBytes, 3), threeBytes);

		assert Arrays.equals(DSArrayUtils.prefix(fourBytes, 2), twoBytes);
		}


	@Test
	public void byteArraySuffixOfLengthReturnsLastNBytes()
		{
		@NotNull byte[] fourBytes = new byte[]{34, 65, 123, 75};
		@NotNull byte[] threeBytes = new byte[]{

				65, 123, 75};
		@NotNull byte[] twoBytes = new byte[]{123, 75};
		assert Arrays.equals(DSArrayUtils.suffixOfLength(fourBytes, 3), threeBytes);

		assert Arrays.equals(DSArrayUtils.suffixOfLength(fourBytes, 2), twoBytes);
		}

	@Test
	public void byteArraySuffixReturnsLastBytesFromPos()
		{
		@NotNull byte[] fourBytes = new byte[]{34, 65, 123, 75};
		@NotNull byte[] threeBytes = new byte[]{

				65, 123, 75};
		@NotNull byte[] twoBytes = new byte[]{123, 75};
		assert Arrays.equals(DSArrayUtils.suffix(fourBytes, 1), threeBytes);

		assert Arrays.equals(DSArrayUtils.suffix(fourBytes, 2), twoBytes);
		}


	@Test(expectedExceptions = IndexOutOfBoundsException.class)
	public void byteArrayPrefixThrowsExceptionForTooLongRequest()
		{
		DSArrayUtils.prefix(new byte[]{34, 65, 123, 75}, 5);
		}

	@Test
	public void bytePrependWorks()
		{
		@NotNull byte[] fourBytes = new byte[]{34, 65, 123, 75};
		@NotNull byte[] threeBytes = new byte[]{

				65, 123, 75};
		assert Arrays.equals(DSArrayUtils.prepend((byte) 34, threeBytes), fourBytes);
		}

	@Test
	public void byteAppendWorks()
		{
		@NotNull byte[] fourBytes = new byte[]{34, 65, 123, 75};
		@NotNull byte[] threeBytes = new byte[]{34, 65, 123};
		assert Arrays.equals(DSArrayUtils.append(threeBytes, (byte) 75), fourBytes);
		}

	@Test
	public void positionsWorks()
		{
		@NotNull int[] aa = new int[]{22, 45, 7, 5, 45, 7, 345, 66, 8, 8, 345, 7, 45, 7};
		@NotNull int[] result = new int[]{2, 5, 11, 13};
		assert Arrays.equals(DSArrayUtils.positions(aa, 7), result);
		}

	@Test
	public void argMinReturnsFirstPositionOfMinimumValue()
		{
		@NotNull double[] aa = new double[]{22, 45, 7, 5, 45, 7, 345, 66, 8, 5, 345, 7, 5, 7};

		assert DSArrayUtils.argmin(aa) == 3;
		}

	@Test
	public void argMaxReturnsFirstPositionOfMaximumValue()
		{
		@NotNull double[] aa = new double[]{22, 45, 7, 5, 45, 7, 345, 66, 8, 5, 345, 7, 5, 7};

		assert DSArrayUtils.argmax(aa) == 6;
		}

	@Test
	public void rescaleInterpolates()
		{

		}

	@Test
	public void intArrayCompareConsidersColumnsInOrder()
		{
		@NotNull int[] ai = new int[]{2, 3, 7, 6, 8};
		@NotNull int[] bi = new int[]{2, 3, 8, 6, 8};
		assert DSArrayUtils.compare(ai, ai) == 0;
		assert DSArrayUtils.compare(bi, bi) == 0;
		assert DSArrayUtils.compare(ai, bi) == -1;
		assert DSArrayUtils.compare(bi, ai) == 1;
		}

	@Test
	public void intArrayCompareConsidersArrayLength()
		{
		@NotNull int[] ai = new int[]{2, 3, 7, 6, 8};
		@NotNull int[] bi = new int[]{2, 3, 7, 6, 8, 2};
		assert DSArrayUtils.compare(ai, ai) == 0;
		assert DSArrayUtils.compare(bi, bi) == 0;
		assert DSArrayUtils.compare(ai, bi) == -1;
		assert DSArrayUtils.compare(bi, ai) == 1;
		}

	@Test
	public void selectWorks()
		{
		@NotNull double[] a1 = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7};

		@NotNull boolean[] mask = {false, true, true, false, false, true, true};

		assert Arrays.equals(DSArrayUtils.select(a1, mask), new double[]{0.2, 0.3, 0.6, 0.7});
		}

	// TODO add more tests of DSArrayUtils
	}
