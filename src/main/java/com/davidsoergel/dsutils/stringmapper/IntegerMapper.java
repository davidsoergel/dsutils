package com.davidsoergel.dsutils.stringmapper;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class IntegerMapper extends StringMapper<Integer>
	{
	public Type[] basicTypes()
		{
		return new Type[]{
				Integer.class,
				int.class
		};
		}

	public Integer parse(String s)
		{
		if (s == null || s.trim().isEmpty())
			{
			return null;
			}
		return new Integer(s);
		}

	public String render(Integer value)
		{
		return value.toString();
		}
	}
