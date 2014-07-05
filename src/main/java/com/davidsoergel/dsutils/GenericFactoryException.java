/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;


/**
 * @version $Id$
 */
public class GenericFactoryException extends ChainedException
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(GenericFactoryException.class);


	// --------------------------- CONSTRUCTORS ---------------------------

	public GenericFactoryException(String s)
		{
		super(s);
		}

	public GenericFactoryException(Throwable t)
		{
		super(t);
		}
	}
