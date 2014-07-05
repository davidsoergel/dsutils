/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;

import org.testng.ITest;

/**
 * Represents a test against an interface or abstract class.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface ContractTest extends ITest
	{
	/**
	 * Return a name for this test instance, typically indicating which concrete class is being tested.
	 *
	 * @param testName
	 */
	void setTestName(String testName);
	}
