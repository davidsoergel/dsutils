/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.DSArrayUtils;
import com.davidsoergel.dsutils.DSStringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class DoublePrimitiveArrayMapper extends StringMapper<double[]>
	{
	@NotNull
	public Type[] basicTypes()
		{
		return new Type[]{double[].class};
		}

	public double[] parse(@NotNull String s)
		{
		@NotNull List<Double> result = new ArrayList<Double>();
		for (String d : s.split(","))
			{
			result.add(Double.parseDouble(d));
			}
		return DSArrayUtils.toPrimitive(result.toArray(new Double[]{}));
		}

	private static DecimalFormat df = new DecimalFormat("#.###");


	public String render(double[] value)
		{
		return DSStringUtils.join(value, ",");
		}

	public String renderAbbreviated(double[] value)
		{
		if (value.length == 0)
			{
			return "";
			}
		StringBuffer sb = new StringBuffer();

		for (Double d : value)
			{
			sb.append(df.format(d)).append(",");
			}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
		//return DSStringUtils.join(DSArrayUtils.toObject(value), ",");
		}

	public String renderHtml(@Nullable double[] ss)
		{
		if (ss == null || ss.length == 0)
			{
			return "";
			}


		@NotNull StringBuffer sb = new StringBuffer("<html>");
		sb.append("Array of ").append(ss.length).append(" Doubles:<P>");
		if (ss.length <= 20)
			{
			for (Double s : ss)
				{
				sb.append(String.format("%g", s)).append("<BR>");
				}
			}
		else
			{
			//Iterator<Integer> si = ss.iterator();
			for (int i = 0; i < ss.length; i++)
				{
				sb.append(String.format("%g  ", ss[i]));
				if (i % 10 == 0)
					{
					sb.append("<br>");
					}
				if (i == 400)
					{
					sb.append("...");
					break;
					}
				}
			}
		sb.append("</html>");
		return sb.toString();
		}
	}
