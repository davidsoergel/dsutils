package com.davidsoergel.dsutils.collections;

/**
 * Represent a pair of keys, guaranteeing that node1 <= node2 for the sake of symmetry
 */
public class UnorderedPair<K> implements Comparable<UnorderedPair<K>>
	{
	final private K key1;
	final private K key2;

	UnorderedPair(K key1, K key2)
		{
		if (key1.hashCode() <= key2.hashCode())
			//if (node1.getValue().compareTo(node2.getValue()) <= 0)
			{
			this.key1 = key1;
			this.key2 = key2;
			}
		else
			{
			this.key1 = key2;
			this.key2 = key1;
			}
		}

	public boolean equals(Object o)
		{
		if (this == o)
			{
			return true;
			}
		if (!(o instanceof UnorderedPair))
			{
			return false;
			}

		UnorderedPair keyPair = (UnorderedPair) o;

		if (key1 != null ? !key1.equals(keyPair.key1) : keyPair.key1 != null)
			{
			return false;
			}
		if (key2 != null ? !key2.equals(keyPair.key2) : keyPair.key2 != null)
			{
			return false;
			}

		return true;
		}

	public int hashCode()
		{
		int result;
		result = (key1 != null ? key1.hashCode() : 0);
		result = 31 * result + (key2 != null ? key2.hashCode() : 0);
		return result;
		}

	public K getKey1()
		{
		return key1;
		}

	public K getKey2()
		{
		return key2;
		}

	public int compareTo(UnorderedPair<K> o)
		{
		int h1 = hashCode();
		int h2 = o.hashCode();

		if (h1 < h2)
			{
			return -1;
			}
		if (h1 == h2)
			{
			return 0;
			}
		return 1;
//		return key1.toString().compareTo(o.toString());
		}

	public String toString()
		{
		return "[" + key1 + ", " + key2 + "]";
		}
	}
