package com.davidsoergel.dsutils.concurrent;

import com.davidsoergel.dsutils.DSArrayUtils;
import com.davidsoergel.dsutils.collections.MappingIterator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
class TaskGroup extends MappingIterator<Runnable, ComparableFutureTask> //implements Iterator<FutureTask>  // could be
	{
	private Set<ComparableFutureTask> futuresEnqueued = new HashSet<ComparableFutureTask>();

	private Set<ComparableFutureTask> futuresDoneAwaitingResultCollection = new HashSet<ComparableFutureTask>();

	public static ThreadLocal<int[]> _currentTaskPriority = new ThreadLocal<int[]>();

	private int[] currentTaskPriority;
	private int subPriority = 0;  // start the first task with the best priority

	Semaphore outstandingTasks;

	protected TaskGroup(final Iterator<Runnable> taskIterator, int queueSize) //, int[] currentTaskPriority)
		{
		super(taskIterator);
		this.currentTaskPriority = _currentTaskPriority.get();
		outstandingTasks = new Semaphore(queueSize);
		}

	/**
	 * Check whether all jobs have completed, whether or not they have had their results collected already
	 *
	 * @return
	 */
	public synchronized boolean isDone()
		{
		return futuresEnqueued.isEmpty() && !super.hasNext();
		}

	public void blockUntilDone() throws ExecutionException, InterruptedException
		{
		assert !super.hasNext();

		// slightly weird to avoid ConcurrentModificationException
		while (!futuresEnqueued.isEmpty())
			{
			FutureTask future = futuresEnqueued.iterator().next();
			if (future != null)
				{
				future.get();  // this removes the task from futuresEnqueued, via reportDone, so we'll get a different one the next time around
				}
/*
			Iterator<ComparableFutureTask> futuresEnqueuedIterator = futuresEnqueued.iterator();
			while (futuresEnqueuedIterator.hasNext())
				{
				FutureTask future = futuresEnqueuedIterator.next();
				future.get();

				// ** liable to produce ConcurrentModificationException?

				// reportDone deals with these
				//	futuresEnqueuedIterator.remove();
				//	assert futuresDoneAwaitingResultCollection.remove(future);
				}*/
			}
		}

	/**
	 * Block until all tasks are done, throwing the first exception encountered, if any.
	 *
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public synchronized void getAllExceptions() throws ExecutionException, InterruptedException
		{
		assert isDone();

		Iterator<ComparableFutureTask> futuresDoneIterator = futuresDoneAwaitingResultCollection.iterator();
		while (futuresDoneIterator.hasNext())
			{
			FutureTask future = futuresDoneIterator.next();
			future.get();
			futuresDoneIterator.remove();
			}
		}

	public synchronized ComparableFutureTask function(final Runnable task)
		{
		// then start each successive task with a worse priority
		int[] s = DSArrayUtils.add(currentTaskPriority, subPriority);
		ComparableFutureTask ftask = new ComparableFutureTask(task, s, this);
		futuresEnqueued.add(ftask);
		subPriority++;
		return ftask;
		}

	public ComparableFutureTask next()
		{
		if (!hasNext())
			{
			return null;
			}
		// wait for a permit
		outstandingTasks.acquireUninterruptibly();
		return super.next();
/*		Runnable task = taskIterator.next();
		// then start each successive task with a worse priority
		subPriority++;
		int[] s = DSArrayUtils.add(currentTaskPriority, subPriority);
		FutureTask ftask = new ComparableFutureTask(task, s);
		futures.add(ftask);
		return ftask;*/
		}
/*
	public void remove()
		{
		throw new NotImplementedException();
		}*/


	public ComparableFutureTask nextIfPermitAvailable()
		{
		if (hasPermits())
			{
			return next();
			}
		return null;
		}

	public boolean hasPermits()
		{
		return outstandingTasks.availablePermits() > 0;
		}

	public synchronized void reportDone(final ComparableFutureTask task)
		{
		if (!futuresEnqueued.remove(task))
			{
			throw new ThreadingException("Can't report a task complete on the wrong TaskGroup");
			}
		futuresDoneAwaitingResultCollection.add(task);
		outstandingTasks.release();
		}
	}