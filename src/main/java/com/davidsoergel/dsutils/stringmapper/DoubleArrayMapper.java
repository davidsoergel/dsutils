package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.DSStringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class DoubleArrayMapper extends StringMapper<Double[]>
	{
	public Type[] basicTypes()
		{
		return new Type[]{Double[].class};
		}

	public Double[] parse(String s)
		{
		List<Double> result = new ArrayList<Double>();
		for (String d : s.split(","))
			{
			if (!d.isEmpty())
				{
				result.add(Double.parseDouble(d));
				}
			}
		return result.toArray(new Double[]{});
		}

	public String render(Double[] value)
		{
		return DSStringUtils.join(value, ",");
		}
	}
