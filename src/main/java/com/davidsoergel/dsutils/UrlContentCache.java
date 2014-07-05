/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Cache URL contents in the filesystem.  Maintains checksum files for verification, and stores the last access time for
 * the file as the modified date on the checksum file (since that's the only one available to us in Java).  For now,
 * ignores expiration times and other such HTTP metadata.  Enforces a maximum size on the cache, keeping the most
 * recently accessed files and expiring older ones as needed.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public interface UrlContentCache
	{
	// -------------------------- OTHER METHODS --------------------------

	/**
	 * Clear the disk cache.
	 */
	void clear();

	/**
	 * Get the checksum for the file identified by the given URL.
	 *
	 * @param s a String containing a URL.
	 * @return a String containing some kind of checksum (perhaps just an integer, in the case of crc32).
	 * @throws MalformedURLException    if the provided string is not a valid URL.
	 * @throws UrlContentCacheException if anything else goes wrong
	 */
	String getChecksum(String s) throws MalformedURLException, UrlContentCacheException;

	/**
	 * Gets the checksum for the file identified by the given URL.
	 *
	 * @param s a URL.
	 * @return a String containing some kind of checksum (perhaps just an integer, in the case of crc32).
	 * @throws UrlContentCacheException if anything goes wrong
	 */
	String getChecksum(URL s) throws UrlContentCacheException;

	/**
	 * Gets a file identified by the given URL.  If the file is available in the cache, it is simply returned; otherwise it
	 * is retrieved and cached first.  This may cause other files to be expired from the cache to make space.
	 *
	 * @param url a String containing a URL.
	 * @return the File
	 * @throws MalformedURLException    if the provided string is not a valid URL.
	 * @throws UrlContentCacheException if anything else goes wrong
	 */
	@NotNull
	File getFile(String url) throws UrlContentCacheException, MalformedURLException;

	/**
	 * Gets a file identified by the given URL.  If the file is available in the cache, it is simply returned; otherwise it
	 * is retrieved and cached first.  This may cause other files to be expired from the cache to make space.
	 *
	 * @param url a URL.
	 * @return the File
	 * @throws UrlContentCacheException if anything goes wrong
	 */
	@NotNull
	File getFile(URL url) throws UrlContentCacheException;

	/**
	 * Gets a file identified by the given URL, verifying that it matches the given checksum.  If the file is available in
	 * the cache and the checksum matches, it is simply returned; otherwise it is retrieved and cached first.  This may
	 * cause other files to be expired from the cache to make space.
	 *
	 * @param url a URL.
	 * @return the File
	 * @throws UrlContentCacheException if anything else goes wrong
	 */
	@NotNull
	File getFile(URL url, String checksum) throws UrlContentCacheException;

	/**
	 * Gets a file identified by the given URL, verifying that it matches the given checksum.  If the file is available in
	 * the cache and the checksum matches, it is simply returned; otherwise it is retrieved and cached first.  This may
	 * cause other files to be expired from the cache to make space.
	 *
	 * @param url a String containing a URL.
	 * @return the File
	 * @throws MalformedURLException    if the provided string is not a valid URL.
	 * @throws UrlContentCacheException if anything else goes wrong
	 */
	@NotNull
	File getFile(String url, String checksum) throws UrlContentCacheException, MalformedURLException;

	/**
	 * Recomputes the checksum for the file identified by the given URL, and returns it.  Retrieves and caches the file
	 * first, if necessary. This may cause other files to be expired from the cache to make space.
	 *
	 * @param url a String containing a URL.
	 * @return the File
	 * @throws MalformedURLException    if the provided string is not a valid URL.
	 * @throws UrlContentCacheException if anything else goes wrong
	 */
	String recalculateChecksum(String s) throws MalformedURLException, UrlContentCacheException;

	/**
	 * Recomputes the checksum for the file identified by the given URL, and returns it.  Retrieves and caches the file
	 * first, if necessary. This may cause other files to be expired from the cache to make space.
	 *
	 * @param url a URL.
	 * @return the File
	 * @throws UrlContentCacheException if anything  goes wrong
	 */
	String recalculateChecksum(URL s) throws UrlContentCacheException;

	/**
	 * Sets the maximum disk space that the cache may use, in bytes.  This takes effect when there is an attempt to
	 * download a new file, at which point old files are expired from the cache until there is enough room for the new
	 * one.
	 *
	 * @param maxSize the maximum disk space that the cache may use, in bytes.
	 */
	void setMaxSize(long maxSize);
	}
