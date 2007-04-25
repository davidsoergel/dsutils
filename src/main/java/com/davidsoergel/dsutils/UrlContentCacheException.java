package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA. User: soergel Date: Apr 24, 2007 Time: 7:04:41 PM To change this template use File |
 * Settings | File Templates.
 */
public class UrlContentCacheException extends ChainedException
	{
	private static Logger logger = Logger.getLogger(UrlContentCacheException.class);

	public UrlContentCacheException(String s)
		{
		super(s);
		}

	public UrlContentCacheException(Exception e, String s)
		{
		super(e, s);
		}

	public UrlContentCacheException(Exception e)
		{
		super(e);
		}


	}
