/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;


/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class UrlContentCacheException extends ChainedException
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(UrlContentCacheException.class);


	// --------------------------- CONSTRUCTORS ---------------------------

	public UrlContentCacheException(String s)
		{
		super(s);
		}

	public UrlContentCacheException(Exception e)
		{
		super(e);
		}

	public UrlContentCacheException(Exception e, String s)
		{
		super(e, s);
		}
	}
