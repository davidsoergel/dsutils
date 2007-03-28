package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

/**
 * @author lorax
 * @version 1.0
 */
public class BasicLongRationalInterval extends BasicInterval<LongRational>
	{
	private static Logger logger = Logger.getLogger(BasicLongRationalInterval.class);

	private LongRational left, right;


	public BasicLongRationalInterval(LongRational left, LongRational right)
		{
		super(left, right);
		}

	public int compareTo(Interval<LongRational> o)
		{
		return left.compareTo(o.getLeft());
		}

	// assumes closed interval, i.e. includes endpoints
	public boolean encompassesValue(LongRational value)
		{

		return LongRational.overflowSafeCompare(left, value) <= 0
				&& LongRational.overflowSafeCompare(right, value) >= 0;
		}
	}
