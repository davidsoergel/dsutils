/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.subclassfindertest;

import com.davidsoergel.dsutils.ChainedException;
import com.davidsoergel.dsutils.PluginException;
import com.davidsoergel.dsutils.SubclassFinder;
import com.davidsoergel.dsutils.increment.BasicIncrementor;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @version $Id$
 */
public class SubclassFinderTest//extends TestCase
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(SubclassFinderTest.class);


	public TestGenericInterface<Number> testGenericFieldNumber;
	public TestGenericInterface<Integer> testGenericFieldInteger;
	public TestGenericInterface<Double> testGenericFieldDouble;


	// -------------------------- OTHER METHODS --------------------------

	@Test
	public void subclassFinderRecursesFilesystemPackages() throws IOException
		{
		@NotNull List classes = SubclassFinder
				.findRecursive("com.davidsoergel.dsutils", ChainedException.class, new BasicIncrementor(null, null));
		assert classes.contains(PluginException.class);
		assert classes.contains(SubclassFinderTestException.class);
		}

	@Test
	public void subclassFinderWorksWithGenericInheritance() throws NoSuchFieldException, IOException
		{
		@NotNull ParameterizedType t =
				(ParameterizedType) (this.getClass().getField("testGenericFieldNumber").getGenericType());
		@NotNull List classes =
				SubclassFinder.findRecursive("com.davidsoergel.dsutils", t, new BasicIncrementor(null, null));
		assert classes.size() == 2;
		assert classes.contains(TestGenericClassOne.class);
		assert classes.contains(TestGenericClassTwo.class);
		}

	@Test
	public void subclassFinderWorksWithSpecificGenerics() throws NoSuchFieldException, IOException
		{
		@NotNull ParameterizedType t =
				(ParameterizedType) (this.getClass().getField("testGenericFieldInteger").getGenericType());
		@NotNull List classes =
				SubclassFinder.findRecursive("com.davidsoergel.dsutils", t, new BasicIncrementor(null, null));
		assert classes.size() == 1;
		assert classes.contains(TestGenericClassOne.class);

		t = (ParameterizedType) (this.getClass().getField("testGenericFieldDouble").getGenericType());
		classes = SubclassFinder.findRecursive("com.davidsoergel.dsutils", t, new BasicIncrementor(null, null));
		assert classes.size() == 1;
		assert classes.contains(TestGenericClassTwo.class);
		}
	}
