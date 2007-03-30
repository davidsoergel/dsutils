package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

/**
 * @author lorax
 * @version 1.0
 */
public class BasicInterval<T extends Number> implements Interval<T>
	{
	private static Logger logger = Logger.getLogger(BasicInterval.class);

	protected T left, right;


	/*	public BasicInterval()
		 {
		 }
 */
	public BasicInterval(T left, T right)
		{
		this.left = left;
		this.right = right;
		}

	public T getMin()
		{
		return left;
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

	public boolean encompassesValue(T value)
		{
		// too bad we can't easily generify this; we just assume the Numbers are Comparable.
		return ((Comparable) left).compareTo(value) <= 0 && ((Comparable) right).compareTo(value) >= 0;
		}

	/*	public void setRight(T right)
		 {
		 this.right = right;
		 }
 */
	public boolean isZeroWidth()
		{
		return right.equals(left);
		}

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
		return result;
		}

	public String toString()
		{
		return "" + left + " ---- " + right;
		}

	public int compareTo(Interval<T> o)
		{
		// assume we're using Comparable Numbers; ClassCastException if not
		return ((Comparable) getMin()).compareTo(o.getMin());
		}
	}
