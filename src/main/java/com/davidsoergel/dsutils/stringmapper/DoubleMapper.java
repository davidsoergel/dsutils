package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.StringMapper;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
 */
public class DoubleMapper extends StringMapper<Double>
	{
	public Double parse(String s)
		{
		return new Double(s);
		}

	public String render(Double value)
		{
		return value.toString();
		}
	}
