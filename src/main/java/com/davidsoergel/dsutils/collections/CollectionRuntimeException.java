/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import com.davidsoergel.dsutils.ChainedRuntimeException;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class CollectionRuntimeException extends ChainedRuntimeException
	{
	public CollectionRuntimeException()
		{
		}

	public CollectionRuntimeException(final Throwable e)
		{
		super(e);
		}

	public CollectionRuntimeException(final String msg)
		{
		super(msg);
		}

	public CollectionRuntimeException(final Throwable e, final String s)
		{
		super(e, s);
		}
	}
