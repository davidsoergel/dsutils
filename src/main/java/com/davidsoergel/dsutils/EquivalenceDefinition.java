package com.davidsoergel.dsutils;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface EquivalenceDefinition<T>
	{
	boolean areEquivalent(T at, T bt);
	}
