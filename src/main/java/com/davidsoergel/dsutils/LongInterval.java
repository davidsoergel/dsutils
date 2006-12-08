package com.davidsoergel.dsutils;

/**
 * @author lorax
 * @version 1.0
 */
public interface LongInterval extends Interval
	{
	public Long getLeft();

	public Long getRight();

	public boolean isZeroWidth();

	}
