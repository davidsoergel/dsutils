/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.file;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @version $Id$
 */
public class FileUtils
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(FileUtils.class);


	// -------------------------- STATIC METHODS --------------------------

	public static boolean move(@NotNull java.io.File oldFile, @NotNull java.io.File newFile)
		{
		// first try simple rename
		if (!oldFile.renameTo(newFile))
			{
			// perhaps failed while trying to rename across filesystems?
			// try buffered copy
			int bufsize = 1024;

			if (!bufferedCopy(oldFile, newFile, bufsize))
				{
				return false;
				}
			else
				{
				deleteRecursive(oldFile);
				// the copy succeeded, try removing old file

				}
			}
		return true;
		}

	private static void deleteRecursive(@NotNull final File oldFile)
		{
		if (oldFile.isDirectory())
			{
			for (@NotNull File file : oldFile.listFiles())
				{
				deleteRecursive(file);
				}

			if (!oldFile.delete())
				{
				logger.error("Can't delete " + oldFile + " after copy");
				}
			}
		else
			{
			if (!oldFile.delete())
				{
				logger.error("Can't delete " + oldFile + " after copy");
				}
			}
		}

	// ** should replace boolean return values with exceptions as needed

	public static boolean bufferedCopy(@NotNull java.io.File origFile, @NotNull java.io.File copyFile, int buffSize)
		{
		if (origFile.isDirectory())
			{
			copyFile.mkdirs();

			String[] children = origFile.list();
			for (int i = 0; i < children.length; i++)
				{
				if (!bufferedCopy(new File(origFile, children[i]), new File(copyFile, children[i]), buffSize))
					{
					return false;
					}
				}
			}
		else
			{

			@NotNull byte[] buff = new byte[buffSize];

			try
				{
				@NotNull FileInputStream fis = new FileInputStream(origFile);
				//file.createNewFile();
				@NotNull FileOutputStream fos = new FileOutputStream(copyFile);

				try
					{
					for (int bytes = 0; (bytes = fis.read(buff)) > -1;)
						{
						fos.write(buff, 0, bytes);
						}
					}
				finally
					{
					fis.close();
					fos.close();
					}
				}
			catch (IOException e)
				{
				logger.error("Error", e);
				return false;
				}
			}

		return true;
		}

	static public boolean deleteDirectory(@NotNull File path)
		{
		if (path.exists())
			{
			File[] files = path.listFiles();
			for (@NotNull File file : files)
				{
				if (file.isDirectory())
					{
					deleteDirectory(file);
					}
				else
					{
					if (!file.delete())
						{
						logger.error(
								"Unable to delete file: " + file.getAbsolutePath() + " while trying to delete " + path
										.getAbsolutePath());
						}
					}
				}
			}
		return path.delete();
		}

	/*	public static Set<File> getFilesWithNames(String[] filenames)
	   {

	   }*/

	@NotNull
	public static String makeAbsolute(String root, @NotNull String f)
		{
		if (!f.startsWith(File.separator))
			{
			f = root + File.separator + f;
			}
		return f;
		}

	@NotNull
	public static List<File> getFilesWithNames(String root, @Nullable String[] filenames)
		{
		/*

		 try
			{
			theActualDistanceMeasure = PluginManager
					.getNewInstanceByName(DistanceMeasure.class, distanceMeasure);
			}
		catch (PluginException e)
			{
			throw new PropsException(e);
			}

		if (theActualDistanceMeasure == null)
			{
			throw new PropsException("Can't find distancemeasure measure: " + distanceMeasure);
			}
*/

		//	outputDirectory = new File(outputDirectoryName + File.separator + getRunId());
		//	logger.info("Writing outputs to " + outputDirectoryName);
		//	logger.info("Found directory: " + outputDirectory);

		@NotNull List<File> files = new ArrayList<File>();
		// List<File> inputFilesList = new ArrayList<File>();
		if (filenames != null)
			{
			try
				{
				for (@NotNull String f : filenames)
					{
					if (!f.startsWith(File.separator))
						{
						f = root + File.separator + f;
						}

					if (f.endsWith("*"))
						{
						//logger.info("Separator: " + File.separator);
						String dirname = f.substring(0, f.lastIndexOf(File.separator));
						//logger.info("Wildcard directory: " + dirname);

						@NotNull File dir = new File(dirname);

						//logger.info("is directory: " + dir.isDirectory());
						//logger.info("Canonical path = " + dir.getCanonicalPath());

						// TODO full-blown pattern matching
						final String prefix = f.substring(f.lastIndexOf(File.separator) + 1, f.length() - 1);
						//logger.info("Looking for files with prefix '" + prefix + "' in " + dir);
						//logger.info("all files: " + DSStringUtils.join(dir.list(), ", "));

						if (dir.list() == null || dir.listFiles() == null)
							{
							logger.error("Directory list is empty: " + dir.getCanonicalPath());
							logger.debug("is directory: " + dir.isDirectory());
							}

						for (File r : dir.listFiles(new FilenameFilter()
						{
						public boolean accept(File file, @NotNull String string)
							{
							return string.startsWith(prefix);
							}
						}))
							{
							//		logger.info("Adding " + r + " to file list");
							files.add(r);
							//	inputFilesList.add(r);
							}
						}
					else
						{
						files.add(new File(f));
						//	inputFilesList.add(new File(f));
						}
					}
				}
			catch (Exception e)
				{
				logger.error("Error", e);
				throw new RuntimeException("trouble listing files.", e);
				}
			}
		return files;
		}

	@NotNull
	public static File getDirectory(String name) throws IOException
		{
		@NotNull File dir = new File(name);
		if (!dir.exists())
			{
			if (!dir.mkdirs())
				{
				throw new IOException("Failed to create directory: " + name);
				}
			}
		return dir;
		}
	}
