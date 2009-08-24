/*
 * Copyright (c) 2001-2008 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * dev@davidsoergel.com
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the names of any contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package com.davidsoergel.dsutils;

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
	File getFile(String url) throws UrlContentCacheException, MalformedURLException;

	/**
	 * Gets a file identified by the given URL.  If the file is available in the cache, it is simply returned; otherwise it
	 * is retrieved and cached first.  This may cause other files to be expired from the cache to make space.
	 *
	 * @param url a URL.
	 * @return the File
	 * @throws UrlContentCacheException if anything goes wrong
	 */
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
