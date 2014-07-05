/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.math;

import com.davidsoergel.dsutils.ChainedException;
import org.apache.log4j.Logger;

/**
 * @version $Id$
 */
public class MathUtilsException extends ChainedException
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(MathUtilsException.class);


	// --------------------------- CONSTRUCTORS ---------------------------

	public MathUtilsException(String s)
		{
		super(s);
		}

	public MathUtilsException(Throwable t)
		{
		super(t);
		}
	}
