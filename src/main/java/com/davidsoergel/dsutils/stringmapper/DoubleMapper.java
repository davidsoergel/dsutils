package com.davidsoergel.dsutils.stringmapper;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class DoubleMapper extends StringMapper<Double>
	{
	public Type[] basicTypes()
		{
		return new Type[]{
				Double.class,
				double.class
		};
		}

	public Double parse(String s)
		{
		if (s == null || s.trim().isEmpty())
			{
			return null;
			}
		return new Double(s);
		}

	public String render(Double value)
		{
		return value.toString();
		}

	public String renderAbbreviated(Double value)
		{
		return String.format("%.2g", value);
		}
	}
