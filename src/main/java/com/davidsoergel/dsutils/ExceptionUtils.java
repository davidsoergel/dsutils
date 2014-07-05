/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @version $Id$
 */
public class ExceptionUtils
	{
	// -------------------------- STATIC METHODS --------------------------

	/**
	 * Get the stack trace from a Throwable as a String.
	 */
	public static String getStackTrace(@NotNull Throwable e)
		{
		@NotNull StringWriter sw = new StringWriter();

		e.printStackTrace(new PrintWriter(sw));

		return sw.toString();
		}
	}
