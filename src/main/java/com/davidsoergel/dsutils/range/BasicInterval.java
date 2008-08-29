/*
 * Copyright (c) 2001-2008 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * dev@davidsoergel.com
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the names of any contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package com.davidsoergel.dsutils.range;

import org.apache.log4j.Logger;

/**
 * @author lorax
 * @version 1.0
 */
public class BasicInterval<T extends Number> implements Interval<T>
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(BasicInterval.class);

	protected T left, right;
	boolean closedLeft, closedRight;

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

	public boolean equals(Object o)
		{
		if (this == o)
			{
			return true;
			}
		if (o == null || getClass() != o.getClass())
			{
			return false;
			}

		BasicInterval<T> that = (BasicInterval<T>) o;

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

	public String toString()
		{
		return (closedLeft ? "[" : "(") + left + "," + right + (closedRight ? "]" : ")");
		}


	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface Comparable ---------------------

	public int compareTo(Interval<T> o)
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
