/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import org.jetbrains.annotations.NotNull;

import java.util.BitSet;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class CircularBitField
	{
	// ------------------------------ FIELDS ------------------------------

	private BitSet buf;
	private int size;
	private int start;


	// --------------------------- CONSTRUCTORS ---------------------------

	public CircularBitField(int size)
		{
		this.size = size;
		buf = new BitSet(size);
		start = 0;
		}

	// -------------------------- OTHER METHODS --------------------------

	public void clear()
		{
		buf.clear();
		}

	public boolean get(int pos)
		{
		return buf.get((start + pos + size) % size);
		}

	public boolean getLast()
		{
		return buf.get((start - 1 + size) % size);
		}

	public void retain(@NotNull BitSet mask)
		{
		//assert mask.length() == size;

		//assume the buffer is sparse; operate only on the true bits
		for (int i = buf.nextSetBit(0); i >= 0; i = buf.nextSetBit(i + 1))
			{
			// i is the index in the underlying buffer.

			int pos = (i - start + size) % size;// the logical position
			if (!mask.get(pos))
				{
				buf.set(i, false);
				}
			}
		}

	public void set(int pos, boolean val)
		{
		buf.set((start + pos + size) % size, val);
		}

	/**
	 * shift the field a number of steps to the right (i.e., by moving the start pointer to the left)
	 */
	public void shift(int steps)
		{
		start = (start - steps + size) % size;
		}
	}
