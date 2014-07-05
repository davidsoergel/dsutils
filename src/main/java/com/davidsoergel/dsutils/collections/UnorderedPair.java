/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * Represent a pair of keys, guaranteeing that node1 <= node2 for the sake of symmetry.  Note the contained objects must
 * be immutable for this to work right.
 */
public class UnorderedPair<K extends Comparable<K>> implements Comparable<UnorderedPair<K>>, Serializable
	{
	@NotNull
	final private K key1;
	@NotNull
	final private K key2;

	public UnorderedPair(@NotNull K key1, @NotNull K key2)
		{
		final boolean swap = key1.compareTo(key2) > 0;
		/*boolean swap = false;
		final int k1h = key1.hashCode();
		final int k2h = key2.hashCode();

		if (k1h > k2h)
			//if (node1.getValue().compareTo(node2.getValue()) <= 0)
			{
			swap = true;
			}
		else if (k1h == k2h && !key1.equals(key2))
			{
			swap = (key1.compareTo(key2) > 0);
			}
*/

		if (swap)
			{
			this.key1 = key2;
			this.key2 = key1;
			}
		else
			{
			this.key1 = key1;
			this.key2 = key2;
			}

		hashCode = makeHashCode();
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

		@NotNull UnorderedPair keyPair = (UnorderedPair) o;

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

	final int hashCode;

	public int hashCode()
		{
		return hashCode;
		}

	private int makeHashCode()
		{
		int result;
		result = (key1 != null ? key1.hashCode() : 0);
		result = 31 * result + (key2 != null ? key2.hashCode() : 0);
		return result;
		}

	public
	@NotNull
	K getKey1()
		{
		return key1;
		}

	public
	@NotNull
	K getKey2()
		{
		return key2;
		}

	public int compareTo(@NotNull final UnorderedPair<K> o)
		{
		int c = key1.compareTo(o.key1);
		if (c == 0)
			{
			c = key2.compareTo(o.key2);
			}
		return c;
		}
/*
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
		}*/

	@NotNull
	public String toString()
		{
		return "[" + key1 + ", " + key2 + "]";
		}
	}
