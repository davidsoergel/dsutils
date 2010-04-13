package com.davidsoergel.dsutils.tuples;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
@Deprecated
final class Dimension
	{
	String name;
	Class type;

	Dimension(final String name, final Class type)
		{
		this.name = name;
		this.type = type;
		}

/*	public int getNumber()
		{
		return dimensionalStore.getDimensionNumber(name);
		}*/

	// all types are expensive compared to short!

/*		public boolean isExpensive()
		{
		//assume that any number is as expensive as
		Double.SIZE;
		Integer.SIZE
				Short.SIZE
		if(type.isPrimitive() || (Number.class.isAssignableFrom(type) && type.SIZE)
			{
			return false;
			}
		return true;
		}*/
	}
