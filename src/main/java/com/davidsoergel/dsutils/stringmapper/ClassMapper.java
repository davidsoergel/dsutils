/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.stringmapper;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class ClassMapper extends StringMapper<Class>
	{
	private static final Logger logger = Logger.getLogger(ClassMapper.class);
	private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//ClassLoader.getSystemClassLoader();

	public static void setClassLoader(ClassLoader classLoader)
		{
		ClassMapper.classLoader = classLoader;
		}

	public Type[] basicTypes()
		{
		return new Type[]{Class.class};
		}

	public ClassMapper()
		{
		//super();
		}

	public Class parse(@Nullable String s) throws StringMapperException
		{
		if (s == null || s.trim().equals("")) //s.trim().isEmpty())   // JDK 1.5 compatibility
			{
			throw new StringMapperException("Empty plugin class name");
			}
		try
			{
			return Class.forName(s, true, classLoader);
			}
		catch (ClassNotFoundException e)
			{
			//logger.error("Error", e);
			throw new StringMapperException(e);
			}
		}

	@NotNull
	public String render(@Nullable Class value)
		{
		return value == null ? "null" : value.getCanonicalName();
		}

	public String renderAbbreviated(Class value)
		{
		return value == null ? "null" : value.getSimpleName();
		}
	}
