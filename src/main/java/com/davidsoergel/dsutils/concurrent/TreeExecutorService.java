package com.davidsoergel.dsutils.concurrent;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface TreeExecutorService extends ExecutorService
	{
	<T> Collection<T> submitAndGetAll(Collection<Callable<T>> tasks); //, String format, int intervalSeconds);

	void submitAndWaitForAll(Collection<Runnable> tasks); //,String format,int intervalSeconds);
	}
