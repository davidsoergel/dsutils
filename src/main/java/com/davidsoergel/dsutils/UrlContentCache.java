package com.davidsoergel.dsutils;

import java.io.File;
import java.net.URL;

/**
 * Created by IntelliJ IDEA. User: soergel Date: Apr 24, 2007 Time: 5:35:43 PM To change this template use File |
 * Settings | File Templates.
 */
public interface UrlContentCache
	{
	public void setMaxSize(long maxSize);

	public File getFile(String url) throws UrlContentCacheException;

	public File getFile(URL url) throws UrlContentCacheException;

	public File getFile(URL url, String checksum) throws UrlContentCacheException;

	public File getFile(String url, String checksum) throws UrlContentCacheException;
	}
