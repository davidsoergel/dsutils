/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * @version $Id$
 */
public class ChainedError extends Error
	{
	// ------------------------------ FIELDS ------------------------------

	Throwable parent;


	// --------------------------- CONSTRUCTORS ---------------------------

	/**
	 * Creates new <code>FileUploadException</code> without detail message.
	 */
	public ChainedError()
		{
		super();
		}

	/**
	 * Constructor declaration
	 *
	 * @param e
	 */
	public ChainedError(Throwable e)
		{
		super();
		parent = e;
		}

	/**
	 * Constructs an <code>FileUploadException</code> with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public ChainedError(String msg)
		{
		super(msg);
		}

	/**
	 * Constructor declaration
	 *
	 * @param e
	 * @param s
	 */
	public ChainedError(Throwable e, String s)
		{
		super(s);
		parent = e;
		}

	// -------------------------- OTHER METHODS --------------------------

	/**
	 * Method declaration
	 *
	 * @param pw
	 */
	public void printStackTrace(PrintWriter pw)
		{
		if (parent != null)
			{
			parent.printStackTrace(pw);
			}
		super.printStackTrace(pw);
		}

	/**
	 * Method declaration
	 *
	 * @param s
	 */
	public void printStackTrace(PrintStream s)
		{
		if (parent != null)
			{
			parent.printStackTrace(s);
			}
		super.printStackTrace(s);
		}
	}
