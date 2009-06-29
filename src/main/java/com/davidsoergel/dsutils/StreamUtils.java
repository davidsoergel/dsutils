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
 * @version $Id$
 */
public class StreamUtils
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(StreamUtils.class);


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
			try
				{
				inStream = new DataInputStream(new FileInputStream(pFile));
				data = inStream.readUTF();

				logger.trace("FileIndexReader:  next word: " + data);
				}
			/*	catch (FileNotFoundException fnfe)
			   {

		   logger.error(fnfe);

			   throw fnfe;
			   }*/
			catch (EOFException eof)
				{
				data = null;

				logger.warn("Empty file: " + pFile.toString());
				}
			/*catch (java.io.IOException e)
				{
			logger.error("Error", e);

				throw e;
				}*/

			//try
			//	{
			while (data != null)
				{
				returnBuffer.append(data);

				data = inStream.readUTF();
				}
			//	}
			/*catch (java.io.IOException ioe)
				{
			logger.error(ioe);

				throw ioe;
				}*/

			return returnBuffer;
			}
		finally
			{
			if (inStream != null)
				{
				inStream.close();
				}
			}
		}
	}
