package com.davidsoergel.dsutils.range;

import org.jetbrains.annotations.Nullable;

import java.util.Set;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface DiscreteRange<T> extends Range<T>
	{
	@Nullable
	Set<T> getValues();

	int size();
	}
