/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;

import com.davidsoergel.dsutils.collections.MutableWeightedSet;
import com.davidsoergel.dsutils.collections.WeightedSet;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface Labellable<T>
	{
	void doneLabelling();

	@NotNull
	WeightedSet<T> getImmutableWeightedLabels();

	@NotNull
	MutableWeightedSet<T> getMutableWeightedLabels();

	public int getItemCount();
	}
