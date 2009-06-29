package com.davidsoergel.dsutils.stringmapper;

import org.apache.log4j.Logger;

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

	public Class parse(String s) throws StringMapperException
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

	public String render(Class value)
		{
		return value == null ? "null" : value.getCanonicalName();
		}

	public String renderAbbreviated(Class value)
		{
		return value == null ? "null" : value.getSimpleName();
		}
	}
