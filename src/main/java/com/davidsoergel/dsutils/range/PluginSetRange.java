package com.davidsoergel.dsutils.range;


import com.davidsoergel.dsutils.PluginValue;

import java.util.Collection;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class PluginSetRange extends BasicSetRange<PluginValue>
	{
// for Hessian

	protected PluginSetRange()
		{
		}

	public PluginSetRange(final Collection enumValues)
		{
		super(new HashSet<PluginValue>());
		for (Object s : enumValues)
			{
			if (s instanceof String)
				{
				values.add(new PluginValue((String) s));
				}
			else if (s instanceof PluginValue)
				{
				values.add((PluginValue) s);
				}
			else
				{
				throw new RangeRuntimeException(
						"PluginSetRange must be initialized with PluginValues or Strings, not " + s.getClass());
				}
			}
		}

	protected PluginSetRange create(final Collection<PluginValue> values)
		{
		return new PluginSetRange(values);
		}

	public SortedSet<String> getStringValues()
		{
		SortedSet<String> result = new TreeSet<String>();
		for (PluginValue value : values)
			{
			result.add(value.toString());
			}
		return result;
		}
	}
