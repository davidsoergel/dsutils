/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version $Id$
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
	@Nullable
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
	@Nullable
	public static String[] matchRE(String s, String pattern, @NotNull int[] groupnums)
		{
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(s);
		if (!m.find())
			{
			return null;
			}
		try
			{
			@NotNull String[] groups = new String[groupnums.length];
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
	@Nullable
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

	@NotNull
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
	@NotNull
	public static List matchAllRE(String s, String pattern, int groupnum)
		{
		@NotNull List result = new ArrayList();
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
	@NotNull
	public static List matchAllRE(String s, String pattern, @NotNull int[] groupnums)
		{
		@NotNull List result = new ArrayList();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(s);
		while (m.find())
			{
			try
				{
				@NotNull String[] groups = new String[groupnums.length];
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
