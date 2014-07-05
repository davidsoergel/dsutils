/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;

import org.jboss.aop.advice.annotation.assignability.AssignabilityAlgorithm;
import org.jboss.aop.advice.annotation.assignability.VariableHierarchy;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class TypeUtils
	{
	// -------------------------- STATIC METHODS --------------------------

	public static boolean isAssignableFrom(Type superType, @NotNull Type subType)
		{
		return AssignabilityAlgorithm.VARIABLE_TARGET.isAssignable(superType, subType, new VariableHierarchy());
		/*
		if(superType instanceof Class && subType instanceof Class)
			{
			return ((Class)superType).isAssignableFrom((Class)subType);
			}
		else if (superType instanceof Class && subType instanceof ParameterizedType)
			{
			return ((Class) superType).isAssignableFrom((Class)((ParameterizedType) subType).getRawType());
			}
		else if (superType instanceof ParameterizedType && subType instanceof Class)
			{
			((Class)subType).getGenericSuperclass()

			((Class) subType).getGenericInterfaces()


			if()
			return ((Class) superType).isAssignableFrom((Class) subType);
			}*/
		}
	}
