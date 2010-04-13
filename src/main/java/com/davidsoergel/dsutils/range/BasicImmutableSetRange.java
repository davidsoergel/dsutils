package com.davidsoergel.dsutils.range;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class BasicImmutableSetRange<T extends Serializable> extends AbstractSetRange<T>
	{
	public BasicImmutableSetRange(final Collection<T> newValues)
		{
		super(newValues);
		}

	@Override
	protected AbstractSetRange<T> create(final Collection<T> values)
		{
		throw new UnsupportedOperationException();
		}
	}
