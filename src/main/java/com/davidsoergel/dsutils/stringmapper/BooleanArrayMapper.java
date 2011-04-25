package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.DSStringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class BooleanArrayMapper extends StringMapper<Boolean[]>
	{
	@NotNull
	public Type[] basicTypes()
		{
		return new Type[]{Boolean[].class};
		}

	@Nullable
	public Boolean[] parse(@Nullable String s) throws StringMapperException
		{
		if (s == null || s.trim().equals("")) //s.trim().isEmpty())   // JDK 1.5 compatibility
			{
			return null;
			}
		@NotNull List<Boolean> result = new ArrayList<Boolean>();
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
