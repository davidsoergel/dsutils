package com.davidsoergel.event;

/**
 * @author lorax
 * @version 1.0
 */
public interface EventSource
	{
// -------------------------- OTHER METHODS --------------------------

	public boolean persistentEquals(Object a);

	/** Grovide a hashcode that identifies a unique database record, even when it is represented by multiple distinct
	 * Java objects.  This occurs when the same object is loaded in separate Hibernate sessions.
	 * @return
	 */
	public int persistentHashCode();
	}
