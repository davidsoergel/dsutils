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

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Handles mapping Java objects to Strings and back.  Intended for short, human-readable Strings (unlike the
 * Serializable infrastructure, or an XML mapping, etc.  Hence, limited to simple types.
 */
public class TypedValueStringMapper extends HashMap<Type, StringMapper>
	{
	private static final Logger logger = Logger.getLogger(TypedValueStringMapper.class);

	static TypedValueStringMapper _instance = new TypedValueStringMapper().init();

	public static StringMapper get(Type c) throws StringMapperException
		{
		StringMapper stringMapper = ((HashMap<Type, StringMapper>) _instance).get(c);
		if (stringMapper == null)
			{
			for (Type t : _instance.keySet())
				{
				// this doesn't find the closest match, just the first
				if (TypeUtils.isAssignableFrom(t, c))
					{
					stringMapper = ((HashMap<Type, StringMapper>) _instance).get(t);
					}
				}
			}
		if (stringMapper == null)
			{
			stringMapper = ((HashMap<Type, StringMapper>) _instance).get(Class.class);
			//			throw new StringMapperException("No String Mapper for type: " + c);
			}
		return stringMapper;
		}

	public TypedValueStringMapper init()
		{
		try
			{
			for (Class c : SubclassFinder.find("com.davidsoergel.dsutils.stringmapper", StringMapper.class))
				{
				if (TypeUtils.isAssignableFrom(StringMapper.class, c))
					{
					ParameterizedType pt = (ParameterizedType) c.getGenericSuperclass();
					assert pt.getRawType().equals(StringMapper.class);
					Type[] types = pt.getActualTypeArguments();
					Type t = types[0];
					StringMapper sm = (StringMapper) (c.newInstance());
					put(t, sm);

					t = ClassUtils.wrapperToPrimitive(t);
					if (t != null)
						{
						put(t, sm);
						}
					}
				}
			}
		catch (IllegalAccessException e)
			{
			logger.debug(e);
			e.printStackTrace();
			throw new Error(e);
			}
		catch (InstantiationException e)
			{
			logger.debug(e);
			e.printStackTrace();
			throw new Error(e);
			}
		catch (IOException e)
			{
			logger.debug(e);
			e.printStackTrace();
			throw new Error(e);
			}
		return this;
		}

	// -------------------------- STATIC METHODS --------------------------

	public Object parse(Type type, String s) throws StringMapperException
		{
		StringMapper sm = get(type);
		if (sm != null)
			{
			return sm.parse(s);
			}

		if (((Class) type).isEnum())
			{
			Object result = Enum.valueOf((Class) type, s);
			if (result == null)
				{
				throw new StringMapperException("Enum value " + s + " does not exist in" + type);
				}
			return result;
			}
		/*else if (type instanceof ParameterizedType)
		{
		return get(Class.class).parse(s);
		}*/
		else
			{
			// see if the string names a class that is assignable to the given type
			Class c = (Class) (get(Class.class).parse(s));
			if (TypeUtils.isAssignableFrom(type, c))//s(Class) type).isAssignableFrom(c))
				{
				return c;
				}
			}
		throw new StringMapperException("Don't know how to parse a string into type " + type);
		}

	/*
	 {
		 if (s == null || s.equals(""))
			 {
			 return null;
			 }
		 else if (type == String[].class)
			 {
			 return parseStringArray(s);
			 }
		 else if (type == Double[].class)
			 {
			 return parseDoubleArray(s);
			 }
		 else if (type == Integer[].class)
			 {
			 return parseIntegerArray(s);
			 }
		 else if (type == Boolean[].class)
			 {
			 return parseBooleanArray(s);
			 }
		 else if (type == String.class || TypeUtils.isAssignableFrom(PluginMap.class, type))
			 {
			 return parseString(s);
			 }
		 else if (type == Double.class || type == double.class)
			 {
			 return parseDouble(s);
			 }
		 else if (type == Integer.class || type == int.class)
			 {
			 return parseInteger(s);
			 }
		 else if (type == Boolean.class || type == boolean.class)
			 {
			 return parseBoolean(s);
			 }
		 else if (type instanceof ParameterizedType || type == Class.class)
			 {
			 try
				 {
				 return parseClass(s);
				 }
			 catch (ClassNotFoundException e)
				 {
				 throw new StringParseException(e, "Don't know how to parse a string into type " + type);
				 }
			 }
		 else if (((Class) type).isEnum())
			 {
			 Object result = Enum.valueOf((Class) type, s);
			 if (result == null)
				 {
				 throw new StringParseException("Enum value " + s + " does not exist in" + type);
				 }
			 return result;
			 }
		 else
			 //if (type instanceof Class)
			 // see if the string names a class that is assignable to the given type
			 {
			 try
				 {
				 Class c = parseClass(s);
				 if (((Class) type).isAssignableFrom(c))
					 {
					 return c;
					 }
				 }
			 catch (ClassNotFoundException e)
				 {
				 throw new StringParseException(e, "Class not found: " + s + " (needed a " + type + ")");
				 }
			 }
		 throw new StringParseException("Don't know how to parse a string into type " + type);
		 }
 */
	}
