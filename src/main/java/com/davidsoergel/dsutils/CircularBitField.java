package com.davidsoergel.dsutils;

import java.util.BitSet;

public class CircularBitField
	{
	private BitSet buf;
	private int size;
	private int start;

	public CircularBitField(int size)
		{
		this.size = size;
		buf = new BitSet(size);
		start = 0;
		}

	public void set(int pos, boolean val)
		{
		buf.set((start + pos + size) % size, val);
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

	/**
	 * shift the field a number of steps to the right (i.e., by moving the start pointer to the left)
	 */
	public void shift(int steps)
		{
		start = (start - steps + size) % size;
		}

	public void clear()
		{
		buf.clear();
		}
	}
