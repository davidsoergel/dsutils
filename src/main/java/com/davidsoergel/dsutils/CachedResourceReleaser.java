package com.davidsoergel.dsutils;

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

	private static Set<HasReleaseableResources> resourceHogs;

	public static void register(HasReleaseableResources obj)
		{
		resourceHogs.add(obj);
		}

	public static void release()
		{
		for (HasReleaseableResources resourceHog : resourceHogs)
			{
			resourceHog.releaseCachedResources();
			}
		}
	}
