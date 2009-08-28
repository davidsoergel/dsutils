package com.davidsoergel.dsutils.range;


import com.davidsoergel.dsutils.EnumValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class EnumSetRange extends BasicSetRange<EnumValue>
	{
/*	public EnumSetRange(final Collection<EnumValue> values)
		{
		super(values);
		}*/

	public EnumSetRange(final Collection enumValues)
		{
		super(new HashSet<EnumValue>());
		for (Object s : enumValues)
			{
			if (s instanceof String)
				{
				values.add(new EnumValue((String) s));
				}
			else if (s instanceof EnumValue)
				{
				values.add((EnumValue) s);
				}
			else
				{
				throw new RangeRuntimeException(
						"EnumSetRange must be initialized with EnumValues or Strings, not " + s.getClass());
				}
			}
		}

	protected EnumSetRange create(final Collection<EnumValue> values)
		{
		return new EnumSetRange(values);
		}

	public Set<String> getStringValues()
		{
		Set<String> result = new HashSet<String>();
		for (EnumValue value : values)
			{
			result.add(value.toString());
			}
		return result;
		}
	}
