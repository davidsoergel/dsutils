/* $Id$ */

/*
 * Copyright (c) 2001-2007 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
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
