/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import com.davidsoergel.dsutils.GenericFactory;
import com.davidsoergel.dsutils.GenericFactoryException;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class Grid<A, B, C>
	{
	private static final Logger logger = Logger.getLogger(Grid.class);
// ------------------------------ FIELDS ------------------------------

	A[] xValues;
	B[] yValues;

	// ** Iterators are Row-major order, with the origin in the lower left.  x = position; y = nonGapWidth
	// but unless createGrid was run, the cells may not be fully populated
	@NotNull
	SortedMap<OrderedPair<A, B>, C> values = new TreeMap<OrderedPair<A, B>, C>(OrderedPair.getRowMajorComparator());


// -------------------------- OTHER METHODS --------------------------

	public int cols()
		{
		return xValues.length;
		}

	public void createGrid(@NotNull final A[] xValues, @NotNull final B[] yValues,
	                       @NotNull final GenericFactory<C> cFactory)
		{
		this.xValues = xValues;
		this.yValues = yValues;

		Arrays.sort(this.xValues);
		Arrays.sort(this.yValues);

		for (@NotNull final A x : xValues)
			{
			for (@NotNull final B y : yValues)
				{
				try
					{
					values.put(new OrderedPair<A, B>(x, y), cFactory.create());
					}
				catch (GenericFactoryException e)
					{
					logger.error("Error", e);
					throw new CollectionRuntimeException(e);
					}
				}
			}
		assert values.size() == yValues.length * xValues.length;
		}

	@NotNull
	public OrderedPair<A, B> decodeRowMajorIndex(final int index)
		{
		final int xIndex = index % xValues.length;
		final int yIndex = index / xValues.length;
		return new OrderedPair<A, B>(xValues[xIndex], yValues[yIndex]);
		}

	public Set<Map.Entry<OrderedPair<A, B>, C>> entrySet()
		{
		return values.entrySet();
		}

	@NotNull
	public List<C> extractRow(final int yIndex)
		{
		@NotNull List<C> result = new ArrayList<C>();
		final B y = yValues[yIndex];
		for (@NotNull final A x : xValues)
			{
			final C value = values.get(new OrderedPair<A, B>(x, y));
			if (value == null)
				{
				logger.warn("No values at " + x + ", " + y);
				}
			result.add(value);
			}
		return result;
		}

	public C get(@NotNull final A x, @NotNull final B y)
		{
		return values.get(new OrderedPair<A, B>(x, y));
		}

	public int size()
		{
		assert values.size() == yValues.length * xValues.length;
		return values.size();
		}

	@NotNull
	public Map<Integer, C> valuesByRowMajorIndex()
		{
		@NotNull final Map<Integer, C> result = new HashMap<Integer, C>();
		// ** Row-major order, with the origin in the lower left.  x = position; y = nonGapWidth
		int gridIndex = 0;

		// this guarantees an entry for every cell, even if it's null
		for (@NotNull final B y : yValues)
			{
			for (@NotNull final A x : xValues)
				{
				final C value = values.get(new OrderedPair<A, B>(x, y));
				if (value == null)
					{
					logger.warn("No values at " + x + ", " + y);
					}
				result.put(gridIndex, value);
				gridIndex++;
				}
			}


		//for (C c : result.values())
		//	{
		//	result.put(gridIndex,c);
		//	gridIndex++;
		//	}
		return result;
		}
	}
