/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class ContractTestAwareContractTest<T> extends ContractTestAware<T> implements ContractTest
	{
	String testName;

	/**
	 * {@inheritDoc}
	 */
	public void setTestName(String testName)
		{
		this.testName = testName;
		}

	/**
	 * {@inheritDoc}
	 */
	public String getTestName()
		{
		return testName;
		}
	}
