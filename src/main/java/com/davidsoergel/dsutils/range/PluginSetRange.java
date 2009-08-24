package com.davidsoergel.dsutils.range;


import java.util.Collection;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class PluginSetRange extends BasicSetRange<String>
	{
	public PluginSetRange(final Collection<String> values)
		{
		super(values);
		}

	protected PluginSetRange create(final Collection<String> values)
		{
		return new PluginSetRange(values);
		}
	}
