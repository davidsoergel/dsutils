package com.davidsoergel.dsutils;

import junit.framework.TestCase;
import org.testng.annotations.Test;

import java.util.Arrays;

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
	public void testDoubleObjectMappingWorks() throws StringMapperException
		{
		assert TypedValueStringMapper.get(Double.class).parse(".123").equals(.123);
		}

	@Test
	public void testDoublePrimitiveMappingWorks() throws StringMapperException
		{
		assert TypedValueStringMapper.get(double.class).parse(".123").equals(.123);
		}


	@Test
	public void testClassMappingWorks() throws StringMapperException
		{
		assert TypedValueStringMapper.get(Class.class).parse("com.davidsoergel.dsutils.TypedValueStringMapper")
				.equals(TypedValueStringMapper.class);
		}

	@Test
	public void testDoubleObjectArrayMappingWorks() throws StringMapperException
		{
		Double[] o = (Double[]) TypedValueStringMapper.get(Double[].class).parse("1.1,2.2,3.3");
		Double[] expected = {
				1.1,
				2.2,
				3.3
		};
		assert Arrays.deepEquals(o, expected);
		}


	@Test
	public void testDoublePrimitiveArrayMappingWorks() throws StringMapperException
		{
		double[] o = (double[]) TypedValueStringMapper.get(double[].class).parse("1.1,2.2,3.3");
		double[] expected = {
				1.1,
				2.2,
				3.3
		};
		assert Arrays.equals(o, expected);
		}
	}
