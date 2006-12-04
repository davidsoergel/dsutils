package com.davidsoergel.dsutils;

/**
 * Created by IntelliJ IDEA.
 * User: soergel
 * Date: Dec 3, 2006
 * Time: 10:35:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class LongRational //implements Comparable
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
		long gcd = gcd(numerator, denominator);
		numerator /= gcd;
		denominator /= gcd;
		}

	/**
	 * greatest common divisor by Euclid's algorithm
	 * shamelessly stolen from http://www.idevelopment.info/data/Programming/data_structures/java/gcd/GCD.java
	 *
	 * @param m
	 * @param n
	 * @return
	 */
	public static long gcd(long m, long n)
		{
		if (m < n)
			{
			long t = m;
			m = n;
			n = t;
			}
		long r = m % n;
		if (r == 0)
			{
			return n;
			}
		else
			{
			return gcd(n, r);
			}
		}

/*	public int compareTo(Object o)
		{
		LongRational lro = (LongRational)o;
		return (numerator / denominator) (lro.numerator / lro.denominator);
		}
	*/

	public static LongRational mediant(LongRational a, LongRational b)
		{
		return new LongRational(a.numerator + b.numerator, a.denominator + b.denominator);
		}

	}
