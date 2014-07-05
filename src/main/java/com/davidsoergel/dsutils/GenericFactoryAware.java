/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;


import org.jetbrains.annotations.NotNull;

/**
 * Marks a class for which instances keep track of the factory that produced them.  This is useful for cases when the
 * instances need to create additional instances using the same factory.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public interface GenericFactoryAware
	{
	// -------------------------- OTHER METHODS --------------------------

	/**
	 * Returns the factory that produced this object.  This is useful because the factory may have some configuration
	 * parameters, and we may want to construct a new object using the same parameters.
	 *
	 * @return
	 */
	@NotNull
	GenericFactory getFactory();

	/**
	 * Sets the factory that produced this object.
	 *
	 * @param f the factory
	 */
	void setFactory(GenericFactory f);
	}
