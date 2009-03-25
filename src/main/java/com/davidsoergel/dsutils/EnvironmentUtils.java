package com.davidsoergel.dsutils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class EnvironmentUtils
	{
	static
		{
		//	File propFile = new File("system.properties");
		//	init(propFile);
		}

	static String cacheRoot;
	//static String inputRoot;
	//static String outputRoot;

	public static void init(File propFile)
		{
		try
			{
			// set up new properties object
			// from file "myProperties.txt"

			Properties p = new Properties(System.getProperties());
			p.load(new FileInputStream(propFile));

			// set the system properties
			System.setProperties(p);

			//	inputRoot = System.getProperty("INPUTROOT");
			//	outputRoot = System.getProperty("OUTPUTROOT");
			cacheRoot = System.getProperty("CACHEROOT");

			if (cacheRoot == null)
				{
				cacheRoot = "/tmp";
				}
			}
		catch (Exception e)
			{
			throw new Error(e);
			}

		if (!cacheRoot.endsWith(File.separator))
			{
			cacheRoot += File.separator;
			}
		}

	public static String getCacheRoot()
		{
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
	}
