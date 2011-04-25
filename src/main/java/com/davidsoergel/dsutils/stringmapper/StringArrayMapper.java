package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.DSStringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class StringArrayMapper extends StringMapper<String[]>
	{
	@NotNull
	public Type[] basicTypes()
		{
		return new Type[]{String[].class};
		}

	public String[] parse(@Nullable String s)
		{
		if (s == null || s.trim().equals("")) //s.trim().isEmpty())   // JDK 1.5 compatibility
			{
			return new String[0];
			}
		//List<Double> result = new ArrayList<String>();
		String[] result = s.split(",");
		DSStringUtils.trimAll(result);
		return result;
		}

	public String render(String[] value)
		{
		return DSStringUtils.join(value, ", ");
		}
	}
