package com.davidsoergel.dsutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class IntArrayReader
	{
	public static int[] read(String filename) throws IOException
		{
		List<Integer> theList = new ArrayList<Integer>();

		BufferedReader br = new BufferedReader(new FileReader(filename));
		try
			{
			String line;
			while ((line = br.readLine()) != null)
				{
				theList.add(Integer.parseInt(line));
				/*
			String[] numbers = line.split(" +");
			for (int j = 0; j < numbers.length; j++)
				{
				int num = Integer.parseInt(numbers[j]);
				System.out.println("Number=" + num);
				}*/
				}
			return DSArrayUtils.toPrimitive(theList.toArray(new Integer[]{}));
			}
		finally
			{
			br.close();
			}
		}
	}
