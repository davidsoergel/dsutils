package com.davidsoergel.dsutils.range;


import java.util.Collection;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class BooleanSetRange extends AbstractSetRange<Boolean>
	{
	// for Hessian

	protected BooleanSetRange()
		{
		}

	public BooleanSetRange(final Collection<Boolean> values)
		{
		super(values);
		}

	protected AbstractSetRange<Boolean> create(final Collection<Boolean> values)
		{
		return new BooleanSetRange(values);
		}
	}
