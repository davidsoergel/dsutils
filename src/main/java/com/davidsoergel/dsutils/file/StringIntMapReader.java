package com.davidsoergel.dsutils.file;

import org.apache.log4j.Logger;

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

	public static Map<String, Integer> read(String filename) throws IOException
		{
		Map<String, Integer> result = new HashMap<String, Integer>();

		BufferedReader br = new BufferedReader(new FileReader(filename));
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
					Integer value = new Integer(numbers[1]);
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
