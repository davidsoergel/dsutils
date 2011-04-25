package com.davidsoergel.dsutils.file;

import com.davidsoergel.dsutils.DSArrayUtils;
import org.jetbrains.annotations.NotNull;

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
		@NotNull List<Integer> theList = new ArrayList<Integer>();

		@NotNull BufferedReader br = new BufferedReader(new FileReader(filename));
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
