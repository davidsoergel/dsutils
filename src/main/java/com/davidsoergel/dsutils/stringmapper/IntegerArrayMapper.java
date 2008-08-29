package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.DSStringUtils;
import com.davidsoergel.dsutils.StringMapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class IntegerArrayMapper extends StringMapper<Integer[]>
	{
	public Type[] basicTypes()
		{
		return new Type[]{
				Integer[].class
		};
		}

	public Integer[] parse(String s)
		{
		List<Integer> result = new ArrayList<Integer>();
		for (String d : s.split(":"))
			{
			result.add(Integer.parseInt(d));
			}
		return result.toArray(new Integer[]{});
		}

	public String render(Integer[] value)
		{
		return DSStringUtils.join(value, ":");
		}
	}
