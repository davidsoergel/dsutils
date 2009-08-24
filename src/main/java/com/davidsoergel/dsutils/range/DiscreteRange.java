package com.davidsoergel.dsutils.range;

import java.util.Set;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface DiscreteRange<T> extends Range<T>
	{
	Set<T> getValues();

	int size();
	}
