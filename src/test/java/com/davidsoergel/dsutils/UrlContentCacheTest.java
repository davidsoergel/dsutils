/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;


/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class UrlContentCacheTest
	{
	// ------------------------------ FIELDS ------------------------------

	@Nullable
	UrlContentCache cache = null;

	// -------------------------- OTHER METHODS --------------------------

	@Test
	public void accessTimeIsUpdated() throws UrlContentCacheException, InterruptedException, MalformedURLException
		{
		cache.getFile("http://www.davidsoergel.com/testfile");
		long origDate = new File("/tmp/test/www.davidsoergel.com/testfile.crc32").lastModified();
		Thread.sleep(1000);
		cache.getFile("http://www.davidsoergel.com/testfile");
		long newDate = new File("/tmp/test/www.davidsoergel.com/testfile.crc32").lastModified();
		assert newDate > origDate;
		}

	@Test
	public void cacheCanBeCleared() throws UrlContentCacheException, MalformedURLException
		{
		cache.getFile("http://www.davidsoergel.com/testfile");
		assert (new File("/tmp/test").exists());
		cache.clear();
		assert !(new File("/tmp/test").exists());
		}

	@Test
	public void checksumFileIsWritten() throws UrlContentCacheException, MalformedURLException
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
		@NotNull File f = cache.getFile("http://www.davidsoergel.com/testfile");
		assert f.length() == 81;
		@NotNull BufferedReader br = new BufferedReader(new FileReader(f));
		assert br.readLine().trim().equals("this is the first line of the test file");
		}

	@Test
	public void remoteFileIsCached() throws UrlContentCacheException, MalformedURLException
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

		@NotNull FileWriter fw = new FileWriter("/tmp/test/www.davidsoergel.com/testfile");
		fw.write("bogus");
		fw.close();

		fw = new FileWriter("/tmp/test/www.davidsoergel.com/testfile.crc32");
		fw.write("bogus");
		fw.close();

		String wrongChecksum = cache.getChecksum("http://www.davidsoergel.com/testfile");
		assert wrongChecksum.equals("bogus");

		assert new File("/tmp/test/www.davidsoergel.com/testfile").length() == 5;

		@NotNull File f = cache.getFile("http://www.davidsoergel.com/testfile", "4275079459");

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
