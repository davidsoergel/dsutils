/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SerializableOrderedPair<A extends Serializable, B extends Serializable> extends OrderedPair<A, B>
		implements Serializable
	{
	public SerializableOrderedPair(@Nullable final A key1, @Nullable final B key2)
		{
		super(key1, key2);
		}
	}
