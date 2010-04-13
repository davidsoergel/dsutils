package com.davidsoergel.dsutils.tuples;

import com.davidsoergel.dsutils.collections.DSCollectionUtils;
import com.davidsoergel.dsutils.range.Range;

import java.util.ArrayList;
import java.util.Collection;
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
	}
