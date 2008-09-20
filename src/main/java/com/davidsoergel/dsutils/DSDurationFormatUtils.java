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

package com.davidsoergel.dsutils;


/**
 * @author <a href="mailto:dev.davidsoergel.com">David Soergel</a>
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

