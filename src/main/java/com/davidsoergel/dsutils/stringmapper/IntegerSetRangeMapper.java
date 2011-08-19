package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.DSStringUtils;
import com.davidsoergel.dsutils.range.IntegerSetRange;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class IntegerSetRangeMapper extends StringMapper<IntegerSetRange>
	{
	@NotNull
	public Type[] basicTypes()
		{
		return new Type[]{IntegerSetRange.class};
		}

	@NotNull
	public IntegerSetRange parse(String s) throws StringMapperException
		{
		s = s.substring(1, s.length() - 1);
		String[] ss = s.split(",");
		@NotNull SortedSet<Integer> integers = new TreeSet<Integer>();
		for (String sd : ss)
			{
			integers.add(Integer.parseInt(sd));
			}
		@NotNull IntegerSetRange result = new IntegerSetRange(integers);
		return result;
		}

	@NotNull
	public String render(@NotNull IntegerSetRange value)
		{
		return "{" + DSStringUtils.join(value.getValues(), ", ") + "}";
		}
	}
