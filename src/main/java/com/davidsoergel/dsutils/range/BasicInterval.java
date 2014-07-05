/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.range;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @version $Id$
 */
public class BasicInterval<T extends Number & Comparable> implements Interval<T>
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(BasicInterval.class);

	protected final T left, right;
	final boolean closedLeft, closedRight;

	// --------------------------- CONSTRUCTORS ---------------------------

	/*	public BasicInterval()
		 {
		 }
 */

	public BasicInterval(T left, T right, boolean closedLeft, boolean closedRight)
		{
		this.left = left;
		this.right = right;
		this.closedLeft = closedLeft;
		this.closedRight = closedRight;
		}

	public boolean isClosedLeft()
		{
		return closedLeft;
		}

	public boolean isClosedRight()
		{
		return closedRight;
		}

	// ------------------------ CANONICAL METHODS ------------------------

	public boolean equals(@Nullable Object o)
		{
		if (this == o)
			{
			return true;
			}
		if (o == null || getClass() != o.getClass())
			{
			return false;
			}

		@NotNull BasicInterval<T> that = (BasicInterval<T>) o;

		if (closedLeft != that.closedLeft)
			{
			return false;
			}
		if (closedRight != that.closedRight)
			{
			return false;
			}
		if (left != null ? !left.equals(that.left) : that.left != null)
			{
			return false;
			}
		if (right != null ? !right.equals(that.right) : that.right != null)
			{
			return false;
			}

		return true;
		}

	public int hashCode()
		{
		int result;
		result = (left != null ? left.hashCode() : 0);
		result = 31 * result + (right != null ? right.hashCode() : 0);
		result = 31 * result + (closedLeft ? 1 : 0);
		result = 31 * result + (closedRight ? 1 : 0);
		return result;
		}

	@NotNull
	public String toString()
		{
		return (closedLeft ? "[" : "(") + left + "," + right + (closedRight ? "]" : ")");
		}


	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface Comparable ---------------------

	public int compareTo(@NotNull Interval<T> o)
		{
		// assume we're using Comparable Numbers; ClassCastException if not
		int result = ((Comparable) getMin()).compareTo(o.getMin());
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

	// --------------------- Interface Interval ---------------------


	public boolean encompassesValue(T value)
		{
		// too bad we can't easily generify this; we just assume the Numbers are Comparable.
		int leftCompare = ((Comparable) left).compareTo(value);
		int rightCompare = ((Comparable) right).compareTo(value);

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

	/*	public void setLeft(T left)
		 {
		 this.left = left;
		 }
 */
	public T getMax()
		{
		return right;
		}

	public T getMin()
		{
		return left;
		}

	// -------------------------- OTHER METHODS --------------------------

	/*	public void setRight(T right)
		 {
		 this.right = right;
		 }
 */

	public boolean isZeroWidth()
		{
		return right.equals(left);
		}
	}
