package com.davidsoergel.dsutils;

/**
 * @author lorax
 * @version 1.0
 */
public interface LongRationalInterval extends Interval, Comparable
	{
	public LongRational getLeft();

	public LongRational getRight();

	public boolean isZeroWidth();

	}
