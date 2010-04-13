package com.davidsoergel.dsutils.tuples;

import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SimpleTupleStream implements TupleStream
	{
	List<String> dims;
	Iterator<Object[]> it;

	public SimpleTupleStream(final List<String> dims, final Iterator<Object[]> it)
		{
		this.dims = dims;
		this.it = it;
		}

	public List<String> getDimensions()
		{
		return dims;
		}

	public Iterator<Object[]> iterator()
		{
		return it;
		}
	}
