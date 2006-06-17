/*
 * dsutils - a collection of generally useful utility classes
 *
 * Copyright (c) 2001-2006 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
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

package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Should we use org.apache.commons.lang.Pool instead?
 *
 * @author lorax
 * @version 1.0
 */
public class Pool<E>
	{
	private static Logger logger = Logger.getLogger(Pool.class);

	private CopyOnWriteArrayList<E> active = new CopyOnWriteArrayList<E>();
	private CopyOnWriteArrayList<E> inactive = new CopyOnWriteArrayList<E>();


	public void put(E obj)
		{
		// if the object being put wasn't part of the pool to start with, that's OK, but now it is.
		active.remove(obj);
		inactive.add(obj);
		}

	public E get()
		{
		E tmp;
		try
			{
			tmp = inactive.get(inactive.size() - 1); //inactive.getLast();
			}
		catch (IndexOutOfBoundsException e)
			{
			return null;
			}


		inactive.remove(tmp); //inactive.removeLast();
		active.add(tmp);
		return tmp;
		}

	public void addActive(E obj)
		{
		active.add(obj);
		}

	public void inactivateAll()
		{
		inactive.addAll(active);
		active.clear();
		}

	public List<E> getActive()
		{
		return active;
		}

	public List<E> getInactive()
		{

		return inactive;
		}

	}
