/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class DSClassUtils extends org.apache.commons.lang.ClassUtils
	{

	// -------------------------- STATIC METHODS --------------------------

	public static Class[] getClasses(@NotNull Object[] objects)
		{
		@NotNull List<Class> result = new ArrayList<Class>();
		for (@NotNull Object o : objects)
			{
			/*if (o == null)
				{
				result.add(Object.class);
				}
			else
				{*/
			result.add(o.getClass());
			//}
			}
		return result.toArray(new Class[]{});
		}

	public static Constructor findConstructor(@NotNull Class theClass, @NotNull Class[] paramClasses)
			throws NoSuchMethodException
		{
		// based on a comment at http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4651775

		// search for the required constructor
		Constructor[] constrs = theClass.getConstructors();
		for (int i = 0; i < constrs.length; i++)
			{
			Class[] paramTypes = constrs[i].getParameterTypes();


			if (paramTypes.length == paramClasses.length)
				{
				boolean candidate = true;
				for (int j = 0; j < paramTypes.length; j++)
					{
					// ClassUtils.isAssignable deals with autounboxing and nulls, but only in one direction
					// this should take care of the "widening" version, i.e. autoboxing
					if (paramTypes[j].isPrimitive())
						{
						paramTypes[j] = DSClassUtils.primitiveToWrapper(paramTypes[j]);
						}
					if (!DSClassUtils.isAssignable(paramClasses[j], paramTypes[j]))
						{
						candidate = false;
						}
					}
				if (candidate)
					{
					return constrs[i];
					}
				}
			}

		throw new NoSuchMethodException("" + theClass + " (" + DSStringUtils.join(paramClasses, ", ") + ")");
		}

	@NotNull
	private static Map<Type, Type> wrapperPrimitiveMap = new HashMap<Type, Type>();

	static
		{
		wrapperPrimitiveMap.put(Boolean.class, Boolean.TYPE);
		wrapperPrimitiveMap.put(Byte.class, Byte.TYPE);
		wrapperPrimitiveMap.put(Character.class, Character.TYPE);
		wrapperPrimitiveMap.put(Short.class, Short.TYPE);
		wrapperPrimitiveMap.put(Integer.class, Integer.TYPE);
		wrapperPrimitiveMap.put(Long.class, Long.TYPE);
		wrapperPrimitiveMap.put(Double.class, Double.TYPE);
		wrapperPrimitiveMap.put(Float.class, Float.TYPE);
		}

	public static Type wrapperToPrimitive(Type t)
		{
		/*		if(t instanceof Class && ((Class)t).isArray())
		   {

		   }*/
		return wrapperPrimitiveMap.get(t);
		}
	}
