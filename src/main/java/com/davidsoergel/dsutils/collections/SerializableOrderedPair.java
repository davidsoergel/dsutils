package com.davidsoergel.dsutils.collections;

import java.io.Serializable;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SerializableOrderedPair<A extends Serializable, B extends Serializable> extends OrderedPair<A, B>
		implements Serializable
	{
	public SerializableOrderedPair(final A key1, final B key2)
		{
		super(key1, key2);
		}
	}
