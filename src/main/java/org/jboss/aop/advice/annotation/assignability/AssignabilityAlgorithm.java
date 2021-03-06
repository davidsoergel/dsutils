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

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

/**
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * @version $Id$
 */
public enum AssignabilityAlgorithm
	{
		VARIABLE_TARGET()
				{
				@NotNull
				protected AssignabilityAlgorithm getInverseAlgorithm()
					{
					return FROM_VARIABLE;
					}

				protected boolean isVariableOperationApplicable(Type type, Type fromType)
					{
					return type instanceof TypeVariable;
					}

				protected boolean assignValue(Type type, @NotNull Type fromType,
				                              @NotNull VariableHierarchy variableHierarchy)
					{
					VariableNode node = variableHierarchy.getVariableNode((TypeVariable) type);
					return node.assignValue(fromType);
					}

				protected boolean addBound(Type type, @NotNull Type fromType,
				                           @NotNull VariableHierarchy variableHierarchy)
					{
					VariableNode node = variableHierarchy.getVariableNode((TypeVariable) type);
					return node.addLowerBound(fromType);
					}
				},
		FROM_VARIABLE()
				{
				@NotNull
				protected AssignabilityAlgorithm getInverseAlgorithm()
					{
					return VARIABLE_TARGET;
					}

				protected boolean isVariableOperationApplicable(Type type, Type fromType)
					{
					return fromType instanceof TypeVariable;
					}

				protected boolean assignValue(@NotNull Type type, Type fromType,
				                              @NotNull VariableHierarchy variableHierarchy)
					{
					VariableNode fromNode = variableHierarchy.getVariableNode((TypeVariable) fromType);
					return fromNode.addMaximumUpperBound(type);
					}

				protected boolean addBound(@NotNull Type type, Type fromType,
				                           @NotNull VariableHierarchy variableHierarchy)
					{
					VariableNode fromNode = variableHierarchy.getVariableNode((TypeVariable) fromType);
					return fromNode.addUpperBound(type);
					}
				};


	// ------------------------------ FIELDS ------------------------------

	//////////////////////////////////////////////////////////
	@NotNull
	private static final ParamTypeAssignabilityAlgorithm.EqualityChecker<AssignabilityAlgorithm, VariableHierarchy>
			CHECKER = new ParamTypeAssignabilityAlgorithm.EqualityChecker<AssignabilityAlgorithm, VariableHierarchy>()
	{
	public boolean isSame(@NotNull Type type, @NotNull Type fromType, @NotNull AssignabilityAlgorithm client,
	                      @NotNull VariableHierarchy variableHierarchy)
		{
		if (client.isVariableOperationApplicable(type, fromType))
			{
			return client.assignValue(type, fromType, variableHierarchy);
			}
		if (type instanceof Class)
			{
			// Updated by David Soergel <lorax@lorax.org> 2007-09-07
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
			@NotNull ParameterizedType fromParamType = (ParameterizedType) fromType;
			@NotNull ParameterizedType paramType = (ParameterizedType) type;
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

	@NotNull
	protected abstract AssignabilityAlgorithm getInverseAlgorithm();

	public boolean isAssignable(Type type, @NotNull Type fromType, @NotNull VariableHierarchy variableHierarchy)
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

	private boolean isAssignable(Type type, @NotNull WildcardType fromWildcardType,
	                             @NotNull VariableHierarchy variableHierarchy)
		{
		boolean boundOk = false;
		for (@NotNull Type upperBound : fromWildcardType.getUpperBounds())
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
		for (@NotNull Type lowerBound : fromWildcardType.getLowerBounds())
			{
			if (isAssignable(type, lowerBound, variableHierarchy))
				{
				return true;
				}
			}
		return fromWildcardType.getLowerBounds().length == 0;
		}

	// is classType super of fromType?
	private boolean isAssignable(@NotNull Class<?> classType, @NotNull Type fromType,
	                             VariableHierarchy variableHierarchy)
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
		@NotNull TypeVariable current = (TypeVariable) type;
		Type[] bounds = current.getBounds();
		while (bounds.length == 1 && bounds[0] instanceof TypeVariable)
			{
			current = (TypeVariable) bounds[0];
			bounds = current.getBounds();
			}
		return bounds;
		}

	private boolean isAssignable(@NotNull ParameterizedType paramType, Type fromType,
	                             @NotNull VariableHierarchy variableHierarchy)
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

	private boolean isAssignable(@NotNull GenericArrayType arrayType, Type fromType,
	                             @NotNull VariableHierarchy variableHierarchy)
		{
		if (fromType instanceof Class)
			{
			@NotNull Class<?> fromClass = (Class<?>) fromType;
			if (!fromClass.isArray())
				{
				return false;
				}
			return isAssignable(arrayType.getGenericComponentType(), fromClass.getComponentType(), variableHierarchy);
			}
		if (fromType instanceof GenericArrayType)
			{
			@NotNull GenericArrayType fromArrayType = (GenericArrayType) fromType;
			return isAssignable(arrayType.getGenericComponentType(), fromArrayType.getGenericComponentType(),
			                    variableHierarchy);
			}
		return false;
		}
	}
