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

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/**
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public enum AssignabilityAlgorithm
	{
		VARIABLE_TARGET()
				{
				protected AssignabilityAlgorithm getInverseAlgorithm()
					{
					return FROM_VARIABLE;
					}

				protected boolean isVariableOperationApplicable(Type type, Type fromType)
					{
					return type instanceof TypeVariable;
					}

				protected boolean assignValue(Type type, Type fromType, VariableHierarchy variableHierarchy)
					{
					VariableNode node = variableHierarchy.getVariableNode((TypeVariable) type);
					return node.assignValue(fromType);
					}

				protected boolean addBound(Type type, Type fromType, VariableHierarchy variableHierarchy)
					{
					VariableNode node = variableHierarchy.getVariableNode((TypeVariable) type);
					return node.addLowerBound(fromType);
					}
				},
		FROM_VARIABLE()
				{
				protected AssignabilityAlgorithm getInverseAlgorithm()
					{
					return VARIABLE_TARGET;
					}

				protected boolean isVariableOperationApplicable(Type type, Type fromType)
					{
					return fromType instanceof TypeVariable;
					}

				protected boolean assignValue(Type type, Type fromType, VariableHierarchy variableHierarchy)
					{
					VariableNode fromNode = variableHierarchy.getVariableNode((TypeVariable) fromType);
					return fromNode.addMaximumUpperBound(type);
					}

				protected boolean addBound(Type type, Type fromType, VariableHierarchy variableHierarchy)
					{
					VariableNode fromNode = variableHierarchy.getVariableNode((TypeVariable) fromType);
					return fromNode.addUpperBound(type);
					}
				};


	// ------------------------------ FIELDS ------------------------------

	//////////////////////////////////////////////////////////
	private static final ParamTypeAssignabilityAlgorithm.EqualityChecker<AssignabilityAlgorithm, VariableHierarchy> CHECKER =
			new ParamTypeAssignabilityAlgorithm.EqualityChecker<AssignabilityAlgorithm, VariableHierarchy>()
			{
			public boolean isSame(Type type, Type fromType, AssignabilityAlgorithm client,
			                      VariableHierarchy variableHierarchy)
				{
				if (client.isVariableOperationApplicable(type, fromType))
					{
					return client.assignValue(type, fromType, variableHierarchy);
					}
				if (type instanceof Class)
					{
					// ** Updated by David Soergel <lorax@lorax.org> 2007-09-07
					// return type.equals(fromType);
					Class fromClass;
					if (fromType instanceof Class)
						{
						fromClass = (Class) fromType;
						}
					else if (fromType instanceof ParameterizedType)
						{
						fromClass = (Class) (((ParameterizedType) fromType).getRawType());
						}
					else
						{
						return false;
						}

					return ((Class) type).isAssignableFrom(fromClass);
					}
				if (type instanceof ParameterizedType)
					{
					if (!(fromType instanceof ParameterizedType))
						{
						return false;
						}
					ParameterizedType fromParamType = (ParameterizedType) fromType;
					ParameterizedType paramType = (ParameterizedType) type;
					if (!isSame(paramType.getRawType(), fromParamType.getRawType(), client, variableHierarchy))
						{
						return false;
						}
					return isSame(paramType.getActualTypeArguments(), fromParamType.getActualTypeArguments(), client,
					              variableHierarchy);
					}
				if (type instanceof WildcardType)
					{
					Type[] upperBounds = ((WildcardType) type).getUpperBounds();
					Type[] lowerBounds = ((WildcardType) type).getLowerBounds();
					if (fromType instanceof WildcardType)
						{
						Type[] fromUpperBounds = ((WildcardType) fromType).getUpperBounds();
						outer:
						for (int i = 0; i < upperBounds.length; i++)
							{
							for (int j = 0; j < fromUpperBounds.length; j++)
								{
								if (client.isAssignable(upperBounds[i], fromUpperBounds[i], variableHierarchy))
									{
									continue outer;
									}
								}
							return false;
							}
						Type[] fromLowerBounds = ((WildcardType) fromType).getLowerBounds();
						outer:
						for (int i = 0; i < lowerBounds.length; i++)
							{
							for (int j = 0; j < fromLowerBounds.length; j++)
								{
								if (client.getInverseAlgorithm()
										.isAssignable(fromLowerBounds[i], lowerBounds[i], variableHierarchy))
									{
									continue outer;
									}
								}
							return false;
							}
						return true;
						}
					else
						{
						for (int i = 0; i < upperBounds.length; i++)
							{
							if (!client.isAssignable(upperBounds[i], fromType, variableHierarchy))
								{
								return false;
								}
							}
						for (int i = 0; i < lowerBounds.length; i++)
							{
							if (!client.getInverseAlgorithm().isAssignable(fromType, lowerBounds[i], variableHierarchy))
								{
								return false;
								}
							}
						return true;
						}
					}
				return true;
				}
			};


	// -------------------------- OTHER METHODS --------------------------

	protected abstract boolean assignValue(Type type, Type fromType, VariableHierarchy variableHierarchy);

	protected abstract AssignabilityAlgorithm getInverseAlgorithm();

	public boolean isAssignable(Type type, Type fromType, VariableHierarchy variableHierarchy)
		{
		// special case, check fromType
		if (fromType instanceof WildcardType)
			{
			return isAssignable(type, (WildcardType) fromType, variableHierarchy);
			}
		if (isVariableOperationApplicable(type, fromType))
			{
			return addBound(type, fromType, variableHierarchy);
			}
		if (type instanceof Class)
			{
			return isAssignable((Class<?>) type, fromType, variableHierarchy);
			}
		if (type instanceof ParameterizedType)
			{
			return isAssignable((ParameterizedType) type, fromType, variableHierarchy);
			}
		if (type instanceof WildcardType)
			{
			throw new RuntimeException("This comparison should never happen");
			}
		if (type instanceof TypeVariable)
			{
			return false;
			}
		else
			{
			return isAssignable((GenericArrayType) type, fromType, variableHierarchy);
			}
		}

	protected abstract boolean isVariableOperationApplicable(Type type, Type fromType);

	protected abstract boolean addBound(Type type, Type fromType, VariableHierarchy variableHierarchy);

	private boolean isAssignable(Type type, WildcardType fromWildcardType, VariableHierarchy variableHierarchy)
		{
		boolean boundOk = false;
		for (Type upperBound : fromWildcardType.getUpperBounds())
			{
			if (isAssignable(type, upperBound, variableHierarchy))
				{
				boundOk = true;
				break;
				}
			}
		if (!boundOk)
			{
			return false;
			}
		for (Type lowerBound : fromWildcardType.getLowerBounds())
			{
			if (isAssignable(type, lowerBound, variableHierarchy))
				{
				return true;
				}
			}
		return fromWildcardType.getLowerBounds().length == 0;
		}

	// is classType super of fromType?
	private boolean isAssignable(Class<?> classType, Type fromType, VariableHierarchy variableHierarchy)
		{
		if (fromType instanceof Class)
			{
			return classType.isAssignableFrom((Class<?>) fromType);
			}
		else if (fromType instanceof ParameterizedType)
			{
			return classType.isAssignableFrom((Class<?>) ((ParameterizedType) fromType).getRawType());
			}
		else if (fromType instanceof TypeVariable)
			{
			Type[] bounds = getConcreteBounds(fromType);
			boolean inside = false;
			for (int i = 0; i < bounds.length && !inside; i++)
				{
				if (bounds[i] instanceof Class)
					{
					if (classType.isAssignableFrom((Class<?>) bounds[i]))
						{
						inside = true;
						}
					}
				else
					{
					// bound must be a parameterized type
					if (classType.isAssignableFrom((Class<?>) ((ParameterizedType) bounds[i]).getRawType()))
						{
						inside = true;
						}
					}
				}
			return inside;
			}
		// type instanceof GenericArrayType (ommitting check for performance
		// reasons)
		if (classType == Object.class)
			{
			return true;
			}
		if (classType.isArray())
			{
			return isAssignable(classType.getComponentType(), ((GenericArrayType) fromType).getGenericComponentType(),
			                    variableHierarchy);
			}
		return false;
		}

	/**
	 * @param type
	 * @return
	 */
	public static Type[] getConcreteBounds(Type type)
		{
		TypeVariable current = (TypeVariable) type;
		Type[] bounds = current.getBounds();
		while (bounds.length == 1 && bounds[0] instanceof TypeVariable)
			{
			current = (TypeVariable) bounds[0];
			bounds = current.getBounds();
			}
		return bounds;
		}

	private boolean isAssignable(ParameterizedType paramType, Type fromType, VariableHierarchy variableHierarchy)
		{
		if (fromType instanceof TypeVariable)
			{
			Type[] concreteBounds = getConcreteBounds((TypeVariable) fromType);
			try
				{
				variableHierarchy.startRealBoundComparation();
				for (int i = 0; i < concreteBounds.length; i++)
					{
					if (isAssignable(paramType, concreteBounds[i], variableHierarchy))
						{
						return true;
						}
					}
				}
			finally
				{
				variableHierarchy.finishRealBoundComparation();
				}
			return false;
			}
		return ParamTypeAssignabilityAlgorithm.isAssignable(paramType, fromType, CHECKER, this, variableHierarchy);
		}

	private boolean isAssignable(GenericArrayType arrayType, Type fromType, VariableHierarchy variableHierarchy)
		{
		if (fromType instanceof Class)
			{
			Class<?> fromClass = (Class<?>) fromType;
			if (!fromClass.isArray())
				{
				return false;
				}
			return isAssignable(arrayType.getGenericComponentType(), fromClass.getComponentType(), variableHierarchy);
			}
		if (fromType instanceof GenericArrayType)
			{
			GenericArrayType fromArrayType = (GenericArrayType) fromType;
			return isAssignable(arrayType.getGenericComponentType(), fromArrayType.getGenericComponentType(),
			                    variableHierarchy);
			}
		return false;
		}
	}