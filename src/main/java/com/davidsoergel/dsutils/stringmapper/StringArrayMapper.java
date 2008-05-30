package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.StringMapper;
import com.davidsoergel.dsutils.StringUtils;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
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
		//List<Double> result = new ArrayList<String>();
		return s.split(":");
		}

	public String render(String[] value)
		{
		return StringUtils.join(value, ":");
		}
	}
