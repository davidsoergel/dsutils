/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.aop.advice.annotation.assignability;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * @version $Id$
 */
class ParamTypeAssignabilityAlgorithm
	{
	// -------------------------- STATIC METHODS --------------------------

	/**
	 * @param <T>
	 * @param paramType
	 * @param fromType
	 * @param checker
	 * @param checkerToken
	 * @return
	 */
	public static <C, T> boolean isAssignable(@NotNull ParameterizedType paramType, Type fromType,
	                                          @NotNull EqualityChecker<C, T> checker, C caller, T checkerToken)
		{
		@Nullable Class<?> fromRaw = null;
		@Nullable ParameterizedType fromParamType = null;
		@NotNull Class<?> desiredType = (Class<?>) paramType.getRawType();
		if (fromType instanceof Class)
			{
			fromRaw = (Class<?>) fromType;
			if (!desiredType.isAssignableFrom(fromRaw))
				{
				return false;
				}
			if (fromRaw.getTypeParameters().length > 0)
				{
				// notice that, if fromRaw equals desiredType, we also have the
				// result true with a warning (i.e., this if statemente is not only for
				// fromRaw subclass of desiredType, but also for fromRaw same as
				// desiredType
				return true;// (ignore) TODO With warning
				}
			}
		else if (fromType instanceof ParameterizedType)
			{
			fromParamType = (ParameterizedType) fromType;
			fromRaw = (Class<?>) fromParamType.getRawType();
			if (fromRaw == desiredType)
				{
				// compare arguments with arguments
				return checker
						.isSame(paramType.getActualTypeArguments(), fromParamType.getActualTypeArguments(), caller,
						        checkerToken);
				}
			else if (!desiredType.isAssignableFrom(fromRaw))
				{
				return false;
				}
			}
		else
			{
			return false;
			}
		// try to get, if null, warning, parameters lost in hierarchy
		@Nullable Type[] arguments =
				ArgumentContextualizer.getContextualizedArguments(fromParamType, fromRaw, desiredType);
		if (arguments == null)
			{
			return true;// (ignore)  TODO with Warning
			}
		return checker.isSame(paramType.getActualTypeArguments(), arguments, caller, checkerToken);
		}

	// -------------------------- INNER CLASSES --------------------------

	/**
	 * Class responsible for telling whether two groups of arguments, used on two different instances of ParameterizedType,
	 * can be considered the same group of arguments.
	 *
	 * @param <T> this is a token that can be used to store information useful for the implementor.
	 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
	 */
	static abstract class EqualityChecker<C, T>
		{
		/**
		 * Indicates whether both argument list can be considered the same. This method is a facility that will invoke {@link
		 * #isSame(Type, Type, Object, Object)} for each argument of the lists. Both lists have the same length.
		 *
		 * @param arguments     list of arguments.
		 * @param fromArguments list of arguments that will be assigned to <code> arguments</code> only if this method returns
		 *                      <code>true </code>
		 * @param token         a token that may be needed by implementor as auxiliar
		 * @return <code>true</code> only if values of <code> fromArguments</code> list can be assigned to a list of
		 *         <code>arguments</code> type.
		 */
		protected boolean isSame(@NotNull Type[] arguments, Type[] fromArguments, C caller, T token)
			{
			for (int i = 0; i < arguments.length; i++)
				{
				if (!isSame(arguments[i], fromArguments[i], caller, token))
					{
					return false;
					}
				}
			return true;
			}

		/**
		 * Indicates whether both arguments can be considered the same. This method execution might require some variable
		 * inference process, in which case only a variable type referenced by <code>argument</code> can have its value
		 * infered according to the process.
		 *
		 * @param token        a token that may be needed by implementor as auxiliar
		 * @param argument     an argument type
		 * @param fromArgument argument type that will have its value assigned to <code>argument</code> only if this method
		 *                     returns <code>true</code>
		 * @return <code>true</code> only if a parameterized type with <code>argument</code> as one of its parameter values
		 *         can be assigned from the same parameterized type with <code>fromArgument</code> as the equivalent parameter
		 *         value
		 */
		abstract boolean isSame(Type argument, Type fromArgument, C caller, T token);
		}
	}
