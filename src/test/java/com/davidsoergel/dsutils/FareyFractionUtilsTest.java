package com.davidsoergel.dsutils;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: soergel
 * Date: Dec 6, 2006
 * Time: 5:07:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class FareyFractionUtilsTest extends TestCase
	{
	private static Logger logger = Logger.getLogger(FareyFractionUtilsTest.class);

	@Test
	public void rgtWorks()
		{
		//logger.debug(FareyFractionUtils.rgt(new LongRational(5345, 73463)));


		//logger.debug(FareyFractionUtils.rgt(new LongRational(5, 7)));
		// examples from Tropashko 2004
		assert FareyFractionUtils.rgt(new LongRational(1, 2)).equals(new LongRational(1, 1));
		assert FareyFractionUtils.rgt(new LongRational(2, 3)).equals(new LongRational(1, 1));
		assert FareyFractionUtils.rgt(new LongRational(3, 4)).equals(new LongRational(1, 1));
		assert FareyFractionUtils.rgt(new LongRational(4, 5)).equals(new LongRational(1, 1));
		assert FareyFractionUtils.rgt(new LongRational(5, 7)).equals(new LongRational(3, 4));
		assert FareyFractionUtils.rgt(new LongRational(3, 5)).equals(new LongRational(2, 3));
		assert FareyFractionUtils.rgt(new LongRational(4, 7)).equals(new LongRational(3, 5));
		}
	}
