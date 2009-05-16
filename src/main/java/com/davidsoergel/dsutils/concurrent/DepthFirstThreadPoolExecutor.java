package com.davidsoergel.dsutils.concurrent;

import com.davidsoergel.dsutils.DSArrayUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class DepthFirstThreadPoolExecutor extends ThreadPoolExecutor implements TreeExecutorService
	{
	private static final Logger logger = Logger.getLogger(DepthFirstThreadPoolExecutor.class);

	/**
	 * Keep track of the priority of the currently executing task on each thread.  Priorities are tree-structured: the
	 * subtasks of a given task inherit the priority of their parent, but also may have internal order.
	 */
	ThreadLocal<int[]> _currentTaskPriority = new ThreadLocal<int[]>();

	public DepthFirstThreadPoolExecutor()
		{
		this(Runtime.getRuntime().availableProcessors());
		}

	public DepthFirstThreadPoolExecutor(int threads)
		{
		super(threads, threads, 0L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>());
		}

	@Override
	protected <T> RunnableFuture<T> newTaskFor(Callable<T> tCallable)
		{
		return new ComparableFutureTask(tCallable, _currentTaskPriority.get());
		//super.newTaskFor(tCallable);
		}

	@Override
	protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T t)
		{
		return super.newTaskFor(runnable, t);
		}

	/**
	 * Indicate that the caller is running on this very thread pool, but that it is about to block waiting for other tasks
	 * to complete on the same pool.  Since the worker thread will be idle, we want to increment the number of allowed
	 * threads to keep the CPUs busy.
	 */
	private void waitingForChildren()
		{
		setMaximumPoolSize(getMaximumPoolSize() + 1);
		}


	/**
	 * Indicate that the caller's children have all completed, so its thread will now continue; decrement the number of
	 * allowed threads to account for this
	 */
	private void doneWaitingForChildren()
		{
		setMaximumPoolSize(getMaximumPoolSize() + 1);
		}

	/**
	 * Submit a collection of tasks to the thread pool, and block until they complete.  Temporarily increments the maximum
	 * number of threads while blocking, to account for the case that the calling method was running on a worker thread of
	 * this very pool.
	 *
	 * @param tasks
	 * @param <T>
	 * @return
	 */
	public <T> Collection<T> submitAndGetAll(Collection<Callable<T>> tasks) //, String format, int intervalSeconds)
		{
		Set<Future<T>> futures = new HashSet<Future<T>>();

		int[] p = _currentTaskPriority.get();

		int subp = 0;  // start the first task with the best priority

		for (Callable<T> task : tasks)
			{
			// then start each successive task with a worse priority
			subp++;
			FutureTask<T> ftask = new ComparableFutureTask(task, DSArrayUtils.add(p, subp));
			futures.add(ftask);
			execute(ftask);
			}

		waitingForChildren();

		Set<T> result = new HashSet<T>();

		try
			{
			for (Future future : futures)
				{
				future.get();
				}
			}
		catch (ExecutionException e)
			{
			logger.error("Error", e);
			throw new Error(e);
			}
		catch (InterruptedException e)
			{
			logger.error("Error", e);
			throw new Error(e);
			}

		doneWaitingForChildren();

		return result;
		}

	/**
	 * Submit a collection of tasks to the thread pool, and block until they complete.  Temporarily increments the maximum
	 * number of threads while blocking, to account for the case that the calling method was running on a worker thread of
	 * this very pool.
	 *
	 * @param tasks
	 * @return
	 */
	public void submitAndWaitForAll(Collection<Runnable> tasks) //, String reportFormat, int reportEveryNSeconds)
		{
		List<Future> futures = new ArrayList<Future>(tasks.size());

		int[] p = _currentTaskPriority.get();

		int subp = 0;  // start the first task with the best priority

		for (Runnable task : tasks)
			{
			// then start each successive task with a worse priority
			subp++;
			FutureTask ftask = new ComparableFutureTask(task, DSArrayUtils.add(p, subp));
			futures.add(ftask);
			execute(ftask);
			}

		waitingForChildren();

		int doneCount = 0;

		try
			{
			// request the futures in their priority order, so we don't block forever waiting for the last one
			// ** alternatively maybe it's more efficient to block on the last one on purpose, but then we can't monitor progress as easily

			//** reporting missing

			for (Future future : futures)
				{
				future.get();
				doneCount++;
/*
				if(secondsSinceLastReport >= reportEveryNSeconds)
					{
					logger.debug(String.format(reportFormat, doneCount));
					secondsSin
					}*/
				}


			//	logger.debug(String.format(reportFormat, doneCount));

			}
		catch (ExecutionException e)
			{
			logger.error("Error", e);
			throw new Error(e);
			}
		catch (InterruptedException e)
			{
			logger.error("Error", e);
			throw new Error(e);
			}

		doneWaitingForChildren();
		}


	private class ComparableFutureTask<T> extends FutureTask<T> implements Comparable<ComparableFutureTask>
		{
		int[] priority;

		public ComparableFutureTask(Callable<T> tCallable, int[] priority)
			{
			super(tCallable);
			this.priority = priority;
			}

		public ComparableFutureTask(Runnable tCallable, int[] priority)
			{
			super(tCallable, null);
			this.priority = priority;
			}

		/**
		 * Tree-structured comparison
		 *
		 * @param o
		 * @return
		 */
		public int compareTo(ComparableFutureTask o)
			{
			for (int i = 0; i < i; i++)
				{
				if (priority[i] < o.priority[i])
					{
					return -1;
					}
				if (priority[i] > o.priority[i])
					{
					return 1;
					}
				}
			return 0;
			}

		@Override
		public void run()
			{
			int[] lastPriority = _currentTaskPriority.get();
			_currentTaskPriority.set(priority);
			super.run();
			_currentTaskPriority.set(lastPriority);
			}
		}
	}
