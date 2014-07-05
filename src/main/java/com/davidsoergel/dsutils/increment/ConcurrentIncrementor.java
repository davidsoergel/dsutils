/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.increment;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class ConcurrentIncrementor extends Incrementor
	{
	private static final Logger logger = Logger.getLogger(ConcurrentIncrementor.class);

	@NotNull
	AtomicInteger i = new AtomicInteger(0);
	@NotNull
	AtomicInteger max = new AtomicInteger(0);
	String note;

	@NotNull
	final Boolean sync = false;

	public ConcurrentIncrementor(final String clientName, final String initialNote)
		{
		super(clientName);
		synchronized (sync)
			{
			note = initialNote;
			}
		}

	public void increment()
		{
		int x = i.incrementAndGet();
		fireIncrementableUpdatedEvent(x, max.get());
		}

	public void done()
		{
		//if (i.get() < max.get())
		//	{
		fireIncrementableUpdatedEvent(max.get(), max.get());
		//	}
		}

	public void setMaximum(int m)
		{
		max.set(m);
		fireIncrementableUpdatedEvent(i.get(), max.get());
		}

	public int getMax()
		{
		return max.get();
		}

	public void setNote(String n)
		{
		synchronized (sync)
			{
			note = n;
			}
		fireIncrementableNoteEvent(note);
		}

	public void incrementMaximum(int length)
		{
		int x = max.addAndGet(length);
		fireIncrementableUpdatedEvent(i.get(), x);
		}

	public int getCount()
		{
		return i.get();
		}


	public void resetWithNote(String s)
		{
		i.set(0);
		synchronized (sync)
			{
			note = s;
			}
		fireIncrementableUpdatedEvent(i.get(), max.get());
		fireIncrementableNoteEvent(note);
		}


	public String getNote()
		{
		synchronized (sync)
			{
			return note;
			}
		}
	}
