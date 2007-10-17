/*
 * Copyright (c) 2001-2007 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
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
import java.util.ArrayList;
import java.util.List;

/* $Id$ */

/**
 * @Author David Soergel
 * @Version 1.0
 */
public class ClassUtils extends org.apache.commons.lang.ClassUtils
	{
	public static Class[] getClasses(Object[] objects)
		{
		List<Class> result = new ArrayList<Class>();
		for (Object o : objects)
			{
			result.add(o.getClass());
			}
		return result.toArray(new Class[]{});
		}

	public static Constructor findConstructor(Class theClass, Class[] paramClasses) throws NoSuchMethodException
		{
		// copied from http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4651775

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
					// either we have a parameter set and we can successfully check
					// or we have no parameter set, but this is only allowed for primitives
					if ((paramClasses[j] != null && !paramTypes[j].isAssignableFrom(paramClasses[j])) || (
							paramClasses[j] == null && paramTypes[j].isPrimitive()))
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

		throw new NoSuchMethodException("" + theClass + " (" + paramClasses + ")");
		}
	}
