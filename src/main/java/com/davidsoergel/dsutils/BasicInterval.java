package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

/**
 * @author lorax
 * @version 1.0
 */
public class BasicInterval implements Interval
	{
	private static Logger logger = Logger.getLogger(BasicInterval.class);

	private Number left, right;


	public BasicInterval()
		{
		}

	public BasicInterval(Number left, Number right)
		{
		this.left = left;
		this.right = right;
		}

	public Number getLeft()
		{
		return left;
		}

	public void setLeft(Number left)
		{
		this.left = left;
		}

	public Number getRight()
		{
		return right;
		}

	public void setRight(Number right)
		{
		this.right = right;
		}

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

		BasicInterval that = (BasicInterval) o;

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

	/*public int compareTo(Object o)
		{
		return getLeft().compareTo((BasicInterval)o.getLeft());
		}*/
	}
