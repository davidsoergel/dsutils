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

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @author <a href="mailto:dev.davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class PropertiesUtils
	{
	private static final Logger logger = Logger.getLogger(PropertiesUtils.class);


	public static File findPropertiesFile(String environmentVariableName, String homeDirSubdirName,
	                                      String defaultFileName)
		{
		File result;

		String propsFilename = System.getProperty(environmentVariableName);
		logger.debug("Looking for properties file: " + propsFilename);
		if (propsFilename != null)
			{
			result = new File(propsFilename);
			if (result.exists() && result.canRead())
				{
				return result;
				}
			}

		propsFilename = System.getProperty("user.dir") + System.getProperty("file.separator") + defaultFileName;
		logger.debug("Looking for properties file: " + propsFilename);
		result = new File(propsFilename);
		if (result.exists() && result.canRead())
			{
			return result;
			}


		propsFilename = System.getProperty("user.home") + System.getProperty("file.separator") + homeDirSubdirName
				+ System.getProperty("file.separator") + defaultFileName;
		logger.debug("Looking for properties file: " + propsFilename);
		result = new File(propsFilename);
		if (result.exists() && result.canRead())
			{
			return result;
			}

		URL resource = ClassLoader.getSystemClassLoader().getResource(defaultFileName);
		logger.debug("Looking for properties file: " + resource);
		if (resource != null)
			{
			propsFilename = resource.getFile();
			result = new File(propsFilename);
			if (result.exists() && result.canRead())
				{
				return result;
				}
			}

		logger.error("Could not find properties file: " + defaultFileName);
		throw new RuntimeException("Could not find properties file: " + defaultFileName);
		}

	public static Map<String, Properties> splitPeriodDelimitedProperties(Properties p)
		{
		PropertiesToMapAdapter pm = new PropertiesToMapAdapter(p);

		Map<String, Properties> result = new HashMap<String, Properties>();

		for (Map.Entry<String, Object> entry : pm.entrySet())
			//for (String key : pm.keySet())
			{
			String[] keyparts = entry.getKey().split("\\.");
			String dbname = keyparts[0];

			///if (keyparts[1].equals("url") || keyparts[1].equals("username") || keyparts[1].equals("password")
			//		|| keyparts[1].equals("driver"))
			//	{
			if (keyparts.length > 1)
				{
				Properties props = result.get(dbname);
				if (props == null)
					{
					props = new Properties();
					result.put(dbname, props);
					}
				props.put(keyparts[1], entry.getValue());
				}
			//	}
			//else
			//	{
			//	logger.warn("unknown database property: " + key);
			//	}
			}
		return result;
		}


	public static Map<String, Properties> splitPeriodDelimitedPropertiesFromStream(InputStream s) throws IOException
		{
		Properties p = new Properties();
		p.load(s);
		return splitPeriodDelimitedProperties(p);
		}

	public static Map<String, Properties> splitPeriodDelimitedPropertiesFromFilename(String propsFilename)
			throws IOException
		{
		return splitPeriodDelimitedPropertiesFromStream(new FileInputStream(propsFilename));
		}

	public static Map<String, Properties> splitPeriodDelimitedPropertiesFromFile(File propsFile) throws IOException
		{
		return splitPeriodDelimitedPropertiesFromStream(new FileInputStream(propsFile));
		}
	}
