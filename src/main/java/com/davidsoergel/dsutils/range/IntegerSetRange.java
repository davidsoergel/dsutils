package com.davidsoergel.dsutils.range;


import java.util.Collection;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class IntegerSetRange extends AbstractSetRange<Integer>
	{
	// for Hessian

	public IntegerSetRange()
		{
		}

	public IntegerSetRange(final Collection<Integer> values)
		{
		super(values);
		}

	protected IntegerSetRange create(final Collection<Integer> values)
		{
		return new IntegerSetRange(values);
		}
	}
