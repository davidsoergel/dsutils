package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.DSStringUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class BooleanArrayMapper extends StringMapper<Boolean[]>
	{
	public Type[] basicTypes()
		{
		return new Type[]{
				Boolean[].class
		};
		}

	public Boolean[] parse(String s) throws StringMapperException
		{
		List<Boolean> result = new ArrayList<Boolean>();
		StringMapper<Boolean> booleanMapper = TypedValueStringMapper.get(Boolean.class);
		for (String d : s.split(":"))
			{
			result.add(booleanMapper.parse(d));
			}
		return result.toArray(new Boolean[]{});
		}

	public String render(Boolean[] value)
		{
		return DSStringUtils.join(value, ":");
		}
	}
