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

