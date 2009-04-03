package com.davidsoergel.dsutils;

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
public class StringSetIntMapReader
	{
	public static Map<String, Set<Integer>> read(String filename) throws IOException
		{
		Map<String, Set<Integer>> result = new HashMap<String, Set<Integer>>();

		BufferedReader br = new BufferedReader(new FileReader(filename));
		try
			{
			String line;
			int i = 0;
			while ((line = br.readLine()) != null)
				{
				line = line.trim();
				if (line.isEmpty())
					{
					continue;
					}
				String[] numbers = line.split("\t");
				try
					{
					String key = numbers[0];
					Integer value = new Integer(numbers[1]);

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