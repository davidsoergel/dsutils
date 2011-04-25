package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.PluginValue;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class PluginMapper extends StringMapper<PluginValue>
	{
	private static final Logger logger = Logger.getLogger(PluginMapper.class);
	private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//ClassLoader.getSystemClassLoader();

	public static void setClassLoader(ClassLoader classLoader)
		{
		PluginMapper.classLoader = classLoader;
		}

	@NotNull
	public Type[] basicTypes()
		{
		return new Type[]{PluginValue.class};
		}

	public PluginMapper()
		{
		//super();
		}

	@NotNull
	public PluginValue parse(@Nullable String s) throws StringMapperException
		{
		if (s == null || s.trim().equals("")) //s.trim().isEmpty())   // JDK 1.5 compatibility
			{
			throw new StringMapperException("Empty plugin class name");
			}
		//try
		//	{
		return new PluginValue(s);// Class.forName(s, true, classLoader);
		//	}
		//catch (ClassNotFoundException e)
		//	{
		//logger.error("Error", e);
		//	throw new StringMapperException(e);
		//	}
		}

	@NotNull
	public String render(@Nullable PluginValue value)
		{
		return value == null ? "null" : value.getValue(); //getCanonicalName();
		}

	public String renderAbbreviated(@Nullable PluginValue value)
		{
		if (value == null)
			{
			return "null";
			}
		String fullName = value.getValue();
		return fullName.substring(fullName.lastIndexOf(".")); //getSimpleName();
		}
	}
