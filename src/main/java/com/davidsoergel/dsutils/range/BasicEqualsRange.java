/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.range;

import com.davidsoergel.dsutils.collections.DSCollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class BasicEqualsRange<T> implements DiscreteRange<T>
	{
	private final T theObject;

	public BasicEqualsRange(final T theObject)
		{
		this.theObject = theObject;
		}

	@NotNull
	public Set<T> getValues()
		{
		return DSCollectionUtils.setOf(theObject);
		}

	public int size()
		{
		return 1;
		}

	public boolean encompassesValue(final T value)
		{
		return theObject.equals(value);
		}
	}
