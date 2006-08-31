/*
 * Ashatech Ibex - A framework for rapid development of dynamic web applications
 *
 * Copyright (C) 2002 Asha Technologies
 * 1215 2nd Avenue, San Francisco, CA  94122
 * info@ashatech.com
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * USA.
 *
 */

/* $Id: WeakHashSet.java,v 1.1 2003/10/12 20:44:03 david Exp $ */

package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

import java.util.*;
import java.lang.ref.*;

/**
 * @author Ross Morgan-Linial
 * @author David Soergel
 * @version 1.0
 */
public class WeakHashSet implements Set {
    private static Logger logger = Logger.getLogger(WeakHashSet.class);

    HashSet _set = new HashSet();

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
    for (Iterator i = _set.iterator(); i.hasNext();) {
        WeakReference wr = (WeakReference) i.next();
        Object o = wr.get();
        if(o == null) { i.remove(); }
        else { result.add(o); }

    }
    return result;
    }

    public int size() {
        return _set.size();
    }

    public boolean isEmpty() {
        return _set.isEmpty();
    }

    public boolean contains(Object o) {
        return getReferentSet().contains(o);
    }

    public Iterator iterator() {
        return getReferentSet().iterator();
    }

    public Object[] toArray() {
        return getReferentSet().toArray();
    }

    public Object[] toArray(Object a[]) {
            // not implemented
        return null;
    }

    public boolean add(Object o) {
          if(!contains(o))
          {
              _set.add(new WeakReference(o));
              return true;
          }
                          return false;
    }

    public boolean remove(Object o) {
        boolean result = false;
     for (Iterator i = _set.iterator(); i.hasNext();) {
        WeakReference wr = (WeakReference) i.next();
        if(o.equals(wr.get())) { i.remove(); result = true; }

    }
              return result;
    }

    public boolean containsAll(Collection c) {
        Set rs = getReferentSet();
        for(Iterator i = c.iterator(); i.hasNext(); )
        {
        if(!rs.contains(i.next())) {return false; }
        }
        return true;
    }

    public boolean addAll(Collection c) {
        // not implemented
        return false;
    }

    public boolean retainAll(Collection c) {
        // not implemented
        return false;
    }

    public boolean removeAll(Collection c) {
        // not implemented
        return false;
    }

    public void clear() {
        _set.clear();
    }


}



