package com.davidsoergel.dsutils.concurrent;

import com.davidsoergel.dsutils.collections.NextOnlyIterator;
import com.davidsoergel.dsutils.collections.NextOnlyIteratorAsNormalIterator;
import com.google.common.base.Function;

import java.util.Iterator;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class Parallel
	{
	public static <T> void forEach(NextOnlyIterator<T> tasks, final Function<T, Void> function)
		{
		forEach(new NextOnlyIteratorAsNormalIterator<T>(tasks), function);
		}

	public static <T> void forEach(Iterator<T> tasks, final Function<T, Void> function)
		{

		DepthFirstThreadPoolExecutor.getInstance().submitAndWaitForAll(new RunnableForEach<T>(tasks)
		{
		public void performAction(final T o)
			{
			function.apply(o);
			}
		});
		}
	}
