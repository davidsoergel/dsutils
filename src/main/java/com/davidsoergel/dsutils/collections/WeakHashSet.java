/*
 * Copyright (c) 2001-2008 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * dev@davidsoergel.com
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the names of any contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package com.davidsoergel.dsutils.collections;

import org.apache.log4j.Logger;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Ross Morgan-Linial
 * @author David Soergel
 * @version 1.0
 */
public class WeakHashSet implements Set
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(WeakHashSet.class);

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

	public boolean remove(Object o)
		{
		boolean result = false;
		for (Iterator i = _set.iterator(); i.hasNext();)
			{
			WeakReference wr = (WeakReference) i.next();
			if (o.equals(wr.get()))
				{
				i.remove();
				result = true;
				}
			}
		return result;
		}

	public boolean containsAll(Collection c)
		{
		Set rs = getReferentSet();
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

	private Set getReferentSet()
		{
		HashSet result = new HashSet();
		for (Iterator i = _set.iterator(); i.hasNext();)
			{
			WeakReference wr = (WeakReference) i.next();
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



