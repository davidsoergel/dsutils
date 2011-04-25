package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.EnumValue;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id: EnumMapper.java 537 2009-10-15 01:10:06Z soergel $
 */
public class EnumMapper extends StringMapper<EnumValue>
	{
	private static final Logger logger = Logger.getLogger(EnumMapper.class);
	private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//ClassLoader.getSystemClassLoader();

	public static void setClassLoader(ClassLoader classLoader)
		{
		EnumMapper.classLoader = classLoader;
		}

	@NotNull
	public Type[] basicTypes()
		{
		return new Type[]{EnumValue.class};
		}

	public EnumMapper()
		{
		//super();
		}

	@NotNull
	public EnumValue parse(@Nullable String s) throws StringMapperException
		{
		if (s == null || s.trim().equals("")) //s.trim().isEmpty())   // JDK 1.5 compatibility
			{
			throw new StringMapperException("Empty Enum value");
			}
		//try
		//	{
		return new EnumValue(s);// Class.forName(s, true, classLoader);
		//	}
		//catch (ClassNotFoundException e)
		//	{
		//logger.error("Error", e);
		//	throw new StringMapperException(e);
		//	}
		}

	@NotNull
	public String render(@Nullable EnumValue value)
		{
		return value == null ? "null" : value.getValue(); //getCanonicalName();
		}
	}
