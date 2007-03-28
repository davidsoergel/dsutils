package com.davidsoergel.dsutils;

/**
 * Created by IntelliJ IDEA.
 * User: lorax
 * Date: Mar 28, 2007
 * Time: 10:36:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class MutableBasicInterval<T extends Number> extends BasicInterval<T>
	{
	public MutableBasicInterval()
		{
		super(null, null);
		}

	public void setRight(T right)
		{
		this.right = right;
		}

	public void setLeft(T left)
		{
		this.left = left;
		}
	}
