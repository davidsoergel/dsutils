/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;


/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class DSDurationFormatUtils extends org.apache.commons.lang.time.DurationFormatUtils
	{
	/**
	 * Copied from super.formatDurationWords license http://www.apache.org/licenses/LICENSE-2.0
	 *
	 * @param durationMillis
	 * @param suppressLeadingZeroElements
	 * @param suppressTrailingZeroElements
	 * @return
	 */
	public static String formatDurationShortWords(long durationMillis, boolean suppressLeadingZeroElements,
	                                              boolean suppressTrailingZeroElements)
		{


		String duration = formatDuration(durationMillis, "d'd 'H'h 'm'm 's's'");
		if (suppressLeadingZeroElements)
			{
			duration = " " + duration;
			String tmp = DSStringUtils.replaceOnce(duration, " 0d", "");
			if (tmp.length() != duration.length())
				{
				duration = tmp;
				tmp = DSStringUtils.replaceOnce(duration, " 0h", "");
				if (tmp.length() != duration.length())
					{
					duration = tmp;
					tmp = DSStringUtils.replaceOnce(duration, " 0m", "");
					duration = tmp;
					if (tmp.length() != duration.length())
						{
						duration = DSStringUtils.replaceOnce(tmp, " 0s", "");
						}
					}
				}

			if (duration.length() != 0)
				{
				duration = duration.substring(1);
				}
			}

		if (suppressTrailingZeroElements)
			{
			String tmp = DSStringUtils.replaceOnce(duration, " 0s", "");
			if (tmp.length() != duration.length())
				{
				duration = tmp;
				tmp = DSStringUtils.replaceOnce(duration, " 0m", "");
				if (tmp.length() != duration.length())
					{
					duration = tmp;
					tmp = DSStringUtils.replaceOnce(duration, " 0h", "");
					if (tmp.length() != duration.length())
						{
						duration = DSStringUtils.replaceOnce(tmp, " 0d", "");
						}
					}
				}
			}
		return duration.trim();
		}
	}

