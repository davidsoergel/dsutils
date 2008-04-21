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

/* $Id$ */

package com.davidsoergel.dsutils;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by IntelliJ IDEA. User: lorax Date: Apr 24, 2007 Time: 8:17:05 PM To change this template use File | Settings
 * | File Templates.
 */
public class UrlContentCacheTest
	{
	// ------------------------------ FIELDS ------------------------------

	UrlContentCache cache = null;


	// -------------------------- OTHER METHODS --------------------------

	@Test
	public void accessTimeIsUpdated() throws UrlContentCacheException, InterruptedException
		{
		cache.getFile("http://www.davidsoergel.com/testfile");
		long origDate = new File("/tmp/test/www.davidsoergel.com/testfile.crc32").lastModified();
		Thread.sleep(1000);
		cache.getFile("http://www.davidsoergel.com/testfile");
		long newDate = new File("/tmp/test/www.davidsoergel.com/testfile.crc32").lastModified();
		assert newDate > origDate;
		}

	@Test
	public void cacheCanBeCleared() throws UrlContentCacheException
		{
		cache.getFile("http://www.davidsoergel.com/testfile");
		assert (new File("/tmp/test").exists());
		cache.clear();
		assert !(new File("/tmp/test").exists());
		}

	@Test
	public void checksumFileIsWritten() throws UrlContentCacheException
		{
		cache.getFile("http://www.davidsoergel.com/testfile");

		assert (new File("/tmp/test/www.davidsoergel.com/testfile.crc32").exists());
		}

	@Test
	public void checksumIsConsistent() throws UrlContentCacheException, MalformedURLException
		{
		String origChecksum = cache.getChecksum("http://www.davidsoergel.com/testfile");
		assert origChecksum.equals("4275079459");
		cache.clear();
		String newChecksum = cache.getChecksum("http://www.davidsoergel.com/testfile");
		assert (newChecksum.equals(origChecksum));
		String finalChecksum = cache.recalculateChecksum("http://www.davidsoergel.com/testfile");
		assert (finalChecksum.equals(origChecksum));
		}

	@Test
	public void fileCanBeRead() throws UrlContentCacheException, IOException
		{
		File f = cache.getFile("http://www.davidsoergel.com/testfile");
		assert f.length() == 81;
		BufferedReader br = new BufferedReader(new FileReader(f));
		assert br.readLine().trim().equals("this is the first line of the test file");
		}

	@Test
	public void remoteFileIsCached() throws UrlContentCacheException
		{
		cache.getFile("http://www.davidsoergel.com/testfile");
		assert (new File("/tmp/test/www.davidsoergel.com/testfile").exists());
		}

	@BeforeTest
	public void setUp() throws UrlContentCacheException
		{
		cache = new UrlContentCacheImpl("/tmp/test");
		}

	@Test
	public void wrongChecksumForcesDownload() throws UrlContentCacheException, IOException
		{
		String origChecksum = cache.getChecksum("http://www.davidsoergel.com/testfile");
		assert origChecksum.equals("4275079459");

		FileWriter fw = new FileWriter("/tmp/test/www.davidsoergel.com/testfile");
		fw.write("bogus");
		fw.close();

		fw = new FileWriter("/tmp/test/www.davidsoergel.com/testfile.crc32");
		fw.write("bogus");
		fw.close();

		String wrongChecksum = cache.getChecksum("http://www.davidsoergel.com/testfile");
		assert wrongChecksum.equals("bogus");

		assert new File("/tmp/test/www.davidsoergel.com/testfile").length() == 5;

		File f = cache.getFile("http://www.davidsoergel.com/testfile", "4275079459");

		assert cache.getChecksum("http://www.davidsoergel.com/testfile").equals("4275079459");// should be reverted

		assert f.length() == 81;
		}

	/*
   @Test
   public void entireDirectoryIsDownloaded() throws UrlContentCacheException
	   {
	   cache.clear();
	   cache.getFile("http://www.davidsoergel.com/testdirectory");

	   assert (new File("/tmp/test/www.davidsoergel.com/testdirectory/testfile2").exists());
	   }

   @Test
   public void wildcardsAreDownloaded() throws UrlContentCacheException
	   {
	   assert false;
	   }
	   */
	}
