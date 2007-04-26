package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;
import sun.net.www.protocol.ftp.FtpURLConnection;

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
 * the file as the modified date on the checksum file (since that's the onl yone available to us in Java)
 */
public class UrlContentCacheImpl implements UrlContentCache
	{
	private static Logger logger = Logger.getLogger(UrlContentCacheImpl.class);

	private File cacheRootDir;
	private String cacheRootDirCanonicalPath;
	private long maxSize = Long.MAX_VALUE;
	private final static int bufferSize = 16384;

	public UrlContentCacheImpl(File cacheRootDir) throws UrlContentCacheException
		{
		try
			{
			this.cacheRootDirCanonicalPath = cacheRootDir.getCanonicalPath();
			}
		catch (IOException e)
			{
			logger.debug(e);
			throw new UrlContentCacheException(e);
			}
		this.cacheRootDir = new File(cacheRootDirCanonicalPath);
		init();
		}

	public UrlContentCacheImpl(String cacheRootDir) throws UrlContentCacheException
		{
		this(new File(cacheRootDir));
		}

	public void setMaxSize(long maxSize)
		{
		this.maxSize = maxSize;
		}

	private long cacheSize = 0;
	private SortedMap<String, File> filesByAccessDate = null;

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
			logger.debug(e);
			throw new UrlContentCacheException(e);
			}
		}


	public void clear()
		{
		FileUtils.deleteDirectory(cacheRootDir);
		}


	public File getFile(String url) throws UrlContentCacheException
		{
		try
			{
			return getFile(new URL(url));
			}
		catch (MalformedURLException e)
			{
			logger.debug(e);
			throw new UrlContentCacheException(e);
			}
		}

	public File getFile(String url, String checksum) throws UrlContentCacheException
		{
		try
			{
			return getFile(new URL(url), checksum);
			}
		catch (MalformedURLException e)
			{
			logger.debug(e);
			throw new UrlContentCacheException(e);
			}
		}

	public File getFile(URL url) throws UrlContentCacheException
		{
		String filename = urlToLocalFilename(url);
		File f = new File(filename);
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
			logger.debug(e);
			throw new UrlContentCacheException(e);
			}
		return f;
		}

	public File getFile(URL url, String checksum) throws UrlContentCacheException
		{
		try
			{
			File f = getFile(url);
			if (!verifyChecksum(f, checksum))
				{
				updateFile(url, true);
				}
			updateLastAccessTime(f);
			return f;
			}
		catch (IOException e)
			{
			logger.debug(e);
			throw new UrlContentCacheException(e);
			}
		}

	private String urlToLocalFilename(URL url)
		{
		StringBuffer sb = new StringBuffer(cacheRootDirCanonicalPath);

		String path = url.getFile();
		path = org.apache.commons.lang.StringUtils.join(path.split("/"), File.separator);

		sb.append(File.separator).append(url.getHost()).append(path);
		return sb.toString();
		}


	private void updateFile(URL url, boolean force) throws UrlContentCacheException
		{
		try
			{
			HttpURLConnection conn = (HttpURLConnection) url
					.openConnection();// Http only supported for now; ClassCastException otherwise
			FtpURLConnection f;
			File localFile = new File(urlToLocalFilename(url));
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
					localFile.delete();
					ensureFreeSpace(conn.getContentLength());
					localFile.getParentFile().mkdirs();
					localFile.createNewFile();
					FileOutputStream os = new FileOutputStream(localFile);
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
			logger.debug(e);
			throw new UrlContentCacheException(e);
			}
		}

	private static String getChecksumString(File file) throws IOException
		{
		long millis = System.currentTimeMillis();
		InputStream in = new FileInputStream(file);
		CRC32 checksum = new CRC32();
		checksum.reset();
		byte[] buffer = new byte[bufferSize];
		int bytesRead;
		while ((bytesRead = in.read(buffer)) >= 0)
			{
			checksum.update(buffer, 0, bytesRead);
			}
		in.close();
		millis = System.currentTimeMillis() - millis;
		logger.debug("Calculated checksum for " + file.getName() + " in " + (millis / 1000L) + " seconds.");
		return "" + checksum.getValue();
		}

	private void updateChecksum(File file) throws IOException
		{
		File c = getChecksumFile(file);
		c.delete();
		c.createNewFile();
		FileWriter w = new FileWriter(c);
		w.write(getChecksumString(file));
		w.close();
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
	private boolean verifyChecksum(File file, String checksum) throws IOException
		{
		String localChecksum = new BufferedReader(new FileReader(getChecksumFile(file))).readLine().trim();
		return localChecksum.equals(checksum);
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
				f.delete();
				getChecksumFile(f).delete();
				filesByAccessDate.remove(key);
				}
			}
		catch (NoSuchElementException e)
			{
			logger.debug(e);
			throw new UrlContentCacheException(
					"Disk too full: cache is empty, and still we can't store " + contentLength + " bytes.");
			}
		catch (IOException e)
			{
			logger.debug(e);
			throw new UrlContentCacheException(e);
			}
		}


	private long getAvailableSpace()
		{
		//return Math.min(cacheRootDir.getUsableSpace(), maxSize - cacheSize);   // works only in J2SE 1.6

		return maxSize - cacheSize;
		}

	private void registerLocalFile(File file) throws IOException
		{
		if (file.isFile())
			{
			filesByAccessDate.put("" + getLastAccessTime(file) + ":" + file.getCanonicalPath(), file);
			cacheSize += file.length();
			}
		File[] files = file.listFiles();
		if (files != null)
			{
			for (File f : files)
				{
				registerLocalFile(f);
				}
			}
		}


	private File getChecksumFile(File f) throws IOException
		{
		return new File(f.getCanonicalPath() + ".crc32");
		}

	private void updateLastAccessTime(File f) throws IOException
		{
		getChecksumFile(f).setLastModified(System.currentTimeMillis());
		}

	private long getLastAccessTime(File file) throws IOException
		{
		return getChecksumFile(file).lastModified();
		}


	public String getChecksum(String s) throws MalformedURLException, UrlContentCacheException
		{
		return getChecksum(new URL(s));
		}

	public String getChecksum(URL s) throws UrlContentCacheException
		{
		try
			{
			return new BufferedReader(new FileReader(getChecksumFile(getFile(s)))).readLine();
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

	public String recalculateChecksum(String s) throws MalformedURLException, UrlContentCacheException
		{
		return recalculateChecksum(new URL(s));
		}

	public String recalculateChecksum(URL s) throws UrlContentCacheException
		{
		try
			{
			updateChecksum(getFile(s));

			return new BufferedReader(new FileReader(getChecksumFile(getFile(s)))).readLine();
			}
		catch (IOException e)
			{
			throw new UrlContentCacheException(e);

			}

		}

	}
