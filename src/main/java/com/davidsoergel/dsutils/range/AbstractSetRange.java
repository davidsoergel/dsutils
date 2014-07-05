/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.range;


import com.davidsoergel.dsutils.collections.DSCollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public abstract class AbstractSetRange<T extends Serializable> implements DiscreteRange<T>, SerializableRange<T>
	{
	@NotNull
	protected SortedSet<T> values = new TreeSet<T>();  // = null; //wanted final, but then Hessian can't deserialize it


	// --------------------------- CONSTRUCTORS ---------------------------

	// for Hessian

	protected AbstractSetRange()
		{
		}

	protected AbstractSetRange(Collection<T> newValues)
		{
		//values = new TreeSet<T>();
		values.addAll(newValues);
		}

	@NotNull
	public SortedSet<T> getValues()
		{
		return values;
		}


	@NotNull
	public String toString()
		{
		return "{" + StringUtils.join(values.iterator(), ",") + "}";
		}

	// -------------------------- OTHER METHODS --------------------------

	@NotNull
	public AbstractSetRange<T> expandToInclude(T v)
		{
		@NotNull Set<T> newValues = new HashSet<T>(values.size() + 1);
		newValues.addAll(values);
		newValues.add(v);
		return create(newValues);
		}

	@NotNull
	public AbstractSetRange<T> expandToInclude(@NotNull DiscreteRange<T> v)
		{
		@NotNull Set<T> newValues = new HashSet<T>(values.size() + v.size());
		newValues.addAll(values);
		newValues.addAll(v.getValues());
		return create(newValues);
		}

	@NotNull
	protected abstract AbstractSetRange<T> create(final Collection<T> values);


	public int size()
		{
		return values.size();
		}

	public boolean encompassesValue(final T value)
		{
		return values.contains(value);
		}

	@Override
	public boolean equals(@Nullable final Object o)
		{
		if (this == o)
			{
			return true;
			}
		if (o == null || getClass() != o.getClass())
			{
			return false;
			}

		@NotNull final AbstractSetRange that = (AbstractSetRange) o;

		return DSCollectionUtils.isEqualCollection(values, that.values);
/*		if (values != null ? !values.equals(that.values) : that.values != null)
			{
			return false;
			}

		return true;*/
		}

	@Override
	public int hashCode()
		{
		return values != null ? values.hashCode() : 0;
		}
	}
