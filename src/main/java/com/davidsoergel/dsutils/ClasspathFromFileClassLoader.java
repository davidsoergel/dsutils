/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;


import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class ClasspathFromFileClassLoader// extends URLClassLoader
	{
	private static final Logger logger = Logger.getLogger(ClasspathFromFileClassLoader.class);

	private URL[] getURLs(File classpathFile) throws IOException
		{
		@NotNull List<URL> urls = new ArrayList<URL>();
		@NotNull BufferedReader is = new BufferedReader(new FileReader(classpathFile));

		try
			{
			String s = is.readLine();
			while (s != null)
				{
				if (!s.contains(":") && !s.trim().startsWith("#"))
					{
					if (!s.startsWith("/"))
						{
						throw new IOException("Classpath entries must be absolute: " + s);
						}
					if (!s.endsWith("/") && !s.endsWith(".jar"))
						{
						s = s + "/";
						}

					s = "file:" + s;

					@NotNull URL url = new URL(s);
					logger.debug("Adding classpath entry: " + url);
					urls.add(url);
					}

				s = is.readLine();
				}
			return urls.toArray(new URL[]{});
			}
		finally
			{
			is.close();
			}
		}

	public URLClassLoader getClassLoader(File classpathFile) throws IOException
		{
		return getClassLoader(getURLs(classpathFile));
		}

	protected URLClassLoader getClassLoader(final URL[] urls)
		{
		return (URLClassLoader) AccessController.doPrivileged(new PrivilegedAction()
		{
		@NotNull
		public Object run()
			{
			return new URLClassLoader(urls);
			}
		});
		}
	}
