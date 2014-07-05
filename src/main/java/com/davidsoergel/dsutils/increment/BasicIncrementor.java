/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.increment;

import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class BasicIncrementor extends Incrementor
	{
	private static final Logger logger = Logger.getLogger(BasicIncrementor.class);

	int i = 0;
	int max = 0;
	String note;

	public BasicIncrementor(final String clientName, final String initialNote)
		{
		super(clientName);
		note = initialNote;
		}

	public void increment()
		{
		i++;
		fireIncrementableUpdatedEvent(i, max);
		}

	public void done()
		{
		fireIncrementableUpdatedEvent(max, max);
		}

	public void setMaximum(int i)
		{
		max = i;
		fireIncrementableUpdatedEvent(i, max);
		}

	public void setNote(String n)
		{
		note = n;
		fireIncrementableNoteEvent(note);
		}

	public void incrementMaximum(int length)
		{
		max += length;
		fireIncrementableUpdatedEvent(i, max);
		}

	public int getCount()
		{
		return i;
		}

	public int getMax()
		{
		return max;
		}


	public void resetWithNote(String s)
		{
		i = 0;
		note = s;
		fireIncrementableUpdatedEvent(i, max);
		fireIncrementableNoteEvent(note);
		}
	}
