package com.davidsoergel.dsutils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
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
		ClassLoader threadClassLoader = Thread.currentThread().getContextClassLoader();
		//ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();

		//URL res1 = classClassLoader.getResource(filename);
		URL res = threadClassLoader.getResource(filename);
		if (res == null)
			{
			File f = new File(filename);
			if (f.exists())
				{
				res = f.toURI().toURL(); //new URL("file://" + filename);
				}
			else
				{
				throw new FileNotFoundException("File not found: " + filename);
				}
			}
		InputStream is = res.openStream();

		Map<String, Set<Integer>> result = new HashMap<String, Set<Integer>>();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
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
