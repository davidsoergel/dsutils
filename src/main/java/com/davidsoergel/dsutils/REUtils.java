/*
 * Copyright (c) 2001-2008 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * dev@davidsoergel.com
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lorax
 * @version 1.0
 */
public class REUtils
	{
	// -------------------------- STATIC METHODS --------------------------

	/**
	 * Find the first regex match in a string.
	 *
	 * @param s       The string to search in.
	 * @param pattern The regular expression to look for.
	 * @return the first substring of the given string matching the given regular expression, or null if no match.
	 */
	public static String matchRE(String s, String pattern)
		{
		return matchRE(s, pattern, 0);
		}

	/**
	 * Find the nth regex match in a string.
	 *
	 * @param s         The string to search in.
	 * @param pattern   The regular expression to look for.
	 * @param groupnums Array of group numbers to return (0-based).
	 * @return the first substring of the given string matching the given regular expression, or null if no match.
	 */
	public static String[] matchRE(String s, String pattern, int[] groupnums)
		{
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(s);
		if (!m.find())
			{
			return null;
			}
		try
			{
			String[] groups = new String[groupnums.length];
			for (int g = 0; g < groupnums.length; g++)
				{
				groups[g] = m.group(groupnums[g]);
				}
			return groups;
			}
		catch (IllegalStateException e)
			{
			return null;
			}
		}

	/**
	 * Find the nth regex match in a string.
	 *
	 * @param s        The string to search in.
	 * @param pattern  The regular expression to look for.
	 * @param groupnum Look for the groupnum'th match (0-based).
	 * @return the first substring of the given string matching the given regular expression, or null if no match.
	 */
	public static String matchRE(String s, String pattern, int groupnum)
		{
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(s);
		if (!m.find())
			{
			return null;
			}
		try
			{
			return m.group(groupnum);
			}
		catch (IllegalStateException e)
			{
			return null;
			}
		}

	public static List matchAllRE(String s, String pattern)
		{
		return matchAllRE(s, pattern, 0);
		}

	/**
	 * Find the nth regex match in a string.
	 *
	 * @param s       The string to search in.
	 * @param pattern The regular expression to look for.
	 * @return the first substring of the given string matching the given regular expression, or null if no match.
	 */
	public static List matchAllRE(String s, String pattern, int groupnum)
		{
		List result = new ArrayList();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(s);
		while (m.find())
			{
			result.add(m.group(groupnum));
			}

		return result;
		}

	/**
	 * Find the nth regex match in a string.
	 *
	 * @param s         The string to search in.
	 * @param pattern   The regular expression to look for.
	 * @param groupnums Array of group numbers to return (0-based).
	 * @return the first substring of the given string matching the given regular expression, or null if no match.
	 */
	public static List matchAllRE(String s, String pattern, int[] groupnums)
		{
		List result = new ArrayList();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(s);
		while (m.find())
			{
			try
				{
				String[] groups = new String[groupnums.length];
				for (int g = 0; g < groupnums.length; g++)
					{
					groups[g] = m.group(groupnums[g]);
					}
				result.add(groups);
				}
			catch (IllegalStateException e)
				{
				// too bad
				}
			}
		return result;
		}
	}
