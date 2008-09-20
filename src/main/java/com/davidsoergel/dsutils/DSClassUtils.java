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

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author <a href="mailto:dev.davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class DSClassUtils extends org.apache.commons.lang.ClassUtils
	{

	// -------------------------- STATIC METHODS --------------------------

	public static Class[] getClasses(Object[] objects)
		{
		List<Class> result = new ArrayList<Class>();
		for (Object o : objects)
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

	public static Constructor findConstructor(Class theClass, Class[] paramClasses) throws NoSuchMethodException
		{
		// based on a comment at http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4651775

		Constructor constr = null;
		// search for the required constructor
		Constructor[] constrs = theClass.getConstructors();
		for (int i = 0; constr == null && i < constrs.length; i++)
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
