package com.davidsoergel.dsutils.range;


import java.util.Collection;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class DoubleSetRange extends BasicSetRange<Double>
	{
	// for Hessian

	protected DoubleSetRange()
		{
		}

	public DoubleSetRange(final Collection<Double> values)
		{
		super(values);
		}

	protected DoubleSetRange create(final Collection<Double> values)
		{
		return new DoubleSetRange(values);
		}
	}
