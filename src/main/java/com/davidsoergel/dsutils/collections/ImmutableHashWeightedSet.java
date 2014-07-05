/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class ImmutableHashWeightedSet<T> extends AbstractWeightedSet<T> implements Serializable
	{
	public ImmutableHashWeightedSet(@NotNull WeightedSet<T> orig)
		{
		backingMap = new ImmutableMap.Builder<T, Double>().putAll(orig.getMap()).build();
		itemCount = orig.getItemCount();
		}
	}

