package com.davidsoergel.dsutils;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: soergel
 * Date: Dec 6, 2006
 * Time: 4:30:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class LongRationalTest extends TestCase
	{
	private static Logger logger = Logger.getLogger(LongRationalTest.class);


	@Test
	public void equalsWorks()
		{
		assert (new LongRational(20000, 80000).equals(new LongRational(20000, 80000)));
		assert !(new LongRational(20000, 80000).equals(new LongRational(2, 5)));
		}

	@Test
	public void reduceWorks()
		{
		assert (new LongRational(20000, 80000).equals(new LongRational(1, 4)));
		}

	@Test
	public void mediantWorks()
		{
		logger.debug(LongRational.mediant(new LongRational(345, 678), new LongRational(789, 2345)));
		assert LongRational.mediant(new LongRational(345, 678), new LongRational(789, 2345))
				.equals(new LongRational(115 + 789, 226 + 2345));
		}

	@Test
	public void toStringWorks()
		{
		assert new LongRational(345, 678).toString().equals("115/226");
		}

	@Test
	public void compareToWorksForEasyValues()
		{
		assert new LongRational(3, 4).compareTo(new LongRational(3, 4)) == 0;
		assert new LongRational(3, 4).compareTo(new LongRational(2, 3)) == 1;
		assert new LongRational(2, 3).compareTo(new LongRational(3, 4)) == -1;
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
	}
