package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.DSArrayUtils;
import com.davidsoergel.dsutils.DSStringUtils;
import com.davidsoergel.dsutils.StringMapper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class DoublePrimitiveArrayMapper extends StringMapper<double[]>
	{
	public Type[] basicTypes()
		{
		return new Type[]{
				double[].class
		};
		}

	public double[] parse(String s)
		{
		List<Double> result = new ArrayList<Double>();
		for (String d : s.split(","))
			{
			result.add(Double.parseDouble(d));
			}
		return DSArrayUtils.toPrimitive(result.toArray(new Double[]{}));
		}

	public String render(double[] value)
		{
		return DSStringUtils.join(DSArrayUtils.toObject(value), ",");
		}
	}