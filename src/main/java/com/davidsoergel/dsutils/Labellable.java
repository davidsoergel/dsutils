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
