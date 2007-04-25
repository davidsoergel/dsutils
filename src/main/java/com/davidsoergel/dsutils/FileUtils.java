/*
 * dsutils - a collection of generally useful utility classes
 *
 * Copyright (c) 2001-2006 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * USA.
 *
 */

package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author lorax
 * @version 1.0
 */
public class FileUtils
	{
	// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(FileUtils.class);

	// -------------------------- STATIC METHODS --------------------------

	public static boolean move(java.io.File oldFile, java.io.File newFile)
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
				// the copy succeeded, try removing old file
				if (!oldFile.delete())
					{
					logger.error("Can't delete " + oldFile + " after copy");
					}
				}
			}
		return true;
		}

	public static boolean bufferedCopy(java.io.File origFile, java.io.File copyFile, int buffSize)
		{
		byte[] buff = new byte[buffSize];

		try
			{
			FileInputStream fis = new FileInputStream(origFile);
			//file.createNewFile();
			FileOutputStream fos = new FileOutputStream(copyFile);

			for (int bytes = 0; (bytes = fis.read(buff)) > -1;)
				{
				fos.write(buff, 0, bytes);
				}
			}
		catch (IOException e)
			{
			logger.error(e);
			return false;
			}
		return true;
		}


	static public boolean deleteDirectory(File path)
		{
		if (path.exists())
			{
			File[] files = path.listFiles();
			for (File file : files)
				{
				if (file.isDirectory())
					{
					deleteDirectory(file);
					}
				else
					{
					file.delete();
					}
				}
			}
		return (path.delete());
		}

	}
