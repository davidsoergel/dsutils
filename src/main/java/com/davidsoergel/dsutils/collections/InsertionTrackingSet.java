package com.davidsoergel.dsutils.collections;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;

/**
 * A Set that maintains a counter to associate the insertion order with each entry.  If an entry is removed, its index
 * stays the same.  Also provides rapid reverse lookup by index.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class InsertionTrackingSet<T> extends AbstractSet<T> implements Serializable
		//ArrayList<T>
	{
	BiMap<T, Integer> contents = HashBiMap.create();
	private int nextIndex = 0;


	public InsertionTrackingSet()
		{
		}

	public InsertionTrackingSet(Collection<T> c)
		{
		addAll(c);
		}

	public T get(final Integer index)
		{
		return contents.inverse().get(index);
		}

	/**
	 * Does not guarantee any particular iteration order
	 *
	 * @return
	 */
	public Iterator<T> iterator()
		{
		return contents.keySet().iterator();
		}

	public int size()
		{
		return contents.size();
		}

	@Override
	public boolean add(final T t)
		{
		try
			{
			contents.put(t, nextIndex);
			}
		catch (IllegalArgumentException e)
			{
			return false;
			}

		nextIndex++;
		return true;
		}


	public void put(final T t, final Integer index)
		{
		if (contents.containsKey(t) || contents.containsValue(index))
			{
			throw new IllegalArgumentException();
			}
		contents.put(t, index);
		nextIndex = Math.max(nextIndex, index + 1);
		}

	@Override
	public boolean remove(final Object o)
		{
		return contents.remove(o) != null;
		}

	public Integer indexOf(final T key1)
		{
		return contents.get(key1);
		}

	public Integer indexOfWithAdd(final T key1)
		{
		Integer result = indexOf(key1);
		if (result == null)
			{
			add(key1);
			result = nextIndex - 1;
			}
		return result;
		}

	public int getNextId()
		{
		return nextIndex;
		}
	}
