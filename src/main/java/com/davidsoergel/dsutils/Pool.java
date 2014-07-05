/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

	@NotNull
	private CopyOnWriteArrayList<E> active = new CopyOnWriteArrayList<E>();
	@NotNull
	private CopyOnWriteArrayList<E> inactive = new CopyOnWriteArrayList<E>();


	// --------------------- GETTER / SETTER METHODS ---------------------

	@NotNull
	public List<E> getActive()
		{
		return active;
		}

	@NotNull
	public List<E> getInactive()
		{
		return inactive;
		}

	// -------------------------- OTHER METHODS --------------------------

	public void addActive(E obj)
		{
		active.add(obj);
		}

	@Nullable
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
