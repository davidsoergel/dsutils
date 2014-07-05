/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;


/**
 * @version $Id$
 */
public class DSStringUtilsTest
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(DSStringUtilsTest.class);


	// -------------------------- OTHER METHODS --------------------------

	@Test
	public void nullObjectStringConversionIsNotNull()
		{
		//logger.trace("StringUtilsTest.nullObjectStringConversionIsNotNull()");

		assert DSStringUtils.s(null).equals("");
		}

	@Test
	public void joinDoubleArrayToStringWithDelimiterWorks()
		{
		String s = DSStringUtils.join(new double[]{
				1.234,
				6.7346,
				124.124
		}, ", ");
		assert s.equals("1.234, 6.7346, 124.124") : s;
		}
	}
