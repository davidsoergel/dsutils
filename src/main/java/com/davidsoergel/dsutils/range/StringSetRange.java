package com.davidsoergel.dsutils.range;


import java.util.Collection;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class StringSetRange extends BasicSetRange<String>
	{

	// for Hessian

	protected StringSetRange()
		{
		}

	public StringSetRange(final Collection<String> values)
		{
		super(values);
		}

	protected StringSetRange create(final Collection<String> values)
		{
		return new StringSetRange(values);
		}
	}
