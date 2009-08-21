package com.davidsoergel.dsutils.increment;

import org.apache.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class ConcurrentIncrementor extends Incrementor
	{
	private static final Logger logger = Logger.getLogger(ConcurrentIncrementor.class);

	AtomicInteger i = new AtomicInteger(0);
	AtomicInteger max = new AtomicInteger(0);
	String note;

	public ConcurrentIncrementor(final String clientName, final String initialNote)
		{
		super(clientName);
		note = initialNote;
		}

	public void increment()
		{
		int x = i.incrementAndGet();
		fireIncrementableUpdatedEvent(i.get(), max.get());
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
		synchronized (note)
			{
			note = n;
			}
		fireIncrementableNoteEvent(note);
		}

	public void incrementMaximum(int length)
		{
		int x = max.addAndGet(length);
		fireIncrementableUpdatedEvent(i.get(), max.get());
		}

	public int getCount()
		{
		return i.get();
		}


	public void resetWithNote(String s)
		{
		i.set(0);
		synchronized (note)
			{
			note = s;
			}
		fireIncrementableUpdatedEvent(i.get(), max.get());
		fireIncrementableNoteEvent(note);
		}


	public String getNote()
		{
		return note;
		}
	}
