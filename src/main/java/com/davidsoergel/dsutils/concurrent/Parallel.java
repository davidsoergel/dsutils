package com.davidsoergel.dsutils.concurrent;

import com.davidsoergel.dsutils.collections.NextOnlyIterator;
import com.davidsoergel.dsutils.collections.NextOnlyIteratorAsNormalIterator;
import com.google.common.base.Function;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class Parallel
	{
	private static final Logger logger = Logger.getLogger(Parallel.class);

	public static <T> void forEach(NextOnlyIterator<T> tasks, final Function<T, Void> function)
		{
		forEach(new NextOnlyIteratorAsNormalIterator<T>(tasks), function);
		}

	public static <T> void forEach(Iterator<T> tasks, final Function<T, Void> function)
		{

		DepthFirstThreadPoolExecutor.getInstance().submitAndWaitForAll(new ForEach<T>(tasks)
		{
		public void performAction(final T o)
			{
			function.apply(o);
			}
		});
		}

	// unlike RunnableForEach, this one puts the Iterator.next() call within each Runnable
	// it will iterate forever until one of the Runnables throws an exception, so it's important to throttle elsewhere, e.g. via the permits in DepthFirstTPE.
	private abstract static class ForEach<T> implements Iterator<Runnable>
		{
		Iterator<T> iter;

		public ForEach(final Iterator<T> tasks)
			{
			iter = tasks;
			}

		boolean hasNext = true;

		public boolean hasNext()
			{
			return hasNext;
			}

		public Runnable next()
			{
			return new Runnable()
			{
			public void run()
				{

				T o;
				try
					{
					o = iter.next();
					}
				catch (NoSuchElementException e)
					{
					// happens all the time due to concurrency, no problem
					hasNext = false;
					return;
					}

				//		try
				//			{
				performAction(
						o); // exceptions are thrown from DepthFirstThneadPoolIterator wrapped in RuntimeExecutionException and ExecutionException
				/*			}
			   catch (Throwable e)
				   {
				   logger.error("Error", e);
				   }*/
				}
			};
			}

		public void remove()
			{
			}

		public abstract void performAction(T o);
		}

	public static void shutdown()
		{
		DepthFirstThreadPoolExecutor.getInstance().shutdown();
		}
	}
