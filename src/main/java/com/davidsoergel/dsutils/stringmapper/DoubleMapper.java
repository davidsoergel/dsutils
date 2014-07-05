/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.stringmapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.text.DecimalFormat;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class DoubleMapper extends StringMapper<Double>
	{
	@NotNull
	public Type[] basicTypes()
		{
		return new Type[]{Double.class, double.class};
		}

	@Nullable
	public Double parse(@Nullable String s)
		{
		if (s == null || s.trim().equals("")) //s.trim().isEmpty())   // JDK 1.5 compatibility
			{
			return null;
			}
		return new Double(s);
		}

	public String render(@Nullable Double value)
		{
		if (value == null)
			{
			return "";
			}
		return value.toString();
		}


	private static DecimalFormat df = new DecimalFormat("#.###");

	public String renderAbbreviated(Double value)
		{
		return df.format(value); //String.format("%.2g", value);
		}
	}
