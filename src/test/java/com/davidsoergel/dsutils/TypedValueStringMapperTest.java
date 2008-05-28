package com.davidsoergel.dsutils;

import junit.framework.TestCase;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
 */
public class TypedValueStringMapperTest extends TestCase
	{
	@Test
	public void testStringMappingWorks() throws StringMapperException
		{
		assert TypedValueStringMapper.get(String.class).parse(" Hello World  ").equals("Hello World");
		}

	@Test
	public void testDoubleArrayMappingWorks() throws StringMapperException
		{
		assert TypedValueStringMapper.get(double[].class).parse("1.1,2.2,3.3").equals(new double[]{
				1.1,
				2.2,
				3.3
		});
		}
	}
