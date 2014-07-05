/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.ChainedRuntimeException;

/**
 * @version $Id$
 */
public class StringMapperException extends ChainedRuntimeException
	{
	// --------------------------- CONSTRUCTORS ---------------------------

	public StringMapperException(String s)
		{
		super(s);
		}

	public StringMapperException(Throwable e)
		{
		super(e);
		}

	public StringMapperException(Throwable e, String s)
		{
		super(e, s);
		}
	}
