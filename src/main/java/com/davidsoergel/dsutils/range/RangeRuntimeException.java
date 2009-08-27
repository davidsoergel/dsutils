package com.davidsoergel.dsutils.range;

import com.davidsoergel.dsutils.ChainedRuntimeException;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class RangeRuntimeException extends ChainedRuntimeException
	{
	public RangeRuntimeException()
		{
		}

	public RangeRuntimeException(final Throwable e)
		{
		super(e);
		}

	public RangeRuntimeException(final String msg)
		{
		super(msg);
		}

	public RangeRuntimeException(final Throwable e, final String s)
		{
		super(e, s);
		}
	}
