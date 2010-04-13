package com.davidsoergel.dsutils.tuples;

import com.google.common.base.Function;

import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface TupleFunction
	{
	List<String> getFromDimensions();

	List<String> getToDimensions();

	Function<Object[], Object[]> getFunction();
	}
