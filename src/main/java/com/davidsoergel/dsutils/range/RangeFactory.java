/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.range;

import com.davidsoergel.dsutils.collections.DSCollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.net.URL;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class RangeFactory
	{
	public static SerializableRange<?> createBasicSetRange(@NotNull final Object val)
		{
		SerializableRange<?> result;
		if (val instanceof Boolean)
			{
			result = new BooleanSetRange(DSCollectionUtils.setOf((Boolean) val));
			}
		else if (val instanceof Double)
			{
			result = new DoubleSetRange(DSCollectionUtils.setOf((Double) val));
			}
		else if (val instanceof Integer)
			{
			result = new IntegerSetRange(DSCollectionUtils.setOf((Integer) val));
			}
		else if (val instanceof String)
			{
			result = new StringSetRange(DSCollectionUtils.setOf((String) val));
			}
		else if (val instanceof URL)
			{
			result = new URLSetRange(DSCollectionUtils.setOf((URL) val));
			}
		else
			{
			throw new RangeRuntimeException("Can't make a range of type " + val.getClass());
			}

		return result;
		}
	}
