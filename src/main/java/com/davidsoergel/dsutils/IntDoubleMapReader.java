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
public class IntDoubleMapReader
	{
	public static Map<Integer, Double> read(String filename) throws IOException
		{
		Map<Integer, Double> result = new HashMap<Integer, Double>();

		BufferedReader br = new BufferedReader(new FileReader(filename));
		try
			{
			String line;
			while ((line = br.readLine()) != null)
				{
				String[] numbers = line.split(" +");
				Integer key = new Integer(numbers[0]);
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