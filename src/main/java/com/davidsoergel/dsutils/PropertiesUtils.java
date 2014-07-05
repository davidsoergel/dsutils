/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class PropertiesUtils
	{
	private static final Logger logger = Logger.getLogger(PropertiesUtils.class);


	public static File findPropertiesFile(String environmentVariableName, String homeDirSubdirName,
	                                      String defaultFileName) throws FileNotFoundException
		{
		File result;

		String propsFilename = System.getenv(environmentVariableName);
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
		throw new FileNotFoundException("Could not find properties file: " + defaultFileName);
		}

	@NotNull
	public static Map<String, Properties> splitPeriodDelimitedProperties(Properties p)
		{
		@NotNull PropertiesToMapAdapter pm = new PropertiesToMapAdapter(p);

		@NotNull Map<String, Properties> result = new HashMap<String, Properties>();

		for (@NotNull Map.Entry<String, Object> entry : pm.entrySet())
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
			else
				{
				Properties props = result.get("NODATABASE");
				if (props == null)
					{
					props = new Properties();
					result.put("NODATABASE", props);
					}
				props.put(keyparts[0], entry.getValue());
				}
			//	}
			//else
			//	{
			//	logger.warn("unknown database property: " + key);
			//	}
			}
		return result;
		}


	@NotNull
	public static Map<String, Properties> splitPeriodDelimitedPropertiesFromStream(InputStream s) throws IOException
		{
		@NotNull Properties p = new Properties();
		p.load(s);
		return splitPeriodDelimitedProperties(p);
		}

	@NotNull
	public static Map<String, Properties> splitPeriodDelimitedPropertiesFromFilename(String propsFilename)
			throws IOException
		{
		return splitPeriodDelimitedPropertiesFromStream(new FileInputStream(propsFilename));
		}

	@NotNull
	public static Map<String, Properties> splitPeriodDelimitedPropertiesFromFile(File propsFile) throws IOException
		{
		return splitPeriodDelimitedPropertiesFromStream(new FileInputStream(propsFile));
		}

	public static Properties loadPropsFromFile(File propsFile) throws IOException
		{
		Properties props;

		props = new Properties();
		@Nullable FileInputStream inStream = null;
		try
			{
			inStream = new FileInputStream(propsFile);
			props.load(inStream);
			}
		finally
			{
			if (inStream != null)
				{
				inStream.close();
				}
			}
		return props;
		}
	}
