package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.StringMapper;
import com.davidsoergel.dsutils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
 */
public class IntegerArrayMapper extends StringMapper<Integer[]>
	{
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
		return StringUtils.join(value, ":");
		}
	}
