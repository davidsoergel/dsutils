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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Arrays;

/**
 * @author lorax
 * @version 1.0
 */
public class CollectionUtils
	{
// -------------------------- STATIC METHODS --------------------------

	/**
	 * Shallow-copy any Collection into an ArrayList.  Useful for making a local copy for iteration,
	 * to avoid ConcurrentModificationExceptions.
	 */
	public static <T> ArrayList<T> ArrayList(Collection<T> c)
		{
		if (c instanceof ArrayList)
			{
			return (ArrayList<T>) c;
			}

		return new ArrayList<T>(c);
		}

	public static ArrayList<Double> plus(List<Number> a, List<Number> b)
		{
		if (a.size() != b.size())
			{
			throw new IndexOutOfBoundsException("Can't add arrays of different sizes");
			}
		ArrayList<Double> result = new ArrayList<Double>(a.size());


		for (int i = 0; i < a.size(); i++)
			{
			//logger.debug("Adding cells: " + i + ", " + j);
			result.add(a.get(i).doubleValue() + b.get(i).doubleValue());
			}
		return result;
		}


	public static ArrayList<Double> minus(List<Number> a, List<Number> b)
		{
		if (a.size() != b.size())
			{
			throw new IndexOutOfBoundsException("Can't add arrays of different sizes");
			}
		ArrayList<Double> result = new ArrayList<Double>(a.size());


		for (int i = 0; i < a.size(); i++)
			{
			//logger.debug("Adding cells: " + i + ", " + j);
			result.add(a.get(i).doubleValue() - b.get(i).doubleValue());
			}
		return result;
		}

	public static boolean deepEqualsWithinFPError(Set<Double> a, Set<Double> b)
		{
		Double[] aa= a.toArray(new Double[]{});
		Double[] bb= b.toArray(new Double[]{});
		Arrays.sort(aa);
		Arrays.sort(bb);
		return ArrayUtils.equalWithinFPError(aa,bb);
		}
}
