package com.davidsoergel.dsutils;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * @author lorax
 * @version 1.0
 */
public class ArrayUtilsTest extends TestCase
	{
	private static Logger logger = Logger.getLogger(ArrayUtilsTest.class);


	double[] a;
	double[] b;
	double[] c;

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
	public void doubleArrayCloneIsDeepCopyAndEqualsIsElementwise()
		{
		double[] aclone = a.clone();
		assert aclone != a;
		assert Arrays.equals(a, aclone);
		}

	@Test
	public void doubleArrayIncrementWorks()
		{
		ArrayUtils.incrementBy(a, b);

		assert Arrays.equals(a, c);
		}

	@Test
	public void doubleArrayPlusWorks()
		{
		double[] result = ArrayUtils.plus(a, b);
		assert result != a;
		assert result != b;
		assert result != c;

		assert Arrays.equals(result, c);
		}


	@Test
	public void doubleArrayDecrementWorks()
		{
		ArrayUtils.decrementBy(c, b);

		assert ArrayUtils.equalWithinFPError(a, c);
		}

	@Test
	public void doubleArrayMinusWorks()
		{
		double[] result = ArrayUtils.minus(c, b);
		assert result != a;
		assert result != b;
		assert result != c;

		assert ArrayUtils.equalWithinFPError(result, a);
		}
	}
