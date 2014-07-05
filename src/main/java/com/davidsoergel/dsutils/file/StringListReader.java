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
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id: IntArrayReader.java 443 2009-08-24 22:39:21Z soergel $
 */
public class StringListReader
	{
	@NotNull
	public static List<String> read(String filename) throws IOException
		{
		@NotNull List<String> theList = new ArrayList<String>();

		@NotNull BufferedReader br = new BufferedReader(new FileReader(filename));
		try
			{
			String line;
			while ((line = br.readLine()) != null)
				{
				theList.add(line);
				/*
			String[] numbers = line.split(" +");
			for (int j = 0; j < numbers.length; j++)
				{
				int num = Integer.parseInt(numbers[j]);
				System.out.println("Number=" + num);
				}*/
				}
			return theList;
			}
		finally
			{
			br.close();
			}
		}
	}
