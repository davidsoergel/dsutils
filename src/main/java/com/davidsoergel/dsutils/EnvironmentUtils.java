package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class EnvironmentUtils
	{
	private static final Logger logger = Logger.getLogger(EnvironmentUtils.class);

	static
		{
		//	File propFile = new File("system.properties");
		//	init(propFile);
		}

	static String cacheRoot = null; //"/tmp/";
	//static String inputRoot;
	//static String outputRoot;

	public static void init(File propFile)
		{
		FileInputStream in = null;
		try
			{
			in = new FileInputStream(propFile);
			// set up new properties object
			// from file "myProperties.txt"

			Properties p = new Properties(System.getProperties());
			p.load(in);

			// set the system properties
			System.setProperties(p);

			//	inputRoot = System.getProperty("INPUTROOT");
			//	outputRoot = System.getProperty("OUTPUTROOT");
			cacheRoot = System.getProperty("CACHEROOT");

			if (cacheRoot == null)
				{
				logger.warn("No CACHEROOT set, using /tmp");
				cacheRoot = "/tmp/";
				}
			}
		catch (Exception e)
			{
			throw new Error(e);
			}
		finally
			{
			if (in != null)
				{
				try
					{
					in.close();
					}
				catch (IOException e)
					{
					logger.error("Error", e);
					}
				}
			}

		if (!cacheRoot.endsWith(File.separator))
			{
			cacheRoot += File.separator;
			}
		}

	public static String getCacheRoot()
		{
		if (cacheRoot == null)
			{
			throw new Error("EnvironmentUtils has not been initialized");
			}
		return cacheRoot;
		}
/*
	public static String getInputRoot()
		{
		return inputRoot;
		}

	public static String getOutputRoot()
		{
		return outputRoot;
		}*/

	public static void setCacheRoot(String cacheRoot)
		{
		EnvironmentUtils.cacheRoot = cacheRoot;
		}
	}
