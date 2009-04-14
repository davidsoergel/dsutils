package com.davidsoergel.dsutils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class FakeFuture<T> implements Future<T>
	{
	T val;

	public FakeFuture(T val)
		{
		this.val = val;
		}

	public boolean cancel(boolean mayInterruptIfRunning)
		{
		return false;
		}

	public boolean isCancelled()
		{
		return false;
		}

	public boolean isDone()
		{
		return true;
		}

	public T get() throws InterruptedException, ExecutionException
		{
		return val;
		}

	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
		{
		return val;
		}
	}
