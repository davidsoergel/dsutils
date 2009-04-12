package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.DSStringUtils;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class StringArrayMapper extends StringMapper<String[]>
	{
	public Type[] basicTypes()
		{
		return new Type[]{
				String[].class
		};
		}

	public String[] parse(String s)
		{
		if (s == null || s.trim().equals("")) //s.trim().isEmpty())   // JDK 1.5 compatibility
			{
			return new String[0];
			}
		//List<Double> result = new ArrayList<String>();
		return s.split(",");
		}

	public String render(String[] value)
		{
		return DSStringUtils.join(value, ",");
		}
	}
