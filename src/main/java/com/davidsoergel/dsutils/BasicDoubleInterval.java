package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

/**
 * @author lorax
 * @version 1.0
 */
public class BasicDoubleInterval implements Interval
	{
	private static Logger logger = Logger.getLogger(BasicDoubleInterval.class);

	private Double left, right;


	public Double getLeft()
		{
		return left;
		}

	public void setLeft(Double left)
		{
		this.left = left;
		}

	public Double getRight()
		{
		return right;
		}

	public void setRight(Double right)
		{
		this.right = right;
		}
	}
