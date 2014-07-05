/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.stringmapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class IntegerMapper extends StringMapper<Integer>
	{
	@NotNull
	public Type[] basicTypes()
		{
		return new Type[]{Integer.class, int.class};
		}

	@Nullable
	public Integer parse(@Nullable String s)
		{
		if (s == null || s.trim().equals("")) //s.trim().isEmpty())   // JDK 1.5 compatibility
			{
			return null;
			}
		return new Integer(s);
		}

	public String render(@NotNull Integer value)
		{
		return value.toString();
		}
	}
