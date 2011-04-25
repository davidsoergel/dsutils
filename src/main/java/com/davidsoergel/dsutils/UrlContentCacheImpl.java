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

import com.davidsoergel.dsutils.file.FileUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.zip.CRC32;

/**
 * Cache URL contents in the filesystem.  Maintains checksum files for verification, and stores the last access time for
 * the file as the modified date on the checksum file (since that's the only one available to us in Java)
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class UrlContentCacheImpl implements UrlContentCache
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(UrlContentCacheImpl.class);
	private final static int bufferSize = 16384;

	private File cacheRootDir;
	private String cacheRootDirCanonicalPath;
	private long maxSize = Long.MAX_VALUE;

	private long cacheSize = 0;
	@Nullable
	private SortedMap<String, File> filesByAccessDate = null;


	// --------------------------- CONSTRUCTORS ---------------------------

	public UrlContentCacheImpl(@NotNull File cacheRootDir) throws UrlContentCacheException
		{
		try
			{
			this.cacheRootDirCanonicalPath = cacheRootDir.getCanonicalPath();
			}
		catch (IOException e)
			{
			logger.error("Error", e);
			throw new UrlContentCacheException(e);
			}
		this.cacheRootDir = new File(cacheRootDirCanonicalPath);
		init();
		}

	// we're going to need all this anyway, so don't bother with lazy initializers

	private void init() throws UrlContentCacheException
		{
		if (!cacheRootDir.exists() && !cacheRootDir.mkdirs())
			{
			throw new UrlContentCacheException("Can't create cache directory: " + cacheRootDirCanonicalPath);
			}

		filesByAccessDate = new TreeMap<String, File>();
		try
			{
			registerLocalFile(cacheRootDir);
			}
		catch (IOException e)
			{
			logger.error("Error", e);
			throw new UrlContentCacheException(e);
			}
		}

	private void registerLocalFile(@NotNull File file) throws IOException
		{
		if (file.isFile())
			{
			filesByAccessDate.put("" + getLastAccessTime(file) + ":" + file.getCanonicalPath(), file);
			cacheSize += file.length();
			}
		File[] files = file.listFiles();
		if (files != null)
			{
			for (@NotNull File f : files)
				{
				registerLocalFile(f);
				}
			}
		}

	private long getLastAccessTime(@NotNull File file) throws IOException
		{
		return getChecksumFile(file).lastModified();
		}

	public UrlContentCacheImpl(String cacheRootDir) throws UrlContentCacheException
		{
		this(new File(cacheRootDir));
		}

	// --------------------- GETTER / SETTER METHODS ---------------------

	public void setMaxSize(long maxSize)
		{
		this.maxSize = maxSize;
		}

	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface UrlContentCache ---------------------

	public void clear()
		{
		FileUtils.deleteDirectory(cacheRootDir);
		}

	public String getChecksum(String s) throws MalformedURLException, UrlContentCacheException
		{
		return getChecksum(new URL(s));
		}

	public String getChecksum(@NotNull URL s) throws UrlContentCacheException
		{
		try
			{
			@Nullable BufferedReader br = null;
			try
				{
				br = new BufferedReader(new FileReader(getChecksumFile(getFile(s))));
				return br.readLine();
				}
			finally
				{
				if (br != null)
					{
					br.close();
					}
				}
			}
		catch (IOException e)
			{
			return recalculateChecksum(s);
			}
		catch (UrlContentCacheException e)
			{
			return recalculateChecksum(s);
			}
		}

	@NotNull
	public File getFile(String url) throws UrlContentCacheException, MalformedURLException
		{
		//try
		//	{
		return getFile(new URL(url));
		//	}
		//catch (MalformedURLException e)
		//	{
		//	logger.error("Error", e);
		//	throw new UrlContentCacheException(e);
		//	}
		}

	@NotNull
	public File getFile(@NotNull URL url) throws UrlContentCacheException
		{
		String filename = urlToLocalFilename(url);
		@NotNull File f = new File(filename);
		if (!f.exists())
			{
			updateFile(url, false);
			f = new File(filename);
			}
		try
			{
			updateLastAccessTime(f);
			}
		catch (IOException e)
			{
			logger.error("Error", e);
			throw new UrlContentCacheException(e);
			}
		return f;
		}

	@NotNull
	public File getFile(@NotNull URL url, String checksum) throws UrlContentCacheException
		{
		try
			{
			@NotNull File f = getFile(url);
			if (!verifyChecksum(f, checksum))
				{
				updateFile(url, true);
				}
			updateLastAccessTime(f);
			return f;
			}
		catch (IOException e)
			{
			logger.error("Error", e);
			throw new UrlContentCacheException(e);
			}
		}

	@NotNull
	public File getFile(String url, String checksum) throws UrlContentCacheException, MalformedURLException
		{
		//try
		//	{
		return getFile(new URL(url), checksum);
		//	}
		//catch (MalformedURLException e)
		//	{
		//	logger.error("Error", e);
		//	throw new UrlContentCacheException(e);
		//	}
		}

	public String recalculateChecksum(String s) throws MalformedURLException, UrlContentCacheException
		{
		return recalculateChecksum(new URL(s));
		}

	public String recalculateChecksum(@NotNull URL s) throws UrlContentCacheException
		{
		try
			{
			updateChecksum(getFile(s));
			@Nullable BufferedReader br = null;
			try
				{
				br = new BufferedReader(new FileReader(getChecksumFile(getFile(s))));
				return br.readLine();
				}
			finally
				{
				if (br != null)
					{
					br.close();
					}
				}
			}
		catch (IOException e)
			{
			throw new UrlContentCacheException(e);
			}
		}

	// -------------------------- OTHER METHODS --------------------------

	private void updateFile(@NotNull URL url, boolean force) throws UrlContentCacheException
		{
		try
			{
			@NotNull HttpURLConnection conn = (HttpURLConnection) url
					.openConnection();// Http only supported for now; ClassCastException otherwise
			//	FtpURLConnection f;
			@NotNull File localFile = new File(urlToLocalFilename(url));
			if (localFile.exists() && !force)
				{
				conn.setIfModifiedSince(localFile.lastModified());// UNIX date as long?
				}
			conn.connect();
			switch (conn.getResponseCode())
				{
				case HttpURLConnection.HTTP_NOT_MODIFIED:// only possible if force=false
					return;// all done
				case HttpURLConnection.HTTP_OK:
					if (!localFile.delete())
						{
						//throw new UrlContentCacheException("Couldn't delete " + localFile);
						// no problem, maybe the file didn't exist yet
						}
					ensureFreeSpace(conn.getContentLength());
					if (!localFile.getParentFile().mkdirs())
						{
						// probably OK: the dirs already exist
						//throw new UrlContentCacheException("Couldn't create " + localFile.getParentFile());
						}
					if (!localFile.createNewFile())
						{
						throw new UrlContentCacheException("Couldn't create " + localFile);
						}
					@NotNull FileOutputStream os = new FileOutputStream(localFile);
					StreamUtils.pipe(conn.getInputStream(), os);
					os.close();
					updateChecksum(localFile);
					break;
				default:
					// even if force=true, we still leave the old file in place, no harm done
					throw new IOException(
							"Can't handle http response: " + conn.getResponseCode() + " " + conn.getResponseMessage());
				}
			}
		catch (IOException e)
			{
			logger.error("Error", e);
			throw new UrlContentCacheException(e);
			}
		}

	private String urlToLocalFilename(@NotNull URL url)
		{
		@NotNull StringBuffer sb = new StringBuffer(cacheRootDirCanonicalPath);

		String path = url.getFile();
		path = org.apache.commons.lang.StringUtils.join(path.split("/"), File.separator);

		sb.append(File.separator).append(url.getHost()).append(path);
		return sb.toString();
		}

	private void ensureFreeSpace(int contentLength) throws UrlContentCacheException
		{
		try
			{
			while (getAvailableSpace() < contentLength)
				{
				// remove oldest files first.  The dates are alpha sorted but that's OK.
				String key = filesByAccessDate.firstKey();
				File f = filesByAccessDate.get(key);
				cacheSize -= f.length();
				if (!f.delete())
					{
					throw new UrlContentCacheException("Couldn't delete: " + f);
					}
				if (!getChecksumFile(f).delete())
					{
					throw new UrlContentCacheException("Couldn't delete: " + getChecksumFile(f));
					}
				filesByAccessDate.remove(key);
				}
			}
		catch (NoSuchElementException e)
			{
			logger.error("Error", e);
			throw new UrlContentCacheException(
					"Disk too full: cache is empty, and still we can't store " + contentLength + " bytes.");
			}
		catch (IOException e)
			{
			logger.error("Error", e);
			throw new UrlContentCacheException(e);
			}
		}

	private long getAvailableSpace()
		{
		//return Math.min(cacheRootDir.getUsableSpace(), maxSize - cacheSize);   // works only in J2SE 1.6

		return maxSize - cacheSize;
		}

	private void updateChecksum(@NotNull File file) throws IOException
		{
		@NotNull File c = getChecksumFile(file);
		if (!c.delete())
			{
			// no problem
			}
		if (!c.createNewFile())
			{
			throw new IOException("Couldn't create " + c);
			}
		@NotNull FileWriter w = new FileWriter(c);
		w.write(getChecksumString(file));
		w.close();
		}

	@NotNull
	private File getChecksumFile(@NotNull File f) throws IOException
		{
		return new File(f.getCanonicalPath() + ".crc32");
		}

	@NotNull
	private static String getChecksumString(@NotNull File file) throws IOException
		{
		long millis = System.currentTimeMillis();
		@NotNull InputStream in = new FileInputStream(file);
		@NotNull CRC32 checksum = new CRC32();
		checksum.reset();
		@NotNull byte[] buffer = new byte[bufferSize];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) >= 0)
			{
			checksum.update(buffer, 0, bytesRead);
			}
		in.close();
		millis = System.currentTimeMillis() - millis;
		logger.trace("Calculated checksum for " + file.getName() + " in " + (millis / 1000L) + " seconds.");
		return "" + checksum.getValue();
		}

	private void updateLastAccessTime(@NotNull File f) throws IOException
		{
		if (!getChecksumFile(f).setLastModified(System.currentTimeMillis()))
			{
			throw new IOException("Failed to update access time: " + f);
			}
		}

	/**
	 * compare the requested checksum against the locally stored checksum, without recalculating it.  To be really sure,
	 * run updateChecksum first.
	 *
	 * @param file
	 * @param checksum
	 * @return
	 * @throws IOException
	 */
	private boolean verifyChecksum(@NotNull File file, String checksum) throws IOException
		{
		@Nullable BufferedReader br = null;
		try
			{
			br = new BufferedReader(new FileReader(getChecksumFile(file)));
			String s = br.readLine();
			if (s == null)
				{
				throw new IOException("Couldn't read from " + file);
				}
			String localChecksum = s.trim();
			return localChecksum.equals(checksum);
			}
		finally
			{
			if (br != null)
				{
				br.close();
				}
			}
		}
	}
