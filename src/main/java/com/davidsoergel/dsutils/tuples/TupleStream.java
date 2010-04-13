package com.davidsoergel.dsutils.tuples;

import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface TupleStream
		//extends Iterator<Point>
	{

	List<String> getDimensions();

	Iterator<Object[]> iterator() throws TupleException;
	}
