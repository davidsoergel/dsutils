/*
 * Copyright (c) 2001-2007 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the names of any contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/* $Id$ */

package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA. User: lorax Date: Apr 29, 2004 Time: 6:15:13 PM To change this template use File | Settings
 * | File Templates.
 */
public class ArrayUtils extends org.apache.commons.lang.ArrayUtils
	{
	// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(ArrayUtils.class);


	// -------------------------- STATIC METHODS --------------------------

	public static boolean equalWithinFPError(double[] a, double[] b)
		{
		if (a.length != b.length)
			{
			throw new IndexOutOfBoundsException("Can't compare arrays of different sizes");
			}
		for (int i = 0; i < a.length; i++)
			{
			if (!MathUtils.equalWithinFPError(a[i], b[i]))
				{
				return false;
				}
			}
		return true;
		}

	public static boolean equalWithinFPError(Double[] a, Double[] b)
		{
		if (a.length != b.length)
			{
			throw new IndexOutOfBoundsException("Can't compare arrays of different sizes");
			}
		for (int i = 0; i < a.length; i++)
			{
			if (!MathUtils.equalWithinFPError(a[i], b[i]))
				{
				return false;
				}
			}
		return true;
		}

	public static double[] minus(double[] a, double[] b)
		{
		double[] result = a.clone();// does this work??
		decrementBy(result, b);
		return result;
		}

	public static void decrementBy(double[] a, double[] b)
		{
		if (a.length != b.length)
			{
			throw new IndexOutOfBoundsException("Can't add arrays of different sizes");
			}
		for (int i = 0; i < a.length; i++)
			{
			//logger.debug("Adding cells: " + i + ", " + j);
			a[i] -= b[i];
			}
		}

	@Deprecated
	public static double[][] add(double[][] a, double[][] b)
		{
		return plus(a, b);
		}

	public static double[][] plus(double[][] a, double[][] b)
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

	public static double[] plus(double[] a, double[] b)
		{
		double[] result = a.clone();// does this work??
		incrementBy(result, b);
		return result;
		}

	public static void incrementBy(double[] a, double[] b)
		{
		if (a.length != b.length)
			{
			throw new IndexOutOfBoundsException("Can't add arrays of different sizes");
			}
		for (int i = 0; i < a.length; i++)
			{
			//logger.debug("Adding cells: " + i + ", " + j);
			a[i] += b[i];
			}
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

	public static int sum(Integer[] a)
		{
		int result = 0;
		for (int i = 0; i < a.length; i++)
			{
			result += a[i];
			}
		return result;
		}

	public static double sum(double[] a)
		{
		double result = 0;
		for (int i = 0; i < a.length; i++)
			{
			result += a[i];
			}
		return result;
		}

	public static double sum(Collection<Double> a)
		{
		int result = 0;
		for (double i : a)
			{
			result += i;
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

	public static double product(double[] a)
		{
		double result = 0;
		for (int i = 0; i < a.length; i++)
			{
			result *= a[i];
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

	public static double max(double[] x)
		{
		double result = Double.MIN_VALUE;
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

	public static int min(int[] x)
		{
		int result = Integer.MAX_VALUE;
		for (int col = 0; col < x.length; col++)
			{
			result = Math.min(result, x[col]);
			}
		return result;
		}

	public static double min(double[] x)
		{
		double result = Double.MAX_VALUE;
		for (int col = 0; col < x.length; col++)
			{
			result = Math.min(result, x[col]);
			}
		return result;
		}

	public static int argmin(double[] x)
		{
		int posmax = 0;
		for (int col = 0; col < x.length; col++)
			{
			if (x[col] < x[posmax])
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
			if (a[i] == o)
				{
				return true;
				}
			}
		return false;
		}

	public static double[] castToDouble(int[] p)
		{
		double[] result = new double[p.length];
		for (int i = 0; i < p.length; i++)
			{
			result[i] = p[i];
			}
		return result;
		}

	/**
	 * returns a new byte[] containing the first i characters of s.
	 *
	 * @param s
	 * @param i
	 * @return
	 */
	public static byte[] prefix(byte[] s, int i)
		{
		byte[] result = new byte[i];
		System.arraycopy(s, 0, result, 0, i);
		return result;
		}

	/**
	 * returns a new byte[] containing all characters of s starting from startPos
	 *
	 * @param s
	 * @param startPos
	 * @return
	 * @see #suffixOfLength
	 */
	public static byte[] suffix(byte[] s, int startpos)
		{
		byte[] result = new byte[s.length - startpos];
		System.arraycopy(s, startpos, result, 0, s.length - startpos);
		return result;
		}

	public static byte[] suffixOfLength(byte[] s, int length)
		{
		if (length > s.length)
			{
			throw new IndexOutOfBoundsException("Requested suffix is longer than the array");
			}
		byte[] result = new byte[length];
		System.arraycopy(s, s.length - length, result, 0, length);
		return result;
		}

	public static byte[] prepend(byte b, byte[] s)
		{
		byte[] result = new byte[s.length + 1];
		result[0] = b;
		System.arraycopy(s, 0, result, 1, s.length);
		return result;
		}

	public static byte[] append(byte[] s, byte b)
		{
		byte[] result = new byte[s.length + 1];
		System.arraycopy(s, 0, result, 0, s.length);
		result[s.length] = b;
		return result;
		}

	public static double mean(double[] counts)
		{
		double sum = 0;
		for (int i = 0; i < counts.length; i++)
			{
			sum += counts[i];
			}
		return sum / counts.length;
		}

	/**
	 * Since we will often have computed the mean already when calling this, we just pass it in instead of recomputing it
	 *
	 * @param x
	 * @param mean
	 * @return
	 */
	public static double stddev(double[] x, double mean)
		{
		double sumsq = 0;
		for (int i = 0; i < x.length; i++)
			{
			double dev = x[i] - mean;
			sumsq += dev * dev;
			}
		return Math.sqrt(sumsq / x.length);
		}

	public static double[] times(double[] data, double scalar)
		{
		double[] result = data.clone();// does this work??
		multiplyBy(result, scalar);
		return result;
		}

	public static void multiplyBy(double[] a, double scalar)
		{
		for (int i = 0; i < a.length; i++)
			{
			//logger.debug("Adding cells: " + i + ", " + j);
			a[i] *= scalar;
			}
		}

	public static double norm(int[] x)
		{
		int sumsq = 0;
		for (int i = 0; i < x.length; i++)
			{
			sumsq += x[i] * x[i];
			}
		return Math.sqrt(sumsq / x.length);
		}

	public static double norm(double[] x)
		{
		double sumsq = 0;
		for (int i = 0; i < x.length; i++)
			{
			sumsq += x[i] * x[i];
			}
		return Math.sqrt(sumsq / x.length);
		}
	}
