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


package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An object pool, to allow reusing objects that are expensive to instantiate.
 * <p/>
 * Should we use org.apache.commons.lang.Pool instead?  Or that high-performance realtime library thing?  Or nothing,
 * because object pooling is now Considered Harmful?
 *
 * @version $Id$
 * @Deprecated
 */
public class Pool<E>
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(Pool.class);

	private CopyOnWriteArrayList<E> active = new CopyOnWriteArrayList<E>();
	private CopyOnWriteArrayList<E> inactive = new CopyOnWriteArrayList<E>();


	// --------------------- GETTER / SETTER METHODS ---------------------

	public List<E> getActive()
		{
		return active;
		}

	public List<E> getInactive()
		{
		return inactive;
		}

	// -------------------------- OTHER METHODS --------------------------

	public void addActive(E obj)
		{
		active.add(obj);
		}

	public E get()
		{
		E tmp;
		try
			{
			tmp = inactive.get(inactive.size() - 1);//inactive.getLast();
			}
		catch (IndexOutOfBoundsException e)
			{
			return null;
			}


		inactive.remove(tmp);//inactive.removeLast();
		active.add(tmp);
		return tmp;
		}

	public void inactivateAll()
		{
		inactive.addAll(active);
		active.clear();
		}

	public void put(E obj)
		{
		// if the object being put wasn't part of the pool to start with, that's OK, but now it is.
		active.remove(obj);
		inactive.add(obj);
		}
	}
