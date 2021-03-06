/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.DSStringUtils;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class DoubleArrayMapper extends StringMapper<Double[]>
	{
	@NotNull
	public Type[] basicTypes()
		{
		return new Type[]{Double[].class};
		}

	public Double[] parse(@NotNull String s)
		{
		@NotNull List<Double> result = new ArrayList<Double>();
		for (@NotNull String d : s.split(","))
			{
			if (!d.isEmpty())
				{
				result.add(Double.parseDouble(d));
				}
			}
		return result.toArray(new Double[]{});
		}

	private static DecimalFormat df = new DecimalFormat("#.###");

	public String render(Double[] value)
		{
		return DSStringUtils.join(value, ",");
		}

	public String renderAbbreviated(Double[] value)
		{
		StringBuffer sb = new StringBuffer();

		for (Double d : value)
			{
			sb.append(df.format(d)).append(",");
			}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();

		// DSStringUtils.join(value, ",");
		}
	}
