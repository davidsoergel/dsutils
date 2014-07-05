/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Save memory by storing only one object for each set of equal() objects.  This is sensible only if the objects are
 * immutable and equals() is legitimate.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class Dereplicator<T>
	{
	@NotNull
	private Map<T, T> objects = new HashMap<T, T>();

	@Nullable
	public T derep(@Nullable T o)
		{
		if (o == null)
			{
			return null;
			}
		@Nullable T result = objects.get(o);
		if (result == null)
			{
			objects.put(o, o);
			result = o;
			}
		return result;
		}
	}
