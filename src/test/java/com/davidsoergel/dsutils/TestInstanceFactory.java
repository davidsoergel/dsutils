/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;

/**
 * Marks a test class that is able to provide instances of an object to test.  Useful in combination with Contract
 * Tests, which require concrete instances of classes purportedly conforming to the contract (represented as the generic
 * type T) in order to test them.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public interface TestInstanceFactory<T>
	{
	// -------------------------- OTHER METHODS --------------------------

	/**
	 * Returns a "test" instance of an object implementing or extending the generic type T.  The returned object may be
	 * configured with known data to facilitate testing it.  Also, it may hold references to mock objects and so forth in
	 * order to isolate the testable unit.
	 *
	 * @return
	 * @throws Exception
	 */
	T createInstance() throws Exception;
	}
