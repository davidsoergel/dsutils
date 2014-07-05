/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * A superclass for exceptions that are caused by another exception.  Inheriting from this class facilitates printing a
 * stack trace not only of the wrapper exception but also of the ultimate cause.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class ChainedException extends java.lang.Exception
	{
	// ------------------------------ FIELDS ------------------------------

	Throwable parent;


	// --------------------------- CONSTRUCTORS ---------------------------

	/**
	 * Creates new <code>FileUploadException</code> without detail message.
	 */
	public ChainedException()
		{
		super();
		}

	/**
	 * Constructor declaration
	 *
	 * @param e
	 */
	public ChainedException(Throwable e)
		{
		super();
		parent = e;
		}

	/**
	 * Constructs an <code>FileUploadException</code> with the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public ChainedException(String msg)
		{
		super(msg);
		}

	/**
	 * Constructor declaration
	 *
	 * @param e
	 * @param s
	 */
	public ChainedException(Throwable e, String s)
		{
		super(s);
		parent = e;
		}

	// -------------------------- OTHER METHODS --------------------------

	public void printStackTrace()
		{
		if (parent != null)
			{
			parent.printStackTrace();
			}

		super.printStackTrace();
		}

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
