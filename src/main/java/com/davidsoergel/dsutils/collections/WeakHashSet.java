/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.collections;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Ross Morgan-Linial
 * @author David Soergel
 * @version $Id$
 */
public class WeakHashSet implements Set
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(WeakHashSet.class);

	@NotNull
	HashSet _set = new HashSet();


	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface Collection ---------------------

	public int size()
		{
		return _set.size();
		}

	public boolean isEmpty()
		{
		return _set.isEmpty();
		}

	public boolean contains(Object o)
		{
		return getReferentSet().contains(o);
		}

	public Object[] toArray()
		{
		return getReferentSet().toArray();
		}

	@Nullable
	public Object[] toArray(Object a[])
		{
		// not implemented
		return null;
		}

	public boolean add(Object o)
		{
		if (!contains(o))
			{
			_set.add(new WeakReference(o));
			return true;
			}
		return false;
		}

	public boolean remove(@NotNull Object o)
		{
		boolean result = false;
		for (Iterator i = _set.iterator(); i.hasNext();)
			{
			@NotNull WeakReference wr = (WeakReference) i.next();
			if (o.equals(wr.get()))
				{
				i.remove();
				result = true;
				}
			}
		return result;
		}

	public boolean containsAll(@NotNull Collection c)
		{
		@NotNull Set rs = getReferentSet();
		for (Iterator i = c.iterator(); i.hasNext();)
			{
			if (!rs.contains(i.next()))
				{
				return false;
				}
			}
		return true;
		}

	public boolean addAll(Collection c)
		{
		// not implemented
		return false;
		}

	public boolean retainAll(Collection c)
		{
		// not implemented
		return false;
		}

	public boolean removeAll(Collection c)
		{
		// not implemented
		return false;
		}

	public void clear()
		{
		_set.clear();
		}

	// --------------------- Interface Iterable ---------------------

	public Iterator iterator()
		{
		return getReferentSet().iterator();
		}

	// -------------------------- OTHER METHODS --------------------------

	/*   private void clearExpired()
		{
		for (Iterator i = new HashSet(_set).iterator(); i.hasNext();) {
			WeakReference o = (WeakReference) i.next();
			if(o.get() == null) { _set.remove(o); }

		}
		}   */

	@NotNull
	private Set getReferentSet()
		{
		@NotNull HashSet result = new HashSet();
		for (Iterator i = _set.iterator(); i.hasNext();)
			{
			@NotNull WeakReference wr = (WeakReference) i.next();
			Object o = wr.get();
			if (o == null)
				{
				i.remove();
				}
			else
				{
				result.add(o);
				}
			}
		return result;
		}
	}



