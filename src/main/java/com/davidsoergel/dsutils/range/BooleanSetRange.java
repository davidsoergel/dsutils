/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.range;


import org.jetbrains.annotations.NotNull;

import java.util.Collection;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class BooleanSetRange extends AbstractSetRange<Boolean>
	{
	// for Hessian

	protected BooleanSetRange()
		{
		}

	public BooleanSetRange(final Collection<Boolean> values)
		{
		super(values);
		}

	@NotNull
	protected AbstractSetRange<Boolean> create(final Collection<Boolean> values)
		{
		return new BooleanSetRange(values);
		}
	}
