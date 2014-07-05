/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.stringmapper;

import junit.framework.TestCase;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
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
		assert TypedValueStringMapper.get(Class.class)
				.parse("com.davidsoergel.dsutils.stringmapper.TypedValueStringMapper")
				.equals(TypedValueStringMapper.class);
		}

	@Test
	public void testDoubleObjectArrayMappingWorks() throws StringMapperException
		{
		@NotNull Double[] o = (Double[]) TypedValueStringMapper.get(Double[].class).parse("1.1,2.2,3.3");
		@NotNull Double[] expected = {1.1, 2.2, 3.3};
		assert Arrays.deepEquals(o, expected);
		}


	@Test
	public void testDoublePrimitiveArrayMappingWorks() throws StringMapperException
		{
		@NotNull double[] o = (double[]) TypedValueStringMapper.get(double[].class).parse("1.1,2.2,3.3");
		@NotNull double[] expected = {1.1, 2.2, 3.3};
		assert Arrays.equals(o, expected);
		}
	}
