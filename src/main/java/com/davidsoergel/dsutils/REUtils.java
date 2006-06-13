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
		if (!m.find()) return null;
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
		if (!m.find()) return null;
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
