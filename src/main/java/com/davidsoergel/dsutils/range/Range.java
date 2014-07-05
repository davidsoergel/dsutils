/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.range;


/**
 * Marker interface for classes that describe a range of values in one way or another.  "Range" is meant in a very
 * general sense here, including explicit sets, intervals, multi-intervals, etc.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 * @Author David Soergel
 * @Version 1.0
 */
public interface Range<T>
	{
	boolean encompassesValue(T value);
	}
