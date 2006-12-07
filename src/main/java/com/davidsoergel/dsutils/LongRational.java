package com.davidsoergel.dsutils;

/**
 * Created by IntelliJ IDEA.
 * User: soergel
 * Date: Dec 3, 2006
 * Time: 10:35:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class LongRational extends Number implements Comparable
	{
	long numerator;
	long denominator;


	public LongRational(long numerator, long denominator)
		{
		this.numerator = numerator;
		this.denominator = denominator;
		reduce();
		}

	private void reduce()
		{
		long gcd = MathUtils.GCD(numerator, denominator);
		numerator /= gcd;
		denominator /= gcd;
		}

	/**
	 * Standard comparison method, with the caveat that LongRationals that differ by less than the resolution of double way not be correctly ordered.
	 *
	 * @param o
	 * @return
	 */
	public int compareTo(Object o)
		{
		LongRational lro = (LongRational) o;
		return new Double(doubleValue()).compareTo(lro.doubleValue());
		//return (numerator / denominator) (lro.numerator / lro.denominator);
		}

	public static LongRational mediant(LongRational a, LongRational b)
		{
		return new LongRational(a.numerator + b.numerator, a.denominator + b.denominator);
		}

	public int intValue()
		{
		return (int) doubleValue();
		}

	public long longValue()
		{
		return (long) doubleValue();
		}

	public float floatValue()
		{
		return (float) doubleValue();
		}

	public double doubleValue()
		{
		return (double) numerator
				/ (double) denominator;  //To change body of implemented methods use File | Settings | File Templates.
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

		LongRational that = (LongRational) o;

		// note this depends on both LongRationals already being reduced, but that's OK since it's in the constructor
		if (denominator != that.denominator)
			{
			return false;
			}
		if (numerator != that.numerator)
			{
			return false;
			}

		return true;
		}

	public int hashCode()
		{
		int result;
		result = (int) (numerator ^ (numerator >>> 32));
		result = 31 * result + (int) (denominator ^ (denominator >>> 32));
		return result;
		}

	public String toString()
		{
		return "" + numerator + "/" + denominator;
		}
	}
