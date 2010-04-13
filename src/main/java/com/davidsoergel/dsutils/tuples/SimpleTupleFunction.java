package com.davidsoergel.dsutils.tuples;

import com.google.common.base.Function;

import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SimpleTupleFunction implements TupleFunction
	{
	List<String> fromDimensions;
	List<String> toDimensions;
	Function<Object[], Object[]> function;

	public SimpleTupleFunction(final List<String> fromDimensions, final List<String> toDimensions,
	                           final Function<Object[], Object[]> function)
		{
		this.fromDimensions = fromDimensions;
		this.function = function;
		this.toDimensions = toDimensions;
		}

	protected SimpleTupleFunction()
		{
		}

	public List<String> getFromDimensions()
		{
		return fromDimensions;
		}

	public Function<Object[], Object[]> getFunction()
		{
		return function;
		}

	public List<String> getToDimensions()
		{
		return toDimensions;
		}
	}
