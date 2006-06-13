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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * @author lorax
 * @version 1.0
 */
public class SmtpUtils
	{
// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(SmtpUtils.class);

	private static String emailHost;
	private static String emailFrom;
	private static String emailTo;
	private static String emailSubject;

// -------------------------- STATIC METHODS --------------------------

	/**
	 * Get the email host to use for error emailing.
	 */
	public static String getEmailHost()
		{
		return emailHost;
		}

	/**
	 * Set the email host to use for error emailing.
	 *
	 * @param s A fully-qualified SMTP host name.
	 */
	public static void setEmailHost(String s)
		{
		emailHost = s;
		}

	/**
	 * Set the email From: address to use for error emailing.
	 *
	 * @param s An email address.
	 */
	public static void setEmailFrom(String s)
		{
		emailFrom = s;
		}

	/**
	 * Get the email From: address to use for error emailing.
	 */
	public static void setEmailTo(String s)
		{
		emailTo = s;
		}

	/**
	 * Set the email subject to use for error emailing.
	 *
	 * @param s The email subject
	 */
	public static void setEmailSubject(String s)
		{
		emailSubject = s;
		}

	/**
	 * Send an email using the previously set sender, recipient, subject, and emailhost.
	 */
	public static void sendEmail(String s)
		{
		sendEmail(emailHost, emailFrom, emailTo, emailSubject, s);
		}

	/**
	 * Send an email, using the previously set emailhost.
	 */
	public static void sendEmail(String from, String to, String subject, String s)
		{
		sendEmail(emailHost, from, to, subject, s);
		}

	/**
	 * Send an email.
	 */
	public static void sendEmail(String mailhost, String from, String to, String subject, String message)
		{
		Socket mailSocket = null;
		BufferedReader is = null;
		PrintStream os = null;
		StringTokenizer theTokenizer;
		String theToken;
		String servResponse;

		try
			{
			mailSocket = new Socket(mailhost, 25);
			os = new PrintStream(mailSocket.getOutputStream());
			is = new BufferedReader(new InputStreamReader(mailSocket.getInputStream()));
			servResponse = is.readLine();

			// System.err.println(servResponse);

			if (servResponse.startsWith("4"))
				{
				throw new Exception(servResponse);
				}

			os.print("HELO TownError\r\n");

			servResponse = is.readLine();

			// System.err.println(servResponse);

			String lineOut = "MAIL FROM: " + from;

			os.print(lineOut + "\r\n");

			servResponse = is.readLine();

			// System.err.println(servResponse);

			if (servResponse.startsWith("5"))
				{
				throw new Exception(servResponse);
				}

			theTokenizer = new StringTokenizer(to, " ,");

			while (theTokenizer.hasMoreTokens())
				{
				theToken = theTokenizer.nextToken();
				lineOut = "RCPT TO: <" + theToken + ">";

				os.print(lineOut + "\r\n");

				servResponse = is.readLine();

				// System.err.println(servResponse);

				if (servResponse.startsWith("5"))
					{
					throw new Exception(servResponse);
					}
				}

			os.print("DATA\r\n");

			servResponse = is.readLine();

			// System.err.println(servResponse);

			if (servResponse.startsWith("5"))
				{
				throw new Exception(servResponse);
				}

			os.print("To: " + to + "\r\n");
			os.print("From: " + from + "\r\n");
			os.print("Subject: " + subject + "\r\n");
			os.print("Date: " + new java.util.Date() + "\r\n");
			os.print("\r\n");

			message = fixCRLF(message);

			os.print(fixCRLF(message));
			os.print("\r\n.\r\n");
			os.flush();

			servResponse = is.readLine();

			// System.err.println(servResponse);

			if (servResponse.startsWith("5"))
				{
				throw new Exception(servResponse);
				}

			os.print("quit\r\n");

			/*
			 * servResponse = is.readLine();
			 * //System.err.println(servResponse);
			 * if (servResponse.startsWith("5")) {
			 * throw new Exception(servResponse);
			 * }
			 */

			is.close();
			os.close();
			mailSocket.close();
			logger.info("\nException mailed to " + to);
//                    + message);
			}
		catch (Exception e)
			{
			logger.error(e);

			try
				{
				os.print("quit\r\n");
				is.close();
				os.close();
				mailSocket.close();
				}
			catch (Exception f)
				{
				logger.error(f);
				}
			}
		}

	/**
	 * Turn any bare CRs or LFs to CRLF
	 */
	private static String fixCRLF(String s)
		{
		StringBuffer sb = new StringBuffer(s);
		int c = 0;

		if (logger.isDebugEnabled())
			{
			logger.debug("BEFORE: " + sb.length());
			}

		c = sb.toString().indexOf('\n', c);

		try
			{
			if (c == 0)
				{
				sb.insert(c, '\r');

				c++;
				c = sb.toString().indexOf('\n', c + 1);
				}

			while (c != -1)
				{
				if (sb.charAt(c - 1) != '\r')
					{
					sb.insert(c, '\r');

					c++;
					}

				c = sb.toString().indexOf('\n', c + 1);
				if (logger.isDebugEnabled())
					{
					logger.debug(c + ":" + sb.substring((c - 5 < 0 ? 0 : c - 5), (c + 5) > sb.length() ? sb.length() : (c + 5)));
					}
				}
			}
		catch (StringIndexOutOfBoundsException e)
			{
			//System.err.println("Couldn't fix LF -> CRLF at position " + c);
			logger.error("Couldn't fix LF -> CRLF at position " + c, e);
			}

		c = 0;
		c = sb.toString().indexOf('\r', c);

		try
			{
			while (c != -1)
				{
				if (sb.charAt(c + 1) != '\n')
					{
					sb.insert(c + 1, '\n');

					c++;
					}

				c = sb.toString().indexOf('\r', c + 1);

				if (logger.isDebugEnabled())
					{
					logger.debug(c + ":" + sb.substring((c - 5 < 0 ? 0 : c - 5), c + 5));
					}
				}
			}
		catch (StringIndexOutOfBoundsException e)
			{
			logger.debug("Couldn't fix CR -> CRLF at position " + c);
			}


		if (logger.isDebugEnabled())
			{
			logger.debug("AFTER: " + sb.length());
			}


		return sb.toString();
		}

	/**
	 * Turn any bare CRs or LFs to LF
	 */
	public static String normalizeCRLF(String s)
		{
		if (s == null)
			{
			return null;
			}

		StringBuffer sb = new StringBuffer(s);
		int c = 0;

		// first turn bare \r's to \r\n

		c = sb.toString().indexOf('\r', c);

		try
			{
			while (c != -1)
				{
				if (sb.charAt(c + 1) != '\n')
					{
					sb.insert(c + 1, '\n');

					c++;
					}

				c = sb.toString().indexOf('\r', c + 1);

				if (logger.isDebugEnabled())
					{
					logger.debug(c + ":" + sb.substring((c - 5 < 0 ? 0 : c - 5), c + 5));
					}
				}
			}
		catch (StringIndexOutOfBoundsException e)
			{
			logger.error("Couldn't fix CR -> CRLF at position " + c);
			}

		// then remove all \r's

		c = 0;
		c = sb.toString().indexOf('\r', c);

		try
			{
			while (c != -1)
				{
				sb.delete(c, c + 1);

				c = sb.toString().indexOf('\r', c);

				if (logger.isDebugEnabled())
					{
					logger.debug(c + ":" + sb.substring((c - 5 < 0 ? 0 : c - 5), c + 5));
					}
				}
			}
		catch (StringIndexOutOfBoundsException e)
			{
			logger.error("Couldn't remove CR at position " + c);
			}

		return sb.toString();
		}
	}
