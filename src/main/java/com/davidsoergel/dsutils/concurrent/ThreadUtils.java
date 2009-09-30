package com.davidsoergel.dsutils.concurrent;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class ThreadUtils
	{
	public static void checkInterrupted() throws InterruptedException
		{
		if (Thread.interrupted())
			{
			throw new InterruptedException();
			}
		}
	}
