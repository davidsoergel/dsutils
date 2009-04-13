package com.davidsoergel.dsutils;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class BasicIncrementable implements Incrementable
	{
	int i = 0;

	public void increment()
		{
		i++;
		}

	public void setMaximum(int i)
		{
		}

	public void setNote(String n)
		{
		}

	public void incrementMaximum(int length)
		{
		}

	public int getCount()
		{
		return i;
		}
	}
