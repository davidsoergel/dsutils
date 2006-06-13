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

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author lorax
 * @version 1.0
 */
public class StreamUtils
	{
// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(StreamUtils.class);

// -------------------------- STATIC METHODS --------------------------

	/**
	 * Pipe an InputStream to an OutputStream.
	 */
	public static void pipe(InputStream in, OutputStream out) throws IOException
		{
		byte[] b = new byte[512];
		int x = in.read(b, 0, b.length);

		while (x > 0)
			{
			out.write(b, 0, x);

			x = in.read(b, 0, b.length);
			}

		in.close();
		}

	/**
	 * @return the contents of the File in a StringBuffer.
	 */
	public static StringBuffer getFileContents(File pFile) throws FileNotFoundException, IOException
		{
		StringBuffer returnBuffer = new StringBuffer();
		DataInputStream inStream = null;
		String data = null;

		try
			{
			inStream = new DataInputStream(new FileInputStream(pFile));
			data = inStream.readUTF();

			logger.debug("FileIndexReader:  next word: " + data);
			}
		catch (FileNotFoundException fnfe)
			{
			fnfe.printStackTrace();

			throw fnfe;
			}
		catch (EOFException eof)
			{
			data = null;

			logger.debug("Empty file: " + pFile.toString());
			}
		catch (java.io.IOException e)
			{
			e.printStackTrace();

			throw e;
			}

		try
			{
			while (data != null)
				{
				returnBuffer.append(data);

				data = inStream.readUTF();
				}
			}
		catch (java.io.IOException ioe)
			{
			ioe.printStackTrace();

			throw ioe;
			}

		return returnBuffer;
		}
	}
