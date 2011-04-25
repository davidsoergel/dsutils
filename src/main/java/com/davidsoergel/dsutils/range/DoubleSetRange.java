package com.davidsoergel.dsutils.range;


import org.jetbrains.annotations.NotNull;

import java.util.Collection;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class DoubleSetRange extends AbstractSetRange<Double>
	{
	// for Hessian

	protected DoubleSetRange()
		{
		}

	public DoubleSetRange(final Collection<Double> values)
		{
		super(values);
		}

	@NotNull
	protected DoubleSetRange create(final Collection<Double> values)
		{
		return new DoubleSetRange(values);
		}
	}
