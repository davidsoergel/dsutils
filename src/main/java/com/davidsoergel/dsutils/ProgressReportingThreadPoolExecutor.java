package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class ProgressReportingThreadPoolExecutor extends ThreadPoolExecutor
	{
	private static final Logger logger = Logger.getLogger(ProgressReportingThreadPoolExecutor.class);

	public ProgressReportingThreadPoolExecutor()
		{
		super(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), 0L,
		      TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		}

	//Collection<Future> futures = new HashSet<Future>();

	public void finish(String s, int reportEveryNSeconds)
		{
		shutdown();

		// then report progress every 30 seconds
		while (!isTerminated())
			{
			try
				{
				awaitTermination(reportEveryNSeconds, TimeUnit.SECONDS);
				}
			catch (InterruptedException e)
				{
				// no problem, just cycle
				}

			logger.debug(String.format(s, getCompletedTaskCount()));
			}
		logger.debug(String.format(s, getCompletedTaskCount()));
		}

	/*@Override
	public Future<?> submit(Runnable task)
		{
		Future<?> fut = super.submit(task);
		futures.add(fut);
		return fut;
		}*/
	}
