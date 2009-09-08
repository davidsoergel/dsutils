package com.davidsoergel.dsutils.concurrent;

import com.davidsoergel.dsutils.ChainedRuntimeException;

import java.util.concurrent.ExecutionException;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class RuntimeExecutionException extends ChainedRuntimeException
	{
	private boolean outOfMemory;

	public RuntimeExecutionException()
		{
		}

	public RuntimeExecutionException(final Throwable e)
		{
		super(e);
		outOfMemory = (e instanceof ExecutionException && e.getCause() instanceof OutOfMemoryError);
		}

	public RuntimeExecutionException(final String msg)
		{
		super(msg);
		}

	public RuntimeExecutionException(final Throwable e, final String s)
		{
		super(e, s);
		outOfMemory = (e instanceof ExecutionException && e.getCause() instanceof OutOfMemoryError);
		}

	public boolean isOutOfMemory()
		{
		return outOfMemory;
		}
	}
