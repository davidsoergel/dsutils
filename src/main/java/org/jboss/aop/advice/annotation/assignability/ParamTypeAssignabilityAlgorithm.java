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
package org.jboss.aop.advice.annotation.assignability;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
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
	public static <C, T> boolean isAssignable(ParameterizedType paramType, Type fromType, EqualityChecker<C, T> checker,
	                                          C caller, T checkerToken)
		{
		Class<?> fromRaw = null;
		ParameterizedType fromParamType = null;
		Class<?> desiredType = (Class<?>) paramType.getRawType();
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
				return true;// TODO With warning
				}
			}
		else if (fromType instanceof ParameterizedType)
			{
			fromParamType = (ParameterizedType) fromType;
			fromRaw = (Class<?>) fromParamType.getRawType();
			if (fromRaw == desiredType)
				{
				// compare arguments with arguments
				return checker.isSame(paramType.getActualTypeArguments(), fromParamType.getActualTypeArguments(),
				                      caller, checkerToken);
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
		Type[] arguments = ArgumentContextualizer.getContextualizedArguments(fromParamType, fromRaw, desiredType);
		if (arguments == null)
			{
			return true;// TODO with Warning
			}
		return checker.isSame(paramType.getActualTypeArguments(), arguments, caller, checkerToken);
		}

	// -------------------------- INNER CLASSES --------------------------

	/**
	 * Class responsible for telling whether two groups of arguments, used on two different instances of ParameterizedType,
	 * can be considered the same group of arguments.
	 *
	 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
	 * @param <T> this is a token that can be used to store information useful for the implementor.
	 */
	static abstract class EqualityChecker<C, T>
		{
		/**
		 * Indicates whether both argument list can be considered the same. This method is a facility that will invoke {@link
		 * #isSame(Type,Type,Object,Object)} for each argument of the lists. Both lists have the same length.
		 *
		 * @param arguments     list of arguments.
		 * @param fromArguments list of arguments that will be assigned to <code> arguments</code> only if this method returns
		 *                      <code>true </code>
		 * @param token         a token that may be needed by implementor as auxiliar
		 * @return <code>true</code> only if values of <code> fromArguments</code> list can be assigned to a list of
		 *         <code>arguments</code> type.
		 */
		protected boolean isSame(Type[] arguments, Type[] fromArguments, C caller, T token)
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