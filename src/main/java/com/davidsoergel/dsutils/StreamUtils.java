/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	public static void pipe(@NotNull InputStream in, @NotNull OutputStream out) throws IOException
		{
		@NotNull byte[] b = new byte[512];
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
	@NotNull
	public static StringBuffer getFileContents(@NotNull File pFile) throws FileNotFoundException, IOException
		{
		@NotNull StringBuffer returnBuffer = new StringBuffer();
		@Nullable DataInputStream inStream = null;
		@Nullable String data = null;

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

		   logger.error("Error", fnfe);

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
			logger.error("Error", ioe);

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
