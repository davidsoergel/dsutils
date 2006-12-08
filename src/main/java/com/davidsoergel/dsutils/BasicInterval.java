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
	}
