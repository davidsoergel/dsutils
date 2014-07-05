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

public class LongRationalTest//extends TestCase
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(LongRationalTest.class);


	// -------------------------- OTHER METHODS --------------------------

	@Test
	public void compareToWorksForEasyValues()
		{
		assert new LongRational(3, 4).compareTo(new LongRational(3, 4)) == 0;
		assert new LongRational(3, 4).compareTo(new LongRational(2, 3)) == 1;
		assert new LongRational(2, 3).compareTo(new LongRational(3, 4)) == -1;
		}

	@Test
	public void compareToWorksForHardValues()
		{
		assert new LongRational(Long.MAX_VALUE - 8, Long.MAX_VALUE - 6)
				.compareTo(new LongRational(Long.MAX_VALUE - 8, Long.MAX_VALUE - 7)) == -1;
		assert new LongRational(Long.MAX_VALUE - 8, Long.MAX_VALUE - 6)
				.compareTo(new LongRational(Long.MAX_VALUE - 8, Long.MAX_VALUE - 6)) == 0;
		assert new LongRational(Long.MAX_VALUE - 8, Long.MAX_VALUE - 6)
				.compareTo(new LongRational(Long.MAX_VALUE - 8, Long.MAX_VALUE - 5)) == 1;

		assert new LongRational(3123123358455847349L, 1231234567576457675L)
				.compareTo(new LongRational(3123123358455847359L, 1231234567576457785L)) == 1;
		assert new LongRational(3123123358455847359L, 1231234567576457785L)
				.compareTo(new LongRational(3123123358455847359L, 1231234567576457675L)) == -1;
		}

	@Test
	public void compareToWorksForMediumValues()
		{
		assert new LongRational(Long.MAX_VALUE - 9, Long.MAX_VALUE - 6)
				.compareTo(new LongRational(Long.MAX_VALUE - 8, Long.MAX_VALUE - 6)) == -1;
		assert new LongRational(Long.MAX_VALUE - 8, Long.MAX_VALUE - 6)
				.compareTo(new LongRational(Long.MAX_VALUE - 8, Long.MAX_VALUE - 6)) == 0;
		assert new LongRational(Long.MAX_VALUE - 7, Long.MAX_VALUE - 6)
				.compareTo(new LongRational(Long.MAX_VALUE - 8, Long.MAX_VALUE - 6)) == 1;
		}

	@Test
	public void equalsWorks()
		{
		assert (new LongRational(20000, 80000).equals(new LongRational(20000, 80000)));
		assert !(new LongRational(20000, 80000).equals(new LongRational(2, 5)));
		}

	@Test
	public void mediantWorks()
		{
		logger.debug(LongRational.mediant(new LongRational(345, 678), new LongRational(789, 2345)));
		assert LongRational.mediant(new LongRational(345, 678), new LongRational(789, 2345))
				.equals(new LongRational(115 + 789, 226 + 2345));
		}

	/*	@Test(expectedExceptions = SafeIntegerArithmetic.IllegalArithArgsException.class)
	 public void compareToFailsForHardValues()
		 {
		 assert new LongRational(Long.MAX_VALUE-8,Long.MAX_VALUE-6).compareTo(new LongRational(Long.MAX_VALUE-8,Long.MAX_VALUE-5)) == -1;
		 assert new LongRational(Long.MAX_VALUE-8,Long.MAX_VALUE-6).compareTo(new LongRational(Long.MAX_VALUE-8,Long.MAX_VALUE-6)) == 0;
		 assert new LongRational(Long.MAX_VALUE-8,Long.MAX_VALUE-6).compareTo(new LongRational(Long.MAX_VALUE-8,Long.MAX_VALUE-7)) == 1;
		 }
 */
	@Test
	public void minusWorks()
		{
		assert new LongRational(3, 4).minus(new LongRational(2, 3)).equals(new LongRational(1, 12));
		}

	@Test
	public void reduceWorks()
		{
		assert (new LongRational(20000, 80000).equals(new LongRational(1, 4)));
		}

	@Test
	public void toStringWorks()
		{
		assert new LongRational(345, 678).toString().equals("115/226");
		}
	}
