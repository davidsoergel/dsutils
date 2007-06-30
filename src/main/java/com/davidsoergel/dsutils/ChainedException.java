/*
 * dsutils - a collection of generally useful utility classes
 *
 * Copyright (c) 2001-2006 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * USA.
 *
 */

package com.davidsoergel.dsutils;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * A superclass for exceptions that are caused by another exception.  Inheriting from this class facilitates printing a
 * stack trace not only of the wrapper exception but also of the ultimate cause.
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
