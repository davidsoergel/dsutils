/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import com.davidsoergel.dsutils.math.MathUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class DSArrayUtils extends org.apache.commons.lang.ArrayUtils
	{

	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(DSArrayUtils.class);


	// -------------------------- STATIC METHODS --------------------------

	public static boolean equalWithinFPError(@NotNull double[] a, @NotNull double[] b)
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

	public static boolean equalWithinFPError(@NotNull Double[] a, @NotNull Double[] b)
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

	public static double[] minus(@NotNull double[] a, @NotNull double[] b)
		{
		double[] result = a.clone();// does this work??
		decrementBy(result, b);
		return result;
		}

	public static void decrementBy(@NotNull double[] a, @NotNull double[] b)
		{
		if (a.length != b.length)
			{
			throw new IndexOutOfBoundsException("Can't add arrays of different sizes");
			}
		for (int i = 0; i < a.length; i++)
			{
			//logger.trace("Adding cells: " + i + ", " + j);
			a[i] -= b[i];
			}
		}


	public static void decrementByWeighted(@NotNull double[] a, @NotNull double[] b, double weight)
		{
		if (a.length != b.length)
			{
			throw new IndexOutOfBoundsException("Can't add arrays of different sizes");
			}
		for (int i = 0; i < a.length; i++)
			{
			//logger.trace("Adding cells: " + i + ", " + j);
			a[i] -= b[i] * weight;
			}
		}

	/*	@Deprecated
	 public static double[][] add(double[][] a, double[][] b)
		 {
		 return plus(a, b);
		 }
 */

	@NotNull
	public static double[][] plus(@NotNull double[][] a, @NotNull double[][] b)
		{
		if (a.length != b.length)
			{
			throw new ArrayIndexOutOfBoundsException("Can't add arrays of different sizes");
			}
		@NotNull double[][] result = new double[a.length][];
		for (int i = 0; i < a.length; i++)
			{
			result[i] = new double[a[i].length];
			if (a[i].length != b[i].length)
				{
				throw new ArrayIndexOutOfBoundsException("Can't add arrays of different sizes");
				}
			for (int j = 0; j < a[i].length; j++)
				{
				//logger.trace("Adding cells: " + i + ", " + j);
				result[i][j] = a[i][j] + b[i][j];
				}
			}
		return result;
		}

	public static double[] plus(@NotNull double[] a, @NotNull double[] b)
		{
		double[] result = a.clone();// does this work??
		incrementBy(result, b);
		return result;
		}

	public static void incrementBy(@NotNull double[] a, @NotNull double[] b)
		{
		if (a.length != b.length)
			{
			throw new IndexOutOfBoundsException("Can't add arrays of different sizes");
			}
		for (int i = 0; i < a.length; i++)
			{
			//logger.trace("Adding cells: " + i + ", " + j);
			a[i] += b[i];
			}
		}


	public static void incrementByWeighted(@NotNull double[] a, @NotNull double[] b, double weight)
		{
		if (a.length != b.length)
			{
			throw new IndexOutOfBoundsException("Can't add arrays of different sizes");
			}
		for (int i = 0; i < a.length; i++)
			{
			//logger.trace("Adding cells: " + i + ", " + j);
			a[i] += b[i] * weight;
			}
		}

	@Nullable
	public static double[][] deepcopy(double[][] copyFrom)
		{
		return deepcopy(copyFrom, 0, 0);
		}

	@NotNull
	public static int[] deepcopy(@NotNull int[] copyFrom)
		{
		@NotNull int[] to = new int[copyFrom.length];


		for (int j = 0; j < copyFrom.length; j++)
			{
			to[j] = copyFrom[j];
			}
		return to;
		}

	@NotNull
	public static int[][] deepcopy(@NotNull int[][] copyFrom)
		{
		return deepcopy(copyFrom, 0, 0);
		}

	@NotNull
	public static char[][] deepcopy(@NotNull char[][] copyFrom)
		{
		return deepcopy(copyFrom, 0, ' ');
		}

	@NotNull
	public static byte[][] deepcopy(@NotNull byte[][] copyFrom)
		{
		return deepcopy(copyFrom, 0, (byte) ' ');
		}


	@Nullable
	public static double[][] deepcopy(@Nullable double[][] copyFrom, int newcolumns, double newval)
		{
		if (copyFrom == null)
			{
			return null;
			}
		@NotNull double[][] to = new double[copyFrom.length][];
		for (int i = 0; i < copyFrom.length; i++)
			{
			if (copyFrom[i] != null)
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
			}
		return to;
		}

	@NotNull
	public static int[][] deepcopy(@NotNull int[][] copyFrom, int newcolumns, int newval)
		{
		@NotNull int[][] to = new int[copyFrom.length][];
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

	@NotNull
	public static char[][] deepcopy(@NotNull char[][] copyFrom, int newcolumns, char newval)
		{
		@NotNull char[][] to = new char[copyFrom.length][];
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

	@NotNull
	public static byte[][] deepcopy(@NotNull byte[][] copyFrom, int newcolumns, byte newval)
		{
		@NotNull byte[][] to = new byte[copyFrom.length][];
		for (int i = 0; i < copyFrom.length; i++)
			{
			to[i] = new byte[copyFrom[i].length + newcolumns];

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

	public static int sum(@NotNull int[] a)
		{
		int result = 0;
		for (int i = 0; i < a.length; i++)
			{
			result += a[i];
			}
		return result;
		}

	public static int sum(@NotNull Integer[] a)
		{
		int result = 0;
		for (int i = 0; i < a.length; i++)
			{
			result += a[i];
			}
		return result;
		}

	public static double sum(@NotNull double[] a)
		{
		double result = 0;
		for (int i = 0; i < a.length; i++)
			{
			result += a[i];
			}
		return result;
		}

	public static double sumOfSquares(@NotNull double[] a)
		{
		double result = 0;
		for (int i = 0; i < a.length; i++)
			{
			result += a[i] * a[i];
			}
		return result;
		}

	public static double sum(@NotNull double[][] a)
		{
		return sumFirstNColumns(a, a[0].length);
		}

	public static double sumFirstNColumns(@NotNull double[][] a, int cols)
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

	public static double product(@NotNull double[] a)
		{
		double result = 0;
		for (int i = 0; i < a.length; i++)
			{
			result *= a[i];
			}
		return result;
		}

	public static int max(@NotNull int[] x)
		{
		int result = Integer.MIN_VALUE;
		for (int col = 0; col < x.length; col++)
			{
			result = Math.max(result, x[col]);
			}
		return result;
		}

	public static double max(@NotNull double[] x)
		{
		double result = Double.NEGATIVE_INFINITY;
		for (int col = 0; col < x.length; col++)
			{
			result = Math.max(result, x[col]);
			}
		return result;
		}

	public static Double max(@NotNull Double[] x)
		{
		double result = Double.NEGATIVE_INFINITY;
		for (int col = 0; col < x.length; col++)
			{
			result = Math.max(result, x[col]);
			}
		return result;
		}

	public static int argmax(@NotNull double[] x)
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

	public static int min(@NotNull int[] x)
		{
		int result = Integer.MAX_VALUE;
		for (int col = 0; col < x.length; col++)
			{
			result = Math.min(result, x[col]);
			}
		return result;
		}

	public static double min(@NotNull double[] x)
		{
		double result = Double.POSITIVE_INFINITY;
		for (int col = 0; col < x.length; col++)
			{
			result = Math.min(result, x[col]);
			}
		return result;
		}

	public static int argmin(@NotNull double[] x)
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
	@NotNull
	public static int[] positions(@NotNull int[] x, int value)
		{
		@NotNull int result[] = new int[x.length];
		int count = 0;
		for (int col = 0; col < x.length; col++)
			{
			if (x[col] == value)
				{
				result[count] = col;
				count++;
				}
			}
		/*
		// we allocated an array of size x.length.  We could now reallocate one of size count,
		// but to avoid doing that we just put the count in the last element of the array.
		// this hack assumes that the value is not found in _every_ position of x.
		result[result.length - 1] = count;
		*/
		result = prefix(result, count);
		return result;
		}

	/**
	 * Note this doesn't work with NaN
	 *
	 * @param x
	 * @param value
	 * @return
	 */
	@NotNull
	public static boolean[] mapEquals(@NotNull double[] x, double value)
		{
		@NotNull boolean result[] = new boolean[x.length];

		for (int col = 0; col < x.length; col++)
			{
			result[col] = x[col] == value;
			}
		return result;
		}

	@NotNull
	public static boolean[] mapEquals(@NotNull double[] x, double value, boolean[] mask)
		{
		@NotNull boolean result[] = new boolean[x.length];

		for (int col = 0; col < x.length; col++)
			{
			result[col] = mask[col] && x[col] == value;
			}
		return result;
		}

	@NotNull
	public static boolean[] mapNotEquals(@NotNull double[] x, double value)
		{
		@NotNull boolean result[] = new boolean[x.length];

		for (int col = 0; col < x.length; col++)
			{
			result[col] = x[col] != value;
			}
		return result;
		}

	@NotNull
	public static boolean[] mapNotEquals(@NotNull double[] x, double value, boolean[] mask)
		{
		@NotNull boolean result[] = new boolean[x.length];

		for (int col = 0; col < x.length; col++)
			{
			result[col] = mask[col] && x[col] != value;
			}
		return result;
		}

	@NotNull
	public static boolean[] mapNotNaN(@NotNull double[] x)
		{
		@NotNull boolean result[] = new boolean[x.length];

		for (int col = 0; col < x.length; col++)
			{
			result[col] = !Double.isNaN(x[col]);
			}
		return result;
		}

	@NotNull
	public static boolean[] mapNotNaN(@NotNull double[] x, boolean[] mask)
		{
		@NotNull boolean result[] = new boolean[x.length];

		for (int col = 0; col < x.length; col++)
			{
			result[col] = mask[col] && !Double.isNaN(x[col]);
			}
		return result;
		}

	@NotNull
	public static boolean[] mapNotNaNOrInfinite(@NotNull double[] x)
		{
		@NotNull boolean result[] = new boolean[x.length];

		for (int col = 0; col < x.length; col++)
			{
			result[col] = !(Double.isNaN(x[col]) || Double.isInfinite(x[col]));
			}
		return result;
		}

	@NotNull
	public static boolean[] mapNotNaNOrInfinite(@NotNull double[] x, boolean[] mask)
		{
		@NotNull boolean result[] = new boolean[x.length];

		for (int col = 0; col < x.length; col++)
			{
			result[col] = mask[col] && !(Double.isNaN(x[col]) || Double.isInfinite(x[col]));
			}
		return result;
		}


	/**
	 * @param x
	 * @param value
	 */
	public static int count(@NotNull double[] x, double value)
		{
		int count = 0;
		for (int col = 0; col < x.length; col++)
			{
			if (x[col] == value)
				{
				count++;
				}
			}
		return count;
		}

	/**
	 * @param x
	 * @param value
	 */
	public static int count(@NotNull boolean[] x, boolean value)
		{
		int count = 0;
		for (int col = 0; col < x.length; col++)
			{
			if (x[col] == value)
				{
				count++;
				}
			}
		return count;
		}

	/**
	 * @param x
	 * @param value
	 */
	public static int countNot(@NotNull double[] x, double value)
		{
		int count = 0;
		for (int col = 0; col < x.length; col++)
			{
			if (x[col] != value)
				{
				count++;
				}
			}
		return count;
		}

	/**
	 * @param x
	 */
	public static int countNotNaN(@NotNull double[] x)
		{
		int count = 0;
		for (int col = 0; col < x.length; col++)
			{
			if (!Double.isNaN(x[col]))
				{
				count++;
				}
			}
		return count;
		}

	/**
	 * @param x
	 */
	public static int countNotNaNOrInfinite(@NotNull double[] x)
		{
		int count = 0;
		for (int col = 0; col < x.length; col++)
			{
			if (!(Double.isNaN(x[col]) || Double.isInfinite(x[col])))
				{
				count++;
				}
			}
		return count;
		}

	@NotNull
	public static double[] grow(@NotNull double[] a, int x)
		{
		@NotNull double[] result = new double[a.length + x];
		for (int i = 0; i < a.length; i++)
			{
			result[i] = a[i];
			}
		return result;
		}

	public static String asString(@NotNull char[] x, String separator)
		{
		@NotNull StringBuffer sb = new StringBuffer();
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

	public static String asString(@NotNull int[] x, String separator)
		{
		@NotNull StringBuffer sb = new StringBuffer();
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

	public static boolean contains(@NotNull Object[] a, Object o)
		{
		for (int i = 0; i < a.length; i++)
			{
			if (a[i].equals(o))
				{
				return true;
				}
			}
		return false;
		}

	@NotNull
	public static double[] castToDouble(@NotNull int[] p)
		{
		@NotNull double[] result = new double[p.length];
		for (int i = 0; i < p.length; i++)
			{
			result[i] = p[i];
			}
		return result;
		}

	@NotNull
	public static double[] castToDouble(@NotNull float[] p)
		{
		@NotNull double[] result = new double[p.length];
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
	@NotNull
	public static byte[] prefix(byte[] s, int i)
		{
		@NotNull byte[] result = new byte[i];
		System.arraycopy(s, 0, result, 0, i);
		return result;
		}

	/**
	 * returns a new double[] containing the first i characters of s.
	 *
	 * @param s
	 * @param i
	 * @return
	 */
	@NotNull
	public static double[] prefix(double[] s, int i)
		{
		@NotNull double[] result = new double[i];
		System.arraycopy(s, 0, result, 0, i);
		return result;
		}

	/**
	 * returns a new int[] containing the first i characters of s.
	 *
	 * @param s
	 * @param i
	 * @return
	 */
	@NotNull
	public static int[] prefix(int[] s, int i)
		{
		@NotNull int[] result = new int[i];
		System.arraycopy(s, 0, result, 0, i);
		return result;
		}

	/**
	 * returns a new byte[] containing all characters of s starting from startPos
	 *
	 * @param s
	 * @param startpos
	 * @return
	 * @see #suffixOfLength
	 */
	@NotNull
	public static byte[] suffix(@NotNull byte[] s, int startpos)
		{
		@NotNull byte[] result = new byte[s.length - startpos];
		System.arraycopy(s, startpos, result, 0, s.length - startpos);
		return result;
		}

	/**
	 * returns a new double[] containing all characters of s starting from startPos
	 *
	 * @param s
	 * @param startpos
	 * @return
	 * @see #suffixOfLength
	 */
	@NotNull
	public static double[] suffix(@NotNull double[] s, int startpos)
		{
		@NotNull double[] result = new double[s.length - startpos];
		System.arraycopy(s, startpos, result, 0, s.length - startpos);
		return result;
		}

	/**
	 * returns a new String[] containing all characters of s starting from startPos
	 *
	 * @param s
	 * @param startpos
	 * @return
	 * @see #suffixOfLength
	 */
	@NotNull
	public static String[] suffix(@NotNull String[] s, int startpos)
		{
		@NotNull String[] result = new String[s.length - startpos];
		System.arraycopy(s, startpos, result, 0, s.length - startpos);
		return result;
		}

	@NotNull
	public static byte[] suffixOfLength(@NotNull byte[] s, int length)
		{
		if (length > s.length)
			{
			throw new IndexOutOfBoundsException("Requested suffix is longer than the array");
			}
		@NotNull byte[] result = new byte[length];
		System.arraycopy(s, s.length - length, result, 0, length);
		return result;
		}

	@NotNull
	public static byte[] prepend(byte b, @NotNull byte[] s)
		{
		@NotNull byte[] result = new byte[s.length + 1];
		result[0] = b;
		System.arraycopy(s, 0, result, 1, s.length);
		return result;
		}

	@NotNull
	public static byte[] append(@NotNull byte[] s, byte b)
		{
		@NotNull byte[] result = new byte[s.length + 1];
		System.arraycopy(s, 0, result, 0, s.length);
		result[s.length] = b;
		return result;
		}

	public static double mean(@NotNull double[] counts)
		{
		double sum = 0;
		for (int i = 0; i < counts.length; i++)
			{
			sum += counts[i];
			}
		return sum / (double) counts.length;
		}

	public static double mean(@NotNull int[] counts)
		{
		double sum = 0;
		for (int i = 0; i < counts.length; i++)
			{
			sum += counts[i];
			}
		return sum / (double) counts.length;
		}

	/**
	 * Since we will often have computed the mean already when calling this, we just pass it in instead of recomputing it
	 *
	 * @param x
	 * @param mean
	 * @return
	 */
	public static double stddev(@NotNull double[] x, double mean)
		{
		double sumsq = 0;
		for (int i = 0; i < x.length; i++)
			{
			double dev = x[i] - mean;
			sumsq += dev * dev;
			}
		return Math.sqrt(sumsq / (double) x.length);
		}

	/**
	 * Since we will often have computed the mean already when calling this, we just pass it in instead of recomputing it
	 *
	 * @param x
	 * @param mean
	 * @return
	 */
	public static double stddev(@NotNull int[] x, double mean)
		{
		double sumsq = 0;
		for (int i = 0; i < x.length; i++)
			{
			double dev = x[i] - mean;
			sumsq += dev * dev;
			}
		return Math.sqrt(sumsq / (double) x.length);
		}

	public static double mean(@NotNull Collection<Double> counts)
		{
		double sum = 0;
		for (Double d : counts)
			{
			sum += d;
			}
		return sum / (double) counts.size();
		}

	/**
	 * Since we will often have computed the mean already when calling this, we just pass it in instead of recomputing it
	 *
	 * @param x
	 * @param mean
	 * @return
	 */
	public static double stddev(@NotNull Collection<Double> x, double mean)
		{
		double sumsq = 0;
		for (Double d : x)
			{
			double dev = d - mean;
			sumsq += dev * dev;
			}
		return Math.sqrt(sumsq / (double) x.size());
		}


	public static double[] times(@NotNull double[] data, double scalar)
		{
		double[] result = data.clone();// does this work??
		multiplyBy(result, scalar);
		return result;
		}

	public static void multiplyBy(@NotNull double[] a, double scalar)
		{
		for (int i = 0; i < a.length; i++)
			{
			//logger.trace("Adding cells: " + i + ", " + j);
			a[i] *= scalar;
			}
		}

	public static double norm(@NotNull int[] x)
		{
		int sumsq = 0;
		for (int i = 0; i < x.length; i++)
			{
			sumsq += x[i] * x[i];
			}
		return Math.sqrt(sumsq);
		}

	public static double norm(@NotNull double[] x)
		{
		double sumsq = 0;
		for (int i = 0; i < x.length; i++)
			{
			sumsq += x[i] * x[i];
			}
		return Math.sqrt(sumsq);
		}

	/**
	 * Linearly scale the values in the given array in place such that the minimum and maximum values are as requested.
	 *
	 * @param x
	 * @param newMin
	 * @param newMax
	 */
	public static void rescale(@NotNull double[] x, double newMin, double newMax)
		{
		double newSpan = newMax - newMin;

		double oldMin = min(x);
		double oldMax = max(x);
		double oldSpan = oldMax - oldMin;

		for (int i = 0; i < x.length; i++)
			{
			x[i] = newMin + ((x[i] - oldMin) / oldSpan) * newSpan;
			}
		}

	public static int compare(@NotNull int[] a, @NotNull int[] b)
		{
		if (a.length < b.length)
			{
			return -1;
			}
		if (b.length < a.length)
			{
			return 1;
			}

		for (int i = 0; i < a.length; i++)
			{
			int result = a[i] < b[i] ? -1 : a[i] > b[i] ? 1 : 0;
			if (result != 0)
				{
				return result;
				}
			}
		return 0;
		}


	public static int compare(@NotNull char[] a, @NotNull char[] b)
		{
		if (a.length < b.length)
			{
			return -1;
			}
		if (b.length < a.length)
			{
			return 1;
			}

		for (int i = 0; i < a.length; i++)
			{
			int result = a[i] < b[i] ? -1 : a[i] > b[i] ? 1 : 0;
			if (result != 0)
				{
				return result;
				}
			}
		return 0;
		}

	@NotNull
	public static double[] toPrimitiveDoubleArray(@NotNull Collection<Double> c)
		{
		@NotNull double[] result = new double[c.size()];
		int i = 0;
		for (Double d : c)
			{
			result[i] = d;
			i++;
			}
		return result;
		}

	@NotNull
	public static int[] toPrimitiveIntArray(@NotNull Collection<Integer> c)
		{
		@NotNull int[] result = new int[c.size()];
		int i = 0;
		for (Integer d : c)
			{
			result[i] = d;
			i++;
			}
		return result;
		}

	public static boolean allValuesAreNumeric(@NotNull double[] value)
		{
		for (double v : value)
			{
			if (Double.isInfinite(v) || Double.isNaN(v))
				{
				return false;
				}
			}
		return true;
		}

	@NotNull
	public static double[] createIncrementingDoubleArray(final int size, final double start, final double increment)
		{
		@NotNull double[] result = new double[size];
		double trav = start;
		for (int i = 0; i < size; i++)
			{
			result[i] = trav;
			trav += increment;
			}
		return result;
		}

	@NotNull
	public static int[] createIncrementingIntArray(final int size, final int start, final int increment)
		{
		@NotNull int[] result = new int[size];
		int trav = start;
		for (int i = 0; i < size; i++)
			{
			result[i] = trav;
			trav += increment;
			}
		return result;
		}

	public static int floorElement(final int[] nastWidths, final int nastWidth)
		{
		int i = Arrays.binarySearch(nastWidths, nastWidth);
		if (i >= 0)
			{
			return nastWidths[i];
			}

		int insertionPoint = -(i + 1);
		return nastWidths[insertionPoint];
		}

	@NotNull
	public static int[] castToInt(@NotNull final Double[] p)
		{
		@NotNull int[] result = new int[p.length];
		for (int i = 0; i < p.length; i++)
			{
			result[i] = p[i].intValue();
			}
		return result;
		}

	@NotNull
	public static int[] castToInt(@NotNull final boolean[] p)
		{
		@NotNull int[] result = new int[p.length];
		for (int i = 0; i < p.length; i++)
			{
			result[i] = p[i] ? 1 : 0;
			}
		return result;
		}

	@NotNull
	public static byte[] castToByte(@NotNull final char[] p)
		{
		@NotNull byte[] result = new byte[p.length];
		for (int i = 0; i < p.length; i++)
			{
			result[i] = (byte) p[i];
			}
		return result;
		}


	@NotNull
	public static byte[] toByteArray(@NotNull final String s)
		{
		return castToByte(s.toCharArray());
		}

	@NotNull
	public static double[] toDoubleArray(@NotNull final List<String> l) throws NumberFormatException
		{
		@NotNull double[] result = new double[l.size()];
		int i = 0;
		for (String s : l)
			{
			result[i] = Double.parseDouble(s);
			i++;
			}
		return result;
		}

	public static boolean isNumberArray(@NotNull Object o)
		{
		final Class<?> arrayType = o.getClass().getComponentType();
		return o.getClass().isArray() && DSClassUtils.isAssignable(arrayType, Number.class);
		}

	public static boolean isPrimitiveArray(@NotNull Object o)
		{
		final Class<?> arrayType = o.getClass().getComponentType();
		return o.getClass().isArray() && arrayType.isPrimitive();
		}

	public static String[] mapToString(@NotNull final Object[] os)
		{
		@NotNull List<String> result = new ArrayList<String>(os.length);
		for (@NotNull Object o : os)
			{
			result.add(o.toString());
			}
		return result.toArray(EMPTY_STRING_ARRAY);
		}

	@NotNull
	public static double[] select(final double[] d, @NotNull final boolean[] mask)
		{
		int s = count(mask, true);
		@NotNull double[] result = new double[s];
		int di = 0;
		for (int i = 0; i < s; i++)
			{
			while (!mask[di])
				{
				di++;
				}
			result[i] = d[di];
			di++;
			}
		return result;
		}
	}
