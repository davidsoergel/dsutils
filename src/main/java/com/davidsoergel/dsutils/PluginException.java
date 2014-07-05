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
public class PluginException extends ChainedException
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(PluginException.class);


	// --------------------------- CONSTRUCTORS ---------------------------

	public PluginException(String s)
		{
		super(s);
		}

	public PluginException(Throwable t)
		{
		super(t);
		}
	}
