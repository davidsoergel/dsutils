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
		logger.debug(FareyFractionUtils.rgt(new LongRational(5, 7)));
		assert FareyFractionUtils.rgt(new LongRational(5, 7)).equals(new LongRational(3, 4));
		}
	}
