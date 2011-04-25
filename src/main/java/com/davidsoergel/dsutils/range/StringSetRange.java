package com.davidsoergel.dsutils.range;


import org.jetbrains.annotations.NotNull;

import java.util.Collection;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class StringSetRange extends AbstractSetRange<String>
	{

	// for Hessian

	protected StringSetRange()
		{
		}

	public StringSetRange(final Collection<String> values)
		{
		super(values);
		}

	@NotNull
	protected StringSetRange create(final Collection<String> values)
		{
		return new StringSetRange(values);
		}
	}
