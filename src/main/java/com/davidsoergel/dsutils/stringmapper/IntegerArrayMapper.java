/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.DSStringUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class IntegerArrayMapper extends StringMapper<Integer[]>
	{
	@NotNull
	public Type[] basicTypes()
		{
		return new Type[]{Integer[].class};
		}

	public Integer[] parse(@NotNull String s)
		{
		@NotNull List<Integer> result = new ArrayList<Integer>();
		for (@NotNull String d : s.split(","))
			{
			result.add(Integer.parseInt(d.trim()));
			}
		return result.toArray(new Integer[]{});
		}

	public String render(Integer[] value)
		{
		return DSStringUtils.join(value, ",");
		}
	}
