/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 * @version $Id$
 */
public class DSStringUtils extends org.apache.commons.lang.StringUtils
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(DSStringUtils.class);


	// -------------------------- STATIC METHODS --------------------------

	/**
	 * Return the String representation of the given object, or the string "null" if it is null.  Helpful for avoiding
	 * NullPointerExceptions.
	 */
	public static String s(@Nullable Object o)
		{
		if (o == null)
			{
			return "";
			}

		String s = o.toString();

		if (s.equals("null"))
			{
			s = "";
			}

		return s;
		}

	/**
	 * Converts a string into an html encoded string.
	 *
	 * @param val The string to be encoded.
	 * @return The given string with any special characters replaced with the appropriate HTML codes.
	 * @deprecated use {@link org.apache.commons.lang.StringEscapeUtils}.
	 */
	public static String htmlEncode(String val)
		{
		return org.apache.commons.lang.StringEscapeUtils.escapeHtml(val);
		/*if (val != null)
			{
			StringBuffer buf = new StringBuffer(val.length() + 8);
			char c;

			for (int i = 0; i < val.length(); i++)
				{
				c = val.charAt(i);

				switch (c)
					{

					case '<':
						buf.append("&lt;");

						break;

					case '>':
						buf.append("&gt;");

						break;

					case '&':
						buf.append("&amp;");

						break;

					case '\"':
						buf.append("&#034;");

						break;

					case '\'':
						buf.append("&#039;");

						break;

					default:
						buf.append(c);

						break;
					}
				}

			return buf.toString();
			}
		else
			{
			return "";
			}*/
		}

	/**
	 * Capitalize the given string.
	 *
	 * @return The given string with the first letter capitalized.
	 */
	@NotNull
	public static String cap(@NotNull String s)
		{
		return s.substring(0, 1).toUpperCase() + s.substring(1);
		}

	/**
	 * Replace any single quotes with doubled single quotes, so "foo 'bar' foo" becomes "foo ''bar'' foo".
	 */
	@Nullable
	public static String oracleEscapeSingleQuotes(@Nullable String s)
		{
		if (s == null)
			{
			return null;
			}

		int i = s.indexOf("'");

		if (i == -1)
			{
			return s;
			}

		try
			{
			return s.substring(0, i) + "''" + oracleEscapeSingleQuotes(s.substring(i + 1));
			}
		catch (StringIndexOutOfBoundsException e)
			{
			return s.substring(0, i) + "''";
			}
		}

	/**
	 * Tokenize a string.
	 *
	 * @param s     The string to be tokenized.
	 * @param delim The delimiters to use (see {@link StringTokenizer} for details)
	 * @return A Set of tokens.
	 * @deprecated use org.apache.commons.lang.StringUtils.split()
	 */
	@NotNull
	public static Set<String> tokenSet(String s, String delim)
		{
		//return new HashSet<String>(org.apache.commons.lang.StringUtils.split(s, delim));
		@NotNull StringTokenizer st = new StringTokenizer(s, delim);
		@NotNull Set<String> result = new HashSet<String>();

		while (st.hasMoreTokens())
			{
			result.add(st.nextToken());
			}

		return result;
		}

	/**
	 * Join a Collection of Strings together into one string, separating the tokens with a delimiter. The delimiting string
	 * will be placed only between tokens, and will not be added before the first or after the last token.
	 *
	 * @param s     The Set of Strings to be joined.
	 * @param delim A string to insert between tokens.
	 * @return The joined string. // * @deprecated use org.apache.commons.lang.StringUtils.join()
	 */
	/*	public static String join(Collection s, String delim)
	   {
	   StringBuffer sb = new StringBuffer();

	   for (Iterator i = s.iterator(); i.hasNext();)
		   {
		   sb.append(i.next());

		   if (i.hasNext())
			   {
			   sb.append(delim);
			   }
		   }

	   return sb.toString();
	   }*/
	public static String join(@NotNull Iterable s, String delim)
		{
		return join(s.iterator(), delim);
		}


	public static String joinSorted(Set s, String delim)
		{
		return join(new TreeSet(s), delim);
		}

	public static String join(@NotNull char[] chars, String delim)
		{
		return DSArrayUtils.asString(chars, delim);
		}

	public static String join(double[] doubles, String delim)
		{
		return join(ArrayUtils.toObject(doubles), delim);
		}

	/**
	 * Return the name of the month associated with the given int.  I think these are 0-based, but it's easiest just to use
	 * Calendar.JANUARY and so on to avoid any confusion.
	 */
	@NotNull
	public static String month2name(int i)
		{
		switch (i)
			{
			case Calendar.JANUARY:
				return "January";

			case Calendar.FEBRUARY:
				return "February";

			case Calendar.MARCH:
				return "March";

			case Calendar.APRIL:
				return "April";

			case Calendar.MAY:
				return "May";

			case Calendar.JUNE:
				return "June";

			case Calendar.JULY:
				return "July";

			case Calendar.AUGUST:
				return "August";

			case Calendar.SEPTEMBER:
				return "September";

			case Calendar.OCTOBER:
				return "October";

			case Calendar.NOVEMBER:
				return "November";

			case Calendar.DECEMBER:
				return "December";

			default:
				return "Invalid Month";
			}
		}

	/**
	 * Replace newlines with &lt;P&gt;.
	 */
	/*    public static String text2html(String s) {
			HashMap hm = new HashMap();

			hm.put("", "<P>");

			return substituteAll(s, hm);
		}*/


	/**
	 * Converts a string into an html encoded string.
	 *
	 * @param s The string to be encoded.
	 * @return The given string with any special characters replaced with the appropriate HTML codes.
	 * @deprecated use {@link org.apache.commons.lang.StringEscapeUtils}.
	 */
	public static String escapeHTML(String s)
		{
		return org.apache.commons.lang.StringEscapeUtils.escapeHtml(s);
		}

	/*		StringBuffer sb = new StringBuffer();
		 int n = s.length();
		 for (int i = 0; i < n; i++)
			 {
			 char c = s.charAt(i);
			 switch (c)
				 {
				 case '<':
					 sb.append("&lt;");
					 break;
				 case '>':
					 sb.append("&gt;");
					 break;
				 case '&':
					 sb.append("&amp;");
					 break;
				 case '"':
					 sb.append("&quot;");
					 break;
				 case '?':
					 sb.append("&agrave;");
					 break;
				 case '?':
					 sb.append("&Agrave;");
					 break;
				 case '?':
					 sb.append("&acirc;");
					 break;
				 case '?':
					 sb.append("&Acirc;");
					 break;
				 case '?':
					 sb.append("&auml;");
					 break;
				 case '?':
					 sb.append("&Auml;");
					 break;
				 case '?':
					 sb.append("&aring;");
					 break;
				 case '?':
					 sb.append("&Aring;");
					 break;
				 case '?':
					 sb.append("&aelig;");
					 break;
				 case '?':
					 sb.append("&AElig;");
					 break;
				 case '?':
					 sb.append("&ccedil;");
					 break;
				 case '?':
					 sb.append("&Ccedil;");
					 break;
				 case '?':
					 sb.append("&eacute;");
					 break;
				 case '?':
					 sb.append("&Eacute;");
					 break;
				 case '?':
					 sb.append("&egrave;");
					 break;
				 case '?':
					 sb.append("&Egrave;");
					 break;
				 case '?':
					 sb.append("&ecirc;");
					 break;
				 case '?':
					 sb.append("&Ecirc;");
					 break;
				 case '?':
					 sb.append("&euml;");
					 break;
				 case '?':
					 sb.append("&Euml;");
					 break;
				 case '?':
					 sb.append("&iuml;");
					 break;
				 case '?':
					 sb.append("&Iuml;");
					 break;
				 case '?':
					 sb.append("&ocirc;");
					 break;
				 case '?':
					 sb.append("&Ocirc;");
					 break;
				 case '?':
					 sb.append("&ouml;");
					 break;
				 case '?':
					 sb.append("&Ouml;");
					 break;
				 case '?':
					 sb.append("&oslash;");
					 break;
				 case '?':
					 sb.append("&Oslash;");
					 break;
				 case '?':
					 sb.append("&szlig;");
					 break;
				 case '?':
					 sb.append("&ugrave;");
					 break;
				 case '?':
					 sb.append("&Ugrave;");
					 break;
				 case '?':
					 sb.append("&ucirc;");
					 break;
				 case '?':
					 sb.append("&Ucirc;");
					 break;
				 case '?':
					 sb.append("&uuml;");
					 break;
				 case '?':
					 sb.append("&Uuml;");
					 break;
				 case '?':
					 sb.append("&reg;");
					 break;
				 case '?':
					 sb.append("&copy;");
					 break;
					 //   case 'EUR': sb.append("&euro;"); break;
					 // be carefull with this one (non-breaking whitee space)
					 //   case ' ': sb.append("&nbsp;");break;

				 default:
					 sb.append(c);
					 break;
				 }
			 }
		 return sb.toString();
		 }
 */

	public static void trimAll(@NotNull final String[] result)
		{
		for (int i = 0; i < result.length; i++)
			{
			result[i] = result[i].trim();
			}
		}

	@NotNull
	public static List<String> honorDoubleQuotesAndTabs(@NotNull final String[] argv)
		{
		//List<String> args = new ArrayList<String>(Arrays.asList(argv));
		@NotNull List<String> fileNames = new ArrayList<String>();

		// oh hell
		boolean openQuote = false;
		@NotNull StringBuffer sb = new StringBuffer();


		// internal quotes will mess things up

		for (@NotNull String s : argv)
			{
			if (openQuote)
				{
				sb.append(" ").append(s);
				if (s.endsWith("\""))
					{
					openQuote = false;
					sb.deleteCharAt(0); // open quote
					sb.deleteCharAt(sb.length() - 1); // close quote
					String t = sb.toString().replace("\\t", "\t");
					fileNames.add(t);
					sb = new StringBuffer();
					}
				}
			else
				{
				if (s.startsWith("\""))
					{
					openQuote = true;
					sb.append(s);
					}
				else
					{
					fileNames.add(s);
					}
				}
			}
		return fileNames;
		}
	}
