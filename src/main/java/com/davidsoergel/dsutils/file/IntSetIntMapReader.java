/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.file;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class IntSetIntMapReader
	{
	@NotNull
	public static Map<Integer, Set<Integer>> read(String filename) throws IOException
		{
		@NotNull Map<Integer, Set<Integer>> result = new HashMap<Integer, Set<Integer>>();

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
					@NotNull Integer key = new Integer(numbers[0]);
					@NotNull Integer value = new Integer(numbers[1]);

					Set<Integer> resultSet = result.get(key);
					if (resultSet == null)
						{
						resultSet = new HashSet<Integer>();
						result.put(key, resultSet);
						}
					resultSet.add(value);
					i++;
					}
				catch (NumberFormatException e)
					{
					throw new NumberFormatException("Could not read line " + i + " of " + filename + ": " + line);
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
