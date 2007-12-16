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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author lorax
 * @version 1.0
 */
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils
	{
	// -------------------------- STATIC METHODS --------------------------

	/**
	 * Shallow-copy any Collection into an ArrayList.  Useful for making a local copy for iteration, to avoid
	 * ConcurrentModificationExceptions.
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
		Double[] aa = a.toArray(new Double[]{});
		Double[] bb = b.toArray(new Double[]{});
		Arrays.sort(aa);
		Arrays.sort(bb);
		return ArrayUtils.equalWithinFPError(aa, bb);
		}

	public static void retainRandom(LinkedList list, int numElements)
		{
		//inefficient...?
		while (list.size() > numElements)
			{
			list.remove(MersenneTwisterFast.randomInt(list.size()));
			}
		}
	}
