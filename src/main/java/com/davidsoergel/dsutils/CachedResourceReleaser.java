package com.davidsoergel.dsutils;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class CachedResourceReleaser
	{
/*	private static CachedResourceReleaser _instance = new CachedResourceReleaser();
	public static CachedResourceReleaser getInstance()
		{
		return _instance;
		}*/

	@NotNull
	private static Set<HasReleaseableResources> resourceHogs = new HashSet<HasReleaseableResources>();

	public static synchronized void register(HasReleaseableResources obj)
		{
		resourceHogs.add(obj);
		}

	/**
	 * this should never be called from within a context that is synchronized on a HasReleaseableResources object, since
	 * that runs the risk of deadlock if we try to release it
	 */
	public static synchronized void release()
		{
		for (@NotNull HasReleaseableResources resourceHog : resourceHogs)
			{
			resourceHog.releaseCachedResources();
			}
		}
	}
