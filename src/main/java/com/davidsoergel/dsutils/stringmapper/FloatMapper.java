package com.davidsoergel.dsutils.stringmapper;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class FloatMapper extends StringMapper<Float>
	{
	public Type[] basicTypes()
		{
		return new Type[]{
				Float.class,
				float.class
		};
		}

	public Float parse(String s)
		{
		if (s == null || s.trim().isEmpty())
			{
			return null;
			}
		return new Float(s);
		}

	public String render(Float value)
		{
		return value.toString();
		}
	}