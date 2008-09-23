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

	public void retain(BitSet mask)
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
