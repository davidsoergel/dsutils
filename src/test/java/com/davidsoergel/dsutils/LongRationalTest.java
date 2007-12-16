/*
 * Copyright (c) 2001-2007 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
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

/* $Id$ */

package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA. User: soergel Date: Dec 6, 2006 Time: 4:30:53 PM To change this template use File |
 * Settings | File Templates.
 */
public class LongRationalTest//extends TestCase
	{
	// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(LongRationalTest.class);


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
