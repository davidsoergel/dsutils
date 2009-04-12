package com.davidsoergel.dsutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class StringDoubleMapReader
	{
	public static Map<String, Double> read(String filename) throws IOException
		{
		Map<String, Double> result = new HashMap<String, Double>();

		BufferedReader br = new BufferedReader(new FileReader(filename));
		try
			{
			String line;
			while ((line = br.readLine()) != null)
				{
				line = line.trim();
				if (line.equals("")) //line.isEmpty())   // JDK 1.5 compatibility
					{
					continue;
					}
				String[] numbers = line.split("[ ,\t]+");
				String key = numbers[0];
				Double value = new Double(numbers[1]);
				result.put(key, value);
				}
			return result;
			}
		finally
			{
			br.close();
			}
		}
	}