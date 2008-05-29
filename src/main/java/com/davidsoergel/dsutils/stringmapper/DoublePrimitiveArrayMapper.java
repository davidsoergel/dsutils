package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.ArrayUtils;
import com.davidsoergel.dsutils.StringMapper;
import com.davidsoergel.dsutils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
 */
public class DoublePrimitiveArrayMapper extends StringMapper<double[]>
	{
	public double[] parse(String s)
		{
		List<Double> result = new ArrayList<Double>();
		for (String d : s.split(","))
			{
			result.add(Double.parseDouble(d));
			}
		return ArrayUtils.toPrimitive(result.toArray(new Double[]{}));
		}

	public String render(double[] value)
		{
		return StringUtils.join(Arrays.asList(value), ",");
		}
	}