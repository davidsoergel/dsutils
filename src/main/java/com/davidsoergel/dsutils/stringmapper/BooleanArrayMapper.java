package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.StringMapper;
import com.davidsoergel.dsutils.StringMapperException;
import com.davidsoergel.dsutils.StringUtils;
import com.davidsoergel.dsutils.TypedValueStringMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
 */
public class BooleanArrayMapper extends StringMapper<Boolean[]>
	{
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
		return StringUtils.join(value, ":");
		}
	}
