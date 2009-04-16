package com.davidsoergel.dsutils;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface Incrementable
	{
	public void increment();

	void setMaximum(int i);

	void setNote(String n);

	void incrementMaximum(int length);

	void resetWithNote(String s);
	}
