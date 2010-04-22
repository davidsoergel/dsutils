package com.davidsoergel.dsutils.tuples;

import com.davidsoergel.dsutils.range.Range;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface TupleStore
	{
	//int getDimensionNumber(String name);

	//int[] getDimensionNumbers(List<String> dimensionNames);

	//void addFunctionDimension(String name, String function);

	//void addDimension(String name);

	/**
	 * It's important that the dimensionNumbers don't change between the time they were read from the store using
	 * getDimensionNumbers and the time that they're used here.  Accomplishing that with synchronization might be some
	 * hassle
	 *
	 * @param values
	 */
	//void addPoint(int[] dimensionNumbers, Object[] values);

	void addPoints(TupleStream points) throws TupleException;

	TupleStream select(Map<String, Range> constraints) throws TupleException;

	TupleStream selectAll() throws TupleException;

	List<String> getDimensions();

	Map<String, Collection> getUniqueValuesPerDimension(int maxSetSize);

	Map<Comparable, TupleStream> partition(String dimension, final int maxSetSize) throws TupleException;

	/**
	 * Partition the data on one dimension; join it on another; then select a column from the original data and place it in
	 * multiple columns, named according to the partition value.
	 * <p/>
	 * This assumes that the join produces 0 or 1 matches; a to-many relationship produces an error.
	 *
	 * @param partitionDimension
	 * @param joinDimension
	 * @param selectDimension
	 * @return
	 */
	TupleStream partitionJoinSelect(String partitionDimension, final int maxSetSize, String joinDimension,
	                                String selectDimension) throws TupleException;
	}
