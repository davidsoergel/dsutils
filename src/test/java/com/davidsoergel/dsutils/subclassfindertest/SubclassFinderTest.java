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

package com.davidsoergel.dsutils.subclassfindertest;

import com.davidsoergel.dsutils.ChainedException;
import com.davidsoergel.dsutils.PluginException;
import com.davidsoergel.dsutils.SubclassFinder;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author lorax
 * @version 1.0
 */
public class SubclassFinderTest//extends TestCase
	{
	// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(SubclassFinderTest.class);


	public TestGenericInterface<Number> testGenericFieldNumber;
	public TestGenericInterface<Integer> testGenericFieldInteger;
	public TestGenericInterface<Double> testGenericFieldDouble;


	// -------------------------- OTHER METHODS --------------------------

	@Test
	public void subclassFinderRecursesFilesystemPackages() throws IOException
		{
		List classes = SubclassFinder.findRecursive("com.davidsoergel.dsutils", ChainedException.class);
		assert classes.contains(PluginException.class);
		assert classes.contains(SubclassFinderTestException.class);
		}

	@Test
	public void subclassFinderWorksWithGenericInheritance() throws NoSuchFieldException, IOException
		{
		ParameterizedType t = (ParameterizedType) (this.getClass().getField("testGenericFieldNumber").getGenericType());
		List classes = SubclassFinder.findRecursive("com.davidsoergel.dsutils", t);
		assert classes.size() == 2;
		assert classes.contains(TestGenericClassOne.class);
		assert classes.contains(TestGenericClassTwo.class);
		}

	@Test
	public void subclassFinderWorksWithSpecificGenerics() throws NoSuchFieldException, IOException
		{
		ParameterizedType t =
				(ParameterizedType) (this.getClass().getField("testGenericFieldInteger").getGenericType());
		List classes = SubclassFinder.findRecursive("com.davidsoergel.dsutils", t);
		assert classes.size() == 1;
		assert classes.contains(TestGenericClassOne.class);

		t = (ParameterizedType) (this.getClass().getField("testGenericFieldDouble").getGenericType());
		classes = SubclassFinder.findRecursive("com.davidsoergel.dsutils", t);
		assert classes.size() == 1;
		assert classes.contains(TestGenericClassTwo.class);
		}
	}
