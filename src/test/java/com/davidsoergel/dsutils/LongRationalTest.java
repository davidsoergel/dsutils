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
	}
