package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

/**
 * @author lorax
 * @version 1.0
 */
public class BasicLongRationalInterval implements LongRationalInterval
	{
	private static Logger logger = Logger.getLogger(BasicLongRationalInterval.class);

	private LongRational left, right;


	public BasicLongRationalInterval(LongRational left, LongRational right)
		{
		this.left = left;
		this.right = right;
		}

	public LongRational getLeft()
		{
		return left;
		}

	public void setLeft(LongRational left)
		{
		this.left = left;
		}

	public LongRational getRight()
		{
		return right;
		}

	public void setRight(LongRational right)
		{
		this.right = right;
		}

	public boolean isZeroWidth()
		{
		return right.equals(left);
		}

	public int compareTo(Object o)
		{
		return left.compareTo(((LongRationalInterval) o).getLeft());
		}
	}
