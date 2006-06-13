/*
 * dsutils - a collection of generally useful utility classes
 *
 * Copyright (c) 2001-2006 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * USA.
 *
 */

package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: lorax
 * Date: Apr 29, 2004
 * Time: 6:15:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArrayUtils
	{
// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(ArrayUtils.class);

// -------------------------- STATIC METHODS --------------------------

	public static double[][] add(double[][] a, double[][] b)
		{
		if (a.length != b.length)
			{
			throw new Error("Can't add arrays of different sizes");
			}
		double[][] result = new double[a.length][];
		for (int i = 0; i < a.length; i++)
			{
			result[i] = new double[a[i].length];
			if (a[i].length != b[i].length)
				{
				throw new Error("Can't add arrays of different sizes");
				}
			for (int j = 0; j < a[i].length; j++)
				{
				//logger.debug("Adding cells: " + i + ", " + j);
				result[i][j] = a[i][j] + b[i][j];
				}
			}
		return result;
		}

	public static double[][] deepcopy(double[][] copyFrom)
		{
		return deepcopy(copyFrom, 0, 0);
		}

	public static int[] deepcopy(int[] copyFrom)
		{
		int[] to = new int[copyFrom.length];


		for (int j = 0; j < copyFrom.length; j++)
			{
			to[j] = copyFrom[j];
			}
		return to;
		}

	public static int[][] deepcopy(int[][] copyFrom)
		{
		return deepcopy(copyFrom, 0, 0);
		}

	public static char[][] deepcopy(char[][] copyFrom)
		{
		return deepcopy(copyFrom, 0, ' ');
		}

	public static double[][] deepcopy(double[][] copyFrom, int newcolumns, double newval)
		{
		double[][] to = new double[copyFrom.length][];
		for (int i = 0; i < copyFrom.length; i++)
			{
			to[i] = new double[copyFrom[i].length + newcolumns];


			if (newcolumns < 0)
				{
				for (int j = 0; j < (copyFrom[i].length + newcolumns); j++)
					{
					to[i][j] = copyFrom[i][j];
					}
				}
			else
				{
				for (int j = 0; j < copyFrom[i].length; j++)
					{
					to[i][j] = copyFrom[i][j];
					}
				for (int j = copyFrom[i].length; j < copyFrom[i].length + newcolumns; j++)
					{
					to[i][j] = newval;
					}
				}
			}
		return to;
		}

	public static int[][] deepcopy(int[][] copyFrom, int newcolumns, int newval)
		{
		int[][] to = new int[copyFrom.length][];
		for (int i = 0; i < copyFrom.length; i++)
			{
			to[i] = new int[copyFrom[i].length + newcolumns];


			if (newcolumns < 0)
				{
				for (int j = 0; j < (copyFrom[i].length + newcolumns); j++)
					{
					to[i][j] = copyFrom[i][j];
					}
				}
			else
				{
				for (int j = 0; j < copyFrom[i].length; j++)
					{
					to[i][j] = copyFrom[i][j];
					}
				for (int j = copyFrom[i].length; j < copyFrom[i].length + newcolumns; j++)
					{
					to[i][j] = newval;
					}
				}
			}
		return to;
		}

	public static char[][] deepcopy(char[][] copyFrom, int newcolumns, char newval)
		{
		char[][] to = new char[copyFrom.length][];
		for (int i = 0; i < copyFrom.length; i++)
			{
			to[i] = new char[copyFrom[i].length + newcolumns];

			if (newcolumns < 0)
				{
				for (int j = 0; j < (copyFrom[i].length + newcolumns); j++)
					{
					to[i][j] = copyFrom[i][j];
					}
				}
			else
				{
				for (int j = 0; j < copyFrom[i].length; j++)
					{
					to[i][j] = copyFrom[i][j];
					}
				for (int j = copyFrom[i].length; j < copyFrom[i].length + newcolumns; j++)
					{
					to[i][j] = newval;
					}
				}
			}
		return to;
		}

	public static int sum(int[] a)
		{
		int result = 0;
		for (int i = 0; i < a.length; i++)
			{
			result += a[i];
			}
		return result;
		}

	public static double sum(double[][] a)
		{
		return sumFirstNColumns(a, a[0].length);
		}

	public static double sumFirstNColumns(double[][] a, int cols)
		{
		double result = 0.0;
		for (int i = 0; i < a.length; i++)
			{
			for (int j = 0; j < cols; j++)
				{
				//if(a[i][j] == Double.NEGATIVE_INFINITY) { return Double.NEGATIVE_INFINITY; }
				//if(a[i][j] == Double.POSITIVE_INFINITY) { return Double.POSITIVE_INFINITY; }
				result += a[i][j];
				}
			}
		return result;
		}

	public static int max(int[] x)
		{
		int result = Integer.MIN_VALUE;
		for (int col = 0; col < x.length; col++)
			{
			result = Math.max(result, x[col]);
			}
		return result;
		}

	public static int argmax(double[] x)
		{
		int posmax = 0;
		for (int col = 0; col < x.length; col++)
			{
			if (x[col] > x[posmax])
				{
				posmax = col;
				}
			}
		return posmax;
		}

	/**
	 * @param x
	 * @param value
	 */
	public static int[] positions(int[] x, int value)
		{
		int result[] = new int[x.length];
		int count = 0;
		for (int col = 0; col < x.length; col++)
			{
			if (x[col] == value)
				{
				result[count] = col;
				count++;
				}
			}
		// we allocated an array of size x.length.  We could now reallocate one of size count,
		// but to avoid doing that we just put the count in the last element of the array.
		// this hack assumes that the value is not found in _every_ position of x.
		result[result.length - 1] = count;
		return result;
		}

	public static double[] grow(double[] a, int x)
		{
		double[] result = new double[a.length + x];
		for (int i = 0; i < a.length; i++)
			{
			result[i] = a[i];
			}
		return result;
		}

	public static String asString(char[] x, String separator)
		{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < x.length; i++)
			{
			char i1 = x[i];
			sb.append(i1);
			if (i != (x.length - 1))
				{
				sb.append(separator);
				}
			}
		return sb.toString();
		}

	public static String asString(int[] x, String separator)
		{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < x.length; i++)
			{
			int i1 = x[i];
			sb.append(i1);
			if (i != (x.length - 1))
				{
				sb.append(separator);
				}
			}
		return sb.toString();
		}

	public static boolean contains(Object[] a, Object o)
		{
		for (int i = 0; i < a.length; i++)
			{
			if(a[i] == o) { return true; }
			}
		return false;
		}
	}
