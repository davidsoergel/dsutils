package com.davidsoergel.dsutils;

import java.io.File;
import java.net.MalformedURLException;
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

	public void clear();

	String getChecksum(String s) throws MalformedURLException, UrlContentCacheException;

	String getChecksum(URL s) throws UrlContentCacheException;

	String recalculateChecksum(String s) throws MalformedURLException, UrlContentCacheException;

	String recalculateChecksum(URL s) throws UrlContentCacheException;
	}
