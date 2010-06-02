package com.davidsoergel.dsutils.collections;

import java.util.HashMap;
import java.util.Map;

/**
 * Save memory by storing only one object for each set of equal() objects.  This is sensible only if the objects are
 * immutable and equals() is legitimate.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class Dereplicator<T>
	{
	private Map<T, T> objects = new HashMap<T, T>();

	public T derep(T o)
		{
		if (o == null)
			{
			return null;
			}
		T result = objects.get(o);
		if (result == null)
			{
			objects.put(o, o);
			result = o;
			}
		return result;
		}
	}
