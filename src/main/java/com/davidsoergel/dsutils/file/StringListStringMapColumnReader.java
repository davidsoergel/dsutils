package com.davidsoergel.dsutils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class StringListStringMapColumnReader
	{
	public static Map<String, List<String>> read(String filename) throws IOException
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

		Map<String, List<String>> result = new HashMap<String, List<String>>();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try
			{
			String header = br.readLine();
			String[] keys = header.split("\t");

			for (String key : keys)
				{
				result.put(key, new ArrayList<String>());
				}

			String line;
			int i = 0;
			while ((line = br.readLine()) != null)
				{
				//line = line.trim();
				if (line.equals("")) //line.isEmpty())   // JDK 1.5 compatibility
					{
					continue;
					}
				String[] numbers = line.split("\t");

				for (int j = 0; j < numbers.length; j++)
					{
					// PERF this sucks but what the hell

					result.get(keys[j]).add(numbers[j]);
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
