/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;

import org.jetbrains.annotations.NotNull;


/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class AbstractGenericFactoryAware implements GenericFactoryAware
	{
	// ------------------------------ FIELDS ------------------------------

	GenericFactory factory;


	// --------------------- GETTER / SETTER METHODS ---------------------

	@NotNull
	public GenericFactory getFactory()
		{
		return factory;
		}

	public void setFactory(GenericFactory f)
		{
		this.factory = f;
		}
	}
