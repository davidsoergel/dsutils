package com.davidsoergel.dsutils.collections;

/**
 * Represent a pair of objects
 */
public class OrderedPair<A, B> implements Comparable<OrderedPair<A, B>>
	{
	private A key1;  // final, but that screws with Serializable
	private B key2;  // final, but that screws with Serializable

	public OrderedPair(A key1, B key2)
		{
		this.key1 = key1;
		this.key2 = key2;
		}

	public A getKey1()
		{
		return key1;
		}

	public B getKey2()
		{
		return key2;
		}

	@Override
	public boolean equals(final Object o)
		{
		if (this == o)
			{
			return true;
			}
		if (o == null || getClass() != o.getClass())
			{
			return false;
			}

		final OrderedPair that = (OrderedPair) o;

		if (key1 != null ? !key1.equals(that.key1) : that.key1 != null)
			{
			return false;
			}
		if (key2 != null ? !key2.equals(that.key2) : that.key2 != null)
			{
			return false;
			}

		return true;
		}

	@Override
	public int hashCode()
		{
		int result = key1 != null ? key1.hashCode() : 0;
		result = 31 * result + (key2 != null ? key2.hashCode() : 0);
		return result;
		}

	public int compareTo(final OrderedPair<A, B> o)
		{
		int c = key1.toString().compareTo(o.key1.toString());
		if (c == 0)
			{
			c = key2.toString().compareTo(o.key2.toString());
			}
		return c;
		}

	public String toString()
		{
		return "[" + key1 + ", " + key2 + "]";
		}
	}
