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

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lorax
 * @version 1.0
 */
public class StringUtils
	{
// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(StringUtils.class);

// -------------------------- STATIC METHODS --------------------------

	/**
	 * Return the String representation of the given object, or the string "null" if it is null.  Helpful for avoiding NullPointerExceptions.
	 */
	public static String s(Object o)
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
	public static String HtmlEncode(String val)
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
	public static String cap(String s)
		{
		return s.substring(0, 1).toUpperCase() + s.substring(1);
		}

	/**
	 * Replace any single quotes with doubled single quotes, so "foo 'bar' foo" becomes "foo ''bar'' foo".
	 */
	public static String oracleEscapeSingleQuotes(String s)
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
	public static Set TokenSet(String s, String delim)
		{
		//return new HashSet<String>(org.apache.commons.lang.StringUtils.split(s, delim));
		StringTokenizer st = new StringTokenizer(s, delim);
		Set result = new HashSet();

		while (st.hasMoreTokens())
			{
			result.add(st.nextToken());
			}

		return result;
		}

	/**
	 * Join a Set of Strings together into one string, separating the tokens with a delimiter.
	 * The delimiting string will be placed only between tokens, and will not be added before the first or
	 * after the last token.
	 *
	 * @param s     The Set of Strings to be joined.
	 * @param delim A string to insert between tokens.
	 * @return The joined string.
	 * @deprecated use orf.apache.commons.lang.StringUtils.join()
	 */
	public static String joinStringSet(Set s, String delim)
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
		}

	/**
	 * Return the name of the month associated with the given int.  I think these are 0-based,
	 * but it's easiest just to use Calendar.JANUARY and so on to avoid any confusion.
	 */
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
				case 'à':
					sb.append("&agrave;");
					break;
				case 'À':
					sb.append("&Agrave;");
					break;
				case 'â':
					sb.append("&acirc;");
					break;
				case 'Â':
					sb.append("&Acirc;");
					break;
				case 'ä':
					sb.append("&auml;");
					break;
				case 'Ä':
					sb.append("&Auml;");
					break;
				case 'å':
					sb.append("&aring;");
					break;
				case 'Å':
					sb.append("&Aring;");
					break;
				case 'æ':
					sb.append("&aelig;");
					break;
				case 'Æ':
					sb.append("&AElig;");
					break;
				case 'ç':
					sb.append("&ccedil;");
					break;
				case 'Ç':
					sb.append("&Ccedil;");
					break;
				case 'é':
					sb.append("&eacute;");
					break;
				case 'É':
					sb.append("&Eacute;");
					break;
				case 'è':
					sb.append("&egrave;");
					break;
				case 'È':
					sb.append("&Egrave;");
					break;
				case 'ê':
					sb.append("&ecirc;");
					break;
				case 'Ê':
					sb.append("&Ecirc;");
					break;
				case 'ë':
					sb.append("&euml;");
					break;
				case 'Ë':
					sb.append("&Euml;");
					break;
				case 'ï':
					sb.append("&iuml;");
					break;
				case 'Ï':
					sb.append("&Iuml;");
					break;
				case 'ô':
					sb.append("&ocirc;");
					break;
				case 'Ô':
					sb.append("&Ocirc;");
					break;
				case 'ö':
					sb.append("&ouml;");
					break;
				case 'Ö':
					sb.append("&Ouml;");
					break;
				case 'ø':
					sb.append("&oslash;");
					break;
				case 'Ø':
					sb.append("&Oslash;");
					break;
				case 'ß':
					sb.append("&szlig;");
					break;
				case 'ù':
					sb.append("&ugrave;");
					break;
				case 'Ù':
					sb.append("&Ugrave;");
					break;
				case 'û':
					sb.append("&ucirc;");
					break;
				case 'Û':
					sb.append("&Ucirc;");
					break;
				case 'ü':
					sb.append("&uuml;");
					break;
				case 'Ü':
					sb.append("&Uuml;");
					break;
				case '®':
					sb.append("&reg;");
					break;
				case '©':
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
	}
