/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.range;

import com.davidsoergel.dsutils.math.LongRational;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * @version $Id$
 */
public class BasicLongRationalInterval extends BasicInterval<LongRational>
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(BasicLongRationalInterval.class);

	// --------------------------- CONSTRUCTORS ---------------------------

	public BasicLongRationalInterval(LongRational left, LongRational right, boolean closedLeft, boolean closedRight)
		{
		super(left, right, closedLeft, closedRight);
		}

	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface Comparable ---------------------

/*	public int compareTo(@NotNull Interval<LongRational> o)
		{
		// assume we're using Comparable Numbers; ClassCastException if not
		int result = left.compareTo(o.getMin());
		if (result == 0)
			{
			if (closedLeft && !o.isClosedLeft())
				{
				result = -1;
				}
			else if (!closedLeft && o.isClosedLeft())
				{
				result = 1;
				}
			}
		return result;
		}
*/

	// --------------------- Interface Interval ---------------------

	// assumes open interval, i.e. includes endpoints

	public boolean encompassesValue(@NotNull LongRational value)
		{
		int leftCompare = LongRational.overflowSafeCompare(left, value);
		int rightCompare = LongRational.overflowSafeCompare(right, value);

		if (closedLeft && leftCompare == 0)
			{
			return true;
			}
		if (closedRight && rightCompare == 0)
			{
			return true;
			}

		return leftCompare < 0 && rightCompare > 0;
		}
	}
