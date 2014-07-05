/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.math;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;


/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class FareyFractionUtilsTest//extends TestCase
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(FareyFractionUtilsTest.class);


	// -------------------------- OTHER METHODS --------------------------

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
