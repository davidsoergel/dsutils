package com.davidsoergel.dsutils.collections;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SerializableOrderedPair<A extends Serializable, B extends Serializable> extends OrderedPair<A, B>
		implements Serializable
	{
	public SerializableOrderedPair(@NotNull final A key1, @NotNull final B key2)
		{
		super(key1, key2);
		}
	}
