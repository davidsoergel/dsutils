package com.davidsoergel.dsutils.tuples;

import com.davidsoergel.dsutils.collections.DSCollectionUtils;
import com.davidsoergel.dsutils.range.BasicEqualsRange;
import com.davidsoergel.dsutils.range.Range;
import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Everything here is a slow implementation
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class NaiveTupleStoreImpl implements TupleStore
	{

	// use List to avoid hashcode cost
	//List<Point> points = new ArrayList<Point>();

	List<String> dimensions;
	List<Object[]> data = new ArrayList<Object[]>();


	public void addPoints(final TupleStream points) throws TupleException
		{
		List<String> dims = points.getDimensions();
		if (dimensions == null)
			{
			dimensions = dims;
			}
		else if (!DSCollectionUtils.isEqualCollection(dimensions, dims))
			{
			throw new TupleException("Multiple iterators fed to a NaiveTupleStoreImpl must have the same dimensions");
			}

		Iterator<Object[]> it = points.iterator();

		while (it.hasNext())
			{
			Object[] objects = it.next();

			// ** Not safe: we store the Object[] from the iterator directly instead of making a copy
			data.add(objects);

			// ** if we were to make a copy: how could we guarantee that the clone is arbitrarily deep?  Is that really what we want?
			}
		}

	public TupleStream select(final Map<String, Range> constraints) throws TupleException
		{
		return new SimpleTupleFilterStream(selectAll(), constraints);
		}

	public TupleStream selectAll()
		{
		return new SimpleTupleStream(dimensions, data.iterator());
		}

	public List<String> getDimensions()
		{
		return dimensions;
		}

	public Map<String, Collection> getUniqueValuesPerDimension(final int maxSetSize)
		{
		Map<String, Collection> result = new TreeMap<String, Collection>();

		Set[] sets = new Set[dimensions.size()];
		int i = 0;
		for (String dimension : dimensions)
			{
			sets[i] = new HashSet();
			i++;
			}

		for (Object[] objects : data)
			{
			int j = 0;
			for (Object object : objects)
				{
				sets[j].add(object);
				j++;
				}
			}

		int k = 0;
		for (Set set : sets)
			{
			if (set.size() < maxSetSize)
				{
				result.put(dimensions.get(k), set);
				}
			k++;
			}

		return result;
		}

	public Map<Comparable, TupleStream> partition(String dimension, final int maxSetSize) throws TupleException
		{
		Collection keys = getUniqueValuesPerDimension(maxSetSize).get(dimension);
		if (keys == null)
			{
			throw new TupleException(
					"Dimension " + dimension + " either does not exist or has more than " + maxSetSize + " values.");
			}

		Map<Comparable, TupleStream> result = new HashMap<Comparable, TupleStream>();
		for (Object key : keys)
			{
			Map<String, Range> simpleConstraint = new HashMap<String, Range>();
			simpleConstraint.put(dimension,
			                     new BasicEqualsRange(key)); //BasicImmutableSetRange(DSCollectionUtils.listOf(key)));
			result.put((Comparable) key, select(simpleConstraint));
			}
		return result;
		}

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
	public TupleStream partitionJoinSelect(String partitionDimension, final int maxSetSize, String joinDimension,
	                                       String selectDimension) throws TupleException
		{
		//int partitionIndex = dimensions.indexOf(partitionDimension);
		int joinIndex = dimensions.indexOf(joinDimension);
		int selectIndex = dimensions.indexOf(selectDimension);


		final Map<Comparable, TupleStream> partitions = partition(partitionDimension, maxSetSize);
		List<Comparable> allPartitionValues = new ArrayList<Comparable>();
		allPartitionValues.addAll(partitions.keySet());
		Collections.sort(allPartitionValues);


		Map<Object, Object[]> joinValuesToRows = new MapMaker().makeComputingMap(new Function<Object, Object[]>()
		{
		public Object[] apply(final Object from)
			{
			return new Object[partitions.size() + 1];
			}
		});

		for (Object partitionValue : allPartitionValues)
			{
			int partitionIndex = allPartitionValues.indexOf(partitionValue);

			TupleStream ps = partitions.get(partitionValue);

			//localDimensions = ps.getDimensions();
			//we can safely assume that each partition has the same dimensions as this store as a whole

			Iterator<Object[]> it = ps.iterator();
			while (it.hasNext())
				{
				Object[] objects = it.next();
				Object joinValue = objects[joinIndex];
				Object selectValue = objects[selectIndex];

				if (joinValuesToRows.get(joinValue)[partitionIndex] != null)
					{
					throw new TupleException(
							"Join resulted in a to-many relationship for " + joinDimension + " = " + joinValue);
					}
				joinValuesToRows.get(joinValue)[partitionIndex] = selectValue;
				}
			}

		// finally add the join value itself as the final column
		int joinColumn = partitions.size();
		for (Object joinValue : joinValuesToRows.keySet())
			{
			joinValuesToRows.get(joinValue)[joinColumn] = joinValue;
			}

		// set up the column names
		List<String> columns = new ArrayList<String>();
		for (Object value : allPartitionValues)
			{
			columns.add(value.toString());
			}
		columns.add(joinDimension);

		// ideally we'd sort this by the join column; oh well
		return new SimpleTupleStream(columns, joinValuesToRows.values().iterator());
		}
	}
