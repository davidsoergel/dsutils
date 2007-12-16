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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;


/**
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
class VariableNode
	{
	// ------------------------------ FIELDS ------------------------------

	private static ParamTypeAssignabilityAlgorithm.EqualityChecker<VariableNode, ?> CHECKER =
			new ParamTypeAssignabilityAlgorithm.EqualityChecker<VariableNode, Object>()
			{
			public boolean isSame(Type argument, Type fromArgument, VariableNode node, Object token)
				{
				return VariableNode.isSame(argument, fromArgument, false);
				}
			};
	private VariableHierarchy hierarchy;
	private VariableNode previous;
	private VariableNode next;
	private Collection<Type> lowerBounds;
	private Collection<Type> upperBounds;
	private TypeVariable variable;
	private Type assignedValue;


	// --------------------------- CONSTRUCTORS ---------------------------

	public VariableNode(TypeVariable content, VariableHierarchy hierarchy)
		{
		this.hierarchy = hierarchy;
		this.variable = content;
		this.lowerBounds = new HashSet<Type>();
		this.upperBounds = new HashSet<Type>();
		Type[] bounds = content.getBounds();
		if (bounds.length == 1 && bounds[0] instanceof TypeVariable)
			{
			TypeVariable typeVariable = (TypeVariable) bounds[0];
			next = hierarchy.getVariableNode(typeVariable);
			next.previous = this;
			}
		}

	// -------------------------- OTHER METHODS --------------------------

	public final boolean addLowerBound(Type lowerBound)
		{
		if (!isInsideUpperBounds(lowerBound, false) || (this.previous != null && !this.previous
				.areLowerBoundsInside(lowerBound, true)))
			{
			return false;
			}
		if (lowerBound instanceof TypeVariable)
			{
			Type[] bounds = ((TypeVariable) lowerBound).getBounds();
			this.lowerBounds.add(new ChoiceBound((TypeVariable) lowerBound, bounds));
			}
		else
			{
			this.lowerBounds.add(lowerBound);
			}
		return true;
		}

	public final boolean addMaximumUpperBound(Type upperBound)
		{
		if (!isAssignabilityPermited(upperBound))
			{
			return false;
			}
		for (Type oldUpperBound : upperBounds)
			{
			if (!isAssignable(upperBound, oldUpperBound))
				{
				return false;
				}
			}
		for (Type oldLowerBound : lowerBounds)
			{
			if (!isAssignable(upperBound, oldLowerBound))
				{
				return false;
				}
			}
		if (this.assignedValue != null && !isAssignable(upperBound, assignedValue))
			{
			return false;
			}
		if (this.next != null)
			{
			return this.next.addMaximumUpperBound(upperBound);
			}
		Type[] bounds = this.variable.getBounds();
		if (bounds.length == 1 && bounds[0] == Object.class)
			{
			return true;
			}
		else
			{
			for (Type bound : bounds)
				{
				if (!isAssignable(upperBound, bound))
					{
					return false;
					}
				}
			return true;
			}
		}

	// both type and bound belong to the same context
	private static boolean isAssignable(Type type, Type fromType)
		{
		if (fromType instanceof TypeVariable)
			{
			TypeVariable fromVariable = (TypeVariable) fromType;
			if (type instanceof TypeVariable)
				{
				if (type == fromType)
					{
					return true;
					}
				Type[] fromBounds = fromVariable.getBounds();
				while (fromBounds.length == 1 && fromBounds[0] instanceof TypeVariable)
					{
					if (fromBounds[0] == type)
						{
						return true;
						}
					fromVariable = (TypeVariable) fromBounds[0];
					fromBounds = fromVariable.getBounds();
					}
				return false;
				}
			Type[] fromBounds = AssignabilityAlgorithm.getConcreteBounds(fromVariable);
			for (Type fromBound : fromBounds)
				{
				if (isAssignable(type, fromBound))
					{
					return true;
					}
				}
			return false;
			}
		if (fromType instanceof ChoiceBound)
			{
			ChoiceBound fromChoiceBound = (ChoiceBound) fromType;
			if (type instanceof TypeVariable && !isAssignable((TypeVariable) type, fromChoiceBound.variable))
				{
				return false;
				}
			for (Iterator<Type> it = fromChoiceBound.bounds.iterator(); it.hasNext();)
				{
				Type fromOption = it.next();
				if (!isAssignable(type, fromOption))
					{
					it.remove();
					}
				}
			return !fromChoiceBound.bounds.isEmpty();
			}
		if (type instanceof Class)
			{
			if (type == Object.class)
				{
				return true;
				}
			Class<?> clazz = (Class<?>) type;
			if (fromType instanceof Class)
				{
				return clazz.isAssignableFrom((Class<?>) fromType);
				}
			if (fromType instanceof ParameterizedType)
				{
				return clazz.isAssignableFrom((Class<?>) ((ParameterizedType) fromType).getRawType());
				}
			if (fromType instanceof WildcardType)
				{
				WildcardType fromWildcard = (WildcardType) fromType;
				boolean boundOk = false;
				for (Type upperBound : fromWildcard.getUpperBounds())
					{
					if (isAssignable(type, upperBound))
						{
						boundOk = true;
						break;
						}
					}
				if (!boundOk)
					{
					return false;
					}
				for (Type lowerBound : fromWildcard.getLowerBounds())
					{
					if (isAssignable(type, lowerBound))
						{
						return true;
						}
					}
				return false;
				}
			if (fromType instanceof TypeVariable)
				{
				for (Type upperBound : ((TypeVariable) fromType).getBounds())
					{
					if (isAssignable(type, upperBound))
						{
						return true;
						}
					}
				return false;
				}
			return clazz.isArray() && isAssignable(clazz.getComponentType(),
			                                       ((GenericArrayType) fromType).getGenericComponentType());
			}
		if (type instanceof ParameterizedType)
			{
			return ParamTypeAssignabilityAlgorithm
					.isAssignable((ParameterizedType) type, fromType, CHECKER, null, null);
			}
		if (type instanceof TypeVariable)
			{
			for (Type bound : ((TypeVariable) type).getBounds())
				{
				if (!isAssignable(bound, fromType))
					{
					return false;
					}
				}
			return true;
			}
		if (type instanceof WildcardType)
			{
			WildcardType wildcard = (WildcardType) type;
			for (Type bound : wildcard.getUpperBounds())
				{
				if (!isAssignable(bound, fromType))
					{
					return false;
					}
				}
			for (Type bound : wildcard.getLowerBounds())
				{
				if (!isAssignable(bound, fromType))
					{
					return false;
					}
				}
			return true;
			}
		ChoiceBound choiceBound = (ChoiceBound) type;
		if (fromType instanceof TypeVariable && !isAssignable(choiceBound.variable, (TypeVariable) fromType))
			{
			return false;
			}
		for (Iterator<Type> it = choiceBound.bounds.iterator(); it.hasNext();)
			{
			Type boundOption = it.next();
			if (!isAssignable(boundOption, fromType))
				{
				it.remove();
				}
			}
		return !choiceBound.bounds.isEmpty();
		}

	public final boolean addUpperBound(Type upperBound)
		{
		if ((this.next != null && !this.next.isInsideUpperBounds(upperBound, true)) || !areLowerBoundsInside(upperBound,
		                                                                                                     false))
			{
			return false;
			}
		addToUpperBounds(upperBound);
		return true;
		}

	/**
	 * @param upperBound
	 */
	private void addToUpperBounds(Type upperBound)
		{
		if (upperBound instanceof TypeVariable)
			{
			Type[] bounds = ((TypeVariable) upperBound).getBounds();
			this.upperBounds.add(new ChoiceBound((TypeVariable) upperBound, bounds));
			}
		else
			{
			this.upperBounds.add(upperBound);
			}
		}

	public final boolean assignValue(Type value)
		{
		if (!isAssignabilityPermited(value))
			{
			return false;
			}

		if (this.assignedValue != null)
			{
			return isSame(value, this.assignedValue, true);
			}

		if (!isInsideUpperBounds(value, false) || !areLowerBoundsInside(value, false))
			{
			return false;
			}
		this.assignedValue = value;
		return true;
		}

	private boolean isAssignabilityPermited(Type value)
		{
		// assigned bounds can only be assigned to concrete types
		return !(((this.hierarchy.isBoundComparation() && !(value instanceof Class)
				&& !(value instanceof ParameterizedType))) || (
				// real bounds can have values assigned to variables
				(this.hierarchy.isRealBoundComparation() && (value instanceof WildcardType))));
		}

	private static boolean isSame(Type argument, Type fromArgument, boolean argumentAssigned)
		{
		if (argument instanceof WildcardType)
			{
			WildcardType wildcard = (WildcardType) argument;
			Type[] upperBounds = wildcard.getUpperBounds();
			Type[] lowerBounds = wildcard.getLowerBounds();
			if (fromArgument instanceof WildcardType)
				{
				WildcardType fromWildcard = (WildcardType) fromArgument;
				Type[] fromUpperBounds = fromWildcard.getUpperBounds();
				if (!isAssignable(upperBounds, fromUpperBounds))
					{
					return false;
					}

				Type[] fromLowerBounds = fromWildcard.getLowerBounds();
				outer:
				for (int i = 0; i < fromLowerBounds.length; i++)
					{
					for (int j = 0; j < lowerBounds.length; j++)
						{
						if (isAssignable(fromLowerBounds[i], lowerBounds[j]))
							{
							continue outer;
							}
						}
					return false;
					}
				return true;
				}
			if (argumentAssigned)
				{
				return false;
				}
			if (fromArgument instanceof TypeVariable)
				{
				if (!isAssignable(upperBounds, ((TypeVariable) fromArgument).getBounds()))
					{
					return false;
					}
				}
			else
				{
				for (int i = 0; i < upperBounds.length; i++)
					{
					if (!isAssignable(upperBounds[i], fromArgument))
						{
						return false;
						}
					}
				return true;
				}
			}
		else if (argument instanceof GenericArrayType)
			{
			if (fromArgument instanceof GenericArrayType)
				{
				return isSame(((GenericArrayType) argument).getGenericComponentType(),
				              ((GenericArrayType) fromArgument).getGenericComponentType(), argumentAssigned);
				}
			else
				{
				return false;
				}
			}
		return argument.equals(fromArgument);// TODO check this works correctly
		}

	private static boolean isAssignable(Type[] upperBounds, Type[] fromUpperBounds)
		{
		outer:
		for (int i = 0; i < upperBounds.length; i++)
			{
			for (int j = 0; j < fromUpperBounds.length; j++)
				{
				if (isAssignable(upperBounds[i], fromUpperBounds[j]))
					{
					continue outer;
					}
				}
			return false;
			}
		return true;
		}

	private boolean isInsideUpperBounds(Type lowerBound, boolean checkLowerBounds)
		{
		if (this.assignedValue != null && !isAssignable(lowerBound, assignedValue))
			{
			return false;
			}
		if (checkLowerBounds)
			{
			for (Type bound : this.lowerBounds)
				{
				if (!isAssignable(bound, lowerBound))
					{
					return false;
					}
				}
			}
		for (Type upperBound : upperBounds)
			{
			if (!isAssignable(upperBound, lowerBound))
				{
				return false;
				}
			}
		if (next == null)
			{
			Type[] bounds = variable.getBounds();
			this.hierarchy.startBoundComparation();
			try
				{
				for (int i = 0; i < bounds.length; i++)
					{
					if (!AssignabilityAlgorithm.VARIABLE_TARGET.isAssignable(bounds[i], lowerBound, hierarchy))
						{
						return false;
						}
					}
				}
			finally
				{
				this.hierarchy.finishBoundComparation();
				}
			return true;
			}
		return next.isInsideUpperBounds(lowerBound, true);
		}

	private boolean areLowerBoundsInside(Type bound, boolean checkUpperBounds)
		{
		if (this.assignedValue != null && !isAssignable(bound, assignedValue))
			{
			return false;
			}
		if (checkUpperBounds)
			{
			for (Type upperBound : this.upperBounds)
				{
				if (!isAssignable(bound, upperBound))
					{
					return false;
					}
				}
			}
		for (Type lowerBound : this.lowerBounds)
			{
			if (!isAssignable(bound, lowerBound))
				{
				return false;
				}
			}
		if (previous != null)
			{
			return previous.areLowerBoundsInside(bound, true);
			}
		return true;
		}
	}