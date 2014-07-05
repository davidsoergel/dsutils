/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.range;

import org.jetbrains.annotations.NotNull;

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

	@NotNull
	@Override
	protected AbstractSetRange<T> create(final Collection<T> values)
		{
		throw new UnsupportedOperationException();
		}
	}
