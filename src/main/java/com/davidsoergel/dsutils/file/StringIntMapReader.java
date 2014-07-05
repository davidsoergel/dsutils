/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.file;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class StringIntMapReader
	{
	private static final Logger logger = Logger.getLogger(StringIntMapReader.class);

	@NotNull
	public static Map<String, Integer> read(String filename) throws IOException
		{
		@NotNull Map<String, Integer> result = new HashMap<String, Integer>();

		@NotNull BufferedReader br = new BufferedReader(new FileReader(filename));
		try
			{
			String line;
			int i = 0;
			while ((line = br.readLine()) != null)
				{
				line = line.trim();
				if (line.equals("")) //line.isEmpty())   // JDK 1.5 compatibility
					{
					continue;
					}
				String[] numbers = line.split("[ ,\t]+");
				try
					{
					String key = numbers[0];
					@NotNull Integer value = new Integer(numbers[1]);
					result.put(key, value);
					i++;
					}
				catch (NumberFormatException e)
					{
					//throw new NumberFormatException
					logger.warn("Could not read line " + i + " of " + filename + ": " + line);
					}
				}
			return result;
			}
		finally
			{
			br.close();
			}
		}
	}
