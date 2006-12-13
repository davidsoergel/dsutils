package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

/**
 * @author lorax
 * @version 1.0
 */
public class BasicLongInterval implements LongInterval
	{
	private static Logger logger = Logger.getLogger(BasicLongInterval.class);

	private Long left, right;


	public Long getLeft()
		{
		return left;
		}

	public void setLeft(Long left)
		{
		this.left = left;
		}

	public Long getRight()
		{
		return right;
		}

	public void setRight(Long right)
		{
		this.right = right;
		}

	public boolean isZeroWidth()
		{
		return right == left;
		}

	public int compareTo(Object o)
		{
		return left.compareTo(((BasicLongInterval) o).getLeft());
		}
	}
