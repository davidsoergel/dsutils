package com.davidsoergel.dsutils.range;


import java.util.Collection;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class EnumSetRange extends BasicSetRange<String>
	{
	public EnumSetRange(final Collection<String> values)
		{
		super(values);
		}

	protected EnumSetRange create(final Collection<String> values)
		{
		return new EnumSetRange(values);
		}
	}
