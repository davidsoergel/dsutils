/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;


/**
 * A Factory that creates objects of type T, using whatever constructor arguments might be needed.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public interface GenericFactory<T>
	{
	// -------------------------- OTHER METHODS --------------------------

	/**
	 * Creates a new object of the type appropriate for this factory.
	 *
	 * @param constructorArguments arguments to be passed to the constructor
	 * @return the newly instantiated object
	 * @throws GenericFactoryException if no constructor matching the arguments is found
	 */
	T create(Object... constructorArguments) throws GenericFactoryException;

	//public <T> T create(String context, Class<T> theClass, Object... constructorArguments);

	/**
	 * Returns the class which this factory creates.  This may be different from the generic type T, which may itself have
	 * type parameters.  Here we get only the raw class.  This information should also be extractable from instances
	 * implementing this interface, but that's a complicated reflection exercise which this method avoids.
	 *
	 * @return the class which this factory creates.
	 */
	Class getCreatesClass();
	}
