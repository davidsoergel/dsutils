package com.davidsoergel.dsutils.concurrent;

import com.davidsoergel.dsutils.DSArrayUtils;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class DepthFirstThreadPoolExecutor implements TreeExecutorService
	{
	private static final Logger logger = Logger.getLogger(DepthFirstThreadPoolExecutor.class);


	// we can't bound this one because a newly added task may turn out to have the highest priority
//	private PriorityQueue<ComparableFutureTask> priorityQueue = new PriorityQueue<ComparableFutureTask>();

	private ThreadPoolExecutor underlyingExecutor;
	private int queueSizePerTaskGroup;


	//private static final int DEFAULT_QUEUE_SIZE = 1000;


	//** allow unbounded queue
//	private Semaphore queueCount;  // need to do this manually because PriorityBlockingQueue is unbounded


	public DepthFirstThreadPoolExecutor()
		{
		//int cpus = Runtime.getRuntime().availableProcessors();
		//int queueSize = cpus * 2;
		this(Runtime.getRuntime().availableProcessors(),// - 1,  // account for callerRunsPolicy
		     Runtime.getRuntime().availableProcessors());  // *2 ?? should be enough to keep the threads busy, but no so much that we prematurely commit to execute low-priority tasks
		}

	private final TrackedThreadFactory threadFactory;

	public DepthFirstThreadPoolExecutor(int threads, int queueSizePerTaskGroup)
		{
		if (threads == 0)
			{
			threads = Runtime.getRuntime().availableProcessors();
			}
		if (queueSizePerTaskGroup == 0)
			{
			queueSizePerTaskGroup = threads;
			}

		this.queueSizePerTaskGroup = queueSizePerTaskGroup;

		threadFactory = new TrackedThreadFactory();

		//** unbounded queue...?  use queueSize?
		underlyingExecutor = new ThreadPoolExecutor(threads, threads, 0L, TimeUnit.MILLISECONDS,
		                                            new PriorityBlockingQueue<Runnable>(),
		                                            threadFactory);  // new LinkedBlockingQueue()); //
		//		queueCount = new Semaphore(queueSize);
		underlyingExecutor
				.prestartAllCoreThreads();  // this ensures that new tasks get queued rather than being executed immediately, so the queue has the opportunity to proioritize them

		// don't use CallerRunsPolicy, since we'll often end up with CPUs + 1 threads that way.
		// instead we'll just block in execute() below

		// ** unbounded queue
		//underlyingExecutor.setRejectedExecutionHandler(new CallerRunsFromQueuePolicy());  // throttle requests on full queue
		}

	/**
	 * A handler for rejected tasks that runs the rejected task directly in the calling thread of the <tt>execute</tt>
	 * method, unless the executor has been shut down, in which case the task is discarded.
	 */
	private static class CallerRunsFromQueuePolicy implements RejectedExecutionHandler
		{
		/**
		 * Creates a <tt>CallerRunsPolicy</tt>.
		 */
		public CallerRunsFromQueuePolicy()
			{
			}

		/**
		 * Executes task r in the caller's thread, unless the executor has been shut down, in which case the task is
		 * discarded.
		 *
		 * @param r the runnable task requested to be executed
		 * @param e the executor attempting to execute this task
		 */
		public void rejectedExecution(Runnable r, ThreadPoolExecutor e)
			{
			if (!e.isShutdown())
				{
				// run the highest-priority job...
				Runnable queueJob = e.getQueue().poll();

				// ...but don't forget the submitted one
				e.execute(r);


				logger.debug(
						"Rejected " + DSArrayUtils.asString(((ComparableFutureTask) r).priority, ",") + ", running "
								+ DSArrayUtils.asString(((ComparableFutureTask) queueJob).priority, ",") + " instead");

				if (queueJob != null)
					{
					queueJob.run();
					}
				}
			}
		}

	/*	@Override
	 protected void beforeExecute(Thread t, Runnable r)
		 {
		 // the task was pulled off the queue
		 queueCount.release();

		 super.beforeExecute(t, r);
		 }
 */


//	public void submit(ComparableFutureTask ftask)
//		{
//		// submit this job to the queue
//
//		priorityQueue.add(ftask);
//		// then perform the highest-priority job from the queue.  Recall CallerRunsFromQueuePolicy
//		ComparableFutureTask highPriorityTask = priorityQueue.poll();
//		logger.debug("Prioritizing " + DSArrayUtils.asString(ftask.priority, ",") + "; submitting " + DSArrayUtils
//				.asString(highPriorityTask.priority, ","));
//
//		underlyingExecutor.execute(highPriorityTask);
//
//		/*// the task will be put on the queue
//			queueCount.acquireUninterruptibly();  // block until the queue is not saturated
//
//		//	super.execute(command);
//
//		if (queueCount.tryAcquire())
//			{
//			super.execute(command);
//			}
//		else
//			{
//			//super.reject(command);
//			assert !getQueue().isEmpty();
//			getRejectedExecutionHandler().rejectedExecution(command, this);
//			}*/
//		}

/*	@Override
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
*/
	/**
	 * Indicate that the caller is running on this very thread pool, but that it is about to block waiting for other tasks
	 * to complete on the same pool.  Since the worker thread will be idle, we want to increment the number of allowed
	 * threads to keep the CPUs busy.
	 */
/*	private synchronized void waitingForChildren()
		{
		try
			{
			setMaximumPoolSize(getMaximumPoolSize() + 1);
			}
		catch (IllegalArgumentException e)
			{
			logger.error("Could not increment maximum pool size: " + getMaximumPoolSize(),e);
			}

		setCorePoolSize(getCorePoolSize() + 1);
		// this ensures that new tasks get queued rather than being executed immediately, so the queue has the opportunity to proioritize them
		prestartCoreThread();
		}
*/

	/**
	 * Indicate that the caller's children have all completed, so its thread will now continue; decrement the number of
	 * allowed threads to account for this
	 */
/*	private synchronized void doneWaitingForChildren()
		{
		setCorePoolSize(getCorePoolSize() - 1);

		try
			{
			setMaximumPoolSize(getMaximumPoolSize() - 1);
			}
		catch (IllegalArgumentException e)
			{
			logger.error("Could not decrement maximum pool size: " + getMaximumPoolSize(),e);
			}

		}
*/

	/**
	 * Submit a collection of tasks to the thread pool, and block until they complete.  Temporarily increments the maximum
	 * number of threads while blocking, to account for the case that the calling method was running on a worker thread of
	 * this very pool.
	 *
	 * @param tasks
	 * @param <T>
	 * @return
	 */
/*	public <T> Collection<T> submitAndGetAll(Iterable<Callable<T>> tasks) //, String format, int intervalSeconds)
		{
		Set<Future<T>> futures = new HashSet<Future<T>>();

		//	waitingForChildren();  // must call this before the execute() calls

		int[] p = _currentTaskPriority.get();

		int subp = 0;  // start the first task with the best priority

		for (Callable<T> task : tasks)
			{
			// then start each successive task with a worse priority
			subp++;
			ComparableFutureTask<T> ftask = new ComparableFutureTask(task, DSArrayUtils.add(p, subp));
			futures.add(ftask);
			submit(ftask);  // may block if the underlying queue is full
			}

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

//		doneWaitingForChildren();

		return result;
		}
*/

	/**
	 * Submit a collection of tasks to the thread pool, and block until they complete.  If the caller is running on a
	 * worker thread of this very pool, then temporarily increments the maximum number of threads while blocking.
	 *
	 * @param tasks
	 * @return
	 */
	public void submitAndWaitForAll(Iterable<Runnable> tasks) //, String reportFormat, int reportEveryNSeconds)
		{
		submitAndWaitForAll(tasks.iterator());
		}


	public ThreadPoolPerformanceStats shutdown()
		{
		ThreadPoolPerformanceStats stats = threadFactory.getStats();
		underlyingExecutor.shutdown();
		return stats;
		}

	/**
	 * Submit a collection of tasks to the pool.  Do not block until they complete, but run the TaskGroup's completed() callback then.
	 * @param tg
	 */
	/*	public void submitTaskGroup(TaskGroup tg)
		 {
		 int[] currentTaskPriority = _currentTaskPriority.get();

 //			if (isWorkerThread)
 //			  {
 //			  logger.debug("Adding a thread while " + DSArrayUtils.asString(currentTaskPriority,",") + " suspends");
 //			  waitingForChildren();  // must call this before the execute() calls
 //			  }
 //

		 int subPriority = 0;  // start the first task with the best priority

		 Iterator<Callable> tasks = tg.getTaskIterator();

		 while (tasks.hasNext())
			 {
			 Callable task = tasks.next();
			 // then start each successive task with a worse priority
			 subPriority++;
			 int[] s = DSArrayUtils.add(currentTaskPriority, subPriority);
			 FutureTask ftask = new ComparableFutureTask(task, s, tg);
			 tg.add(ftask);
			 underlyingExecutor.execute(ftask);  // remember CallerRunsPolicy
			 }
		 }
 */
	/**
	 * Submit a collection of tasks to the thread pool, and block until they complete.  If the caller is running on a
	 * worker thread of this very pool, then temporarily increments the maximum number of threads while blocking.
	 *
	 * @param tasks
	 * @return
	 */
	public void submitAndWaitForAll(Iterator<Runnable> tasks) //, String reportFormat, int reportEveryNSeconds)
		{
		TaskGroup taskGroup = new TaskGroup(tasks, queueSizePerTaskGroup); //, currentTaskPriority);

		//	boolean isWorkerThread = workers.contains(Thread.currentThread());  // can't do this because workers is private

		boolean isWorkerThread = Thread.currentThread().getThreadGroup() == threadFactory.group;
//						getGroup() getName().contains("pool"); // ** lame workaround; should use ThreadGroup instead

		// detangle spaghetti by splitting the two cases
		if (isWorkerThread)
			{
			submitAndWaitForAllFromWorkerThread(taskGroup);
			}
		else
			{
			submitAndWaitForAllFromNonWorkerThread(taskGroup);
			}
		}

	private void submitAndWaitForAllFromWorkerThread(final TaskGroup taskGroup)
		{
		//** reporting missing

		/*
		  while (taskGroup.hasNext())
			  {
			  // instead of blocking while higher-priority tasks complete, go ahead and execute something
			  while (!taskGroup.hasPermits())
				  {
				  runTaskFromQueue();
				  }

			  // ** need synchronized here?

			  // now there are permits available, so this should not block
			  ComparableFutureTask ftask = taskGroup.next();
			  underlyingExecutor.execute(ftask);
  }*/


		while (taskGroup.hasNext())
			{
			// try to get a permit
			ComparableFutureTask ftask = taskGroup.nextIfPermitAvailable();

			// execute other tasks until a permit is available
			while (taskGroup.hasNext() && ftask == null)
				{
				runTaskFromQueueOrSleep();
				ftask = taskGroup.nextIfPermitAvailable();
				}

			// got one

			// it should be impossible for the taskGroup to become exhausted since no one else should be draining it
			assert ftask != null;
			underlyingExecutor.execute(ftask);
			}


		// now all the tasks have been enqueued, but we need to wait for them to finish

		// instead of blocking while the tasks complete, go ahead and execute something
		while (!taskGroup.isDone())
			{
			runTaskFromQueueOrSleep();
			// if we just slept, cycle with this thread idle anyway... the taskGroup should be done soon enough
			}

		try
			{
			// all tasks are already done, but we still need to report any exceptions
			taskGroup.getAllExceptions();
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
		}

	private void submitAndWaitForAllFromNonWorkerThread(final TaskGroup taskGroup)
		{
		//** reporting missing

		while (taskGroup.hasNext())
			{
			// block until a permit is available
			ComparableFutureTask ftask = taskGroup.next();
			underlyingExecutor.execute(ftask);
			}

		// now all the tasks have been enqueued, but we need to wait for them to finish

		try
			{
			// block until all tasks are done
			taskGroup.blockUntilDone();
			taskGroup.getAllExceptions();
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
		}

	/**
	 * Careful: beforeExecute and afterExecute don't run this way
	 */
	private void runTaskFromQueueOrSleep() //TaskGroup taskGroup)
		{
		// BAD messing with the queue is "strongly discouraged"; does it work anyway?
		ComparableFutureTask task = (ComparableFutureTask) underlyingExecutor.getQueue().poll();
		if (task == null)
			{
			// workaround synchronization issue
			// a moment ago the task group was not done, but now that the queue is empty, it must be done after all
			//assert taskGroup.isDone();

			// either there was a synchronization issue and the taskGroup really is done now,
			// or there simply are no more tasks in the queue and we have to wait for other threads to complete the taskGroup

			try
				{
				Thread.sleep(100);
				}
			catch (InterruptedException e)
				{
				// no problem
				}
			}
		else
			{
			//underlyingExecutor.beforeExecute(task, getThread());
			task.run();
			//afterExecute
			//	task.done();
			}
		}
	}