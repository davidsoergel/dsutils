package com.davidsoergel.dsutils.tuples;

import com.davidsoergel.dsutils.collections.DSCollectionUtils;
import com.davidsoergel.dsutils.range.DoubleIntervalRange;
import com.davidsoergel.dsutils.range.Range;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class TupleStoreTest
	{

	List<String> dims = DSCollectionUtils.listOf("a", "b", "c");

	@Test
	public TupleStore createExampleStore() throws TupleException
		{
		TupleStore ds = new NaiveTupleStoreImpl();

		/*	List<Dimension> dims = new ArrayList<Dimension>();
				dims.add(new Dimension("a", Boolean.class));
				dims.add(new Dimension("b", String.class));
				dims.add(new Dimension("c", Integer.class));

				ds.addPoints(new SimpleTupleStream(dims, new ArrayIterator(new Object[]{Boolean.TRUE, "bogus", 12})));*/


		List<Object[]> points = new ArrayList<Object[]>();
		points.add(new Object[]{Boolean.TRUE, "bogus", 12});
		points.add(new Object[]{Boolean.FALSE, 12.1, 5});
		points.add(new Object[]{23.56, 15.3, Boolean.TRUE});


		ds.addPoints(new SimpleTupleStream(dims, points.iterator()));
		return ds;
		}


	@Test
	public void testSelectAll() throws TupleException
		{
		TupleStore ds = createExampleStore();

		TupleStream outstream = ds.selectAll();

		assert DSCollectionUtils.isEqualCollection(outstream.getDimensions(), dims);

		Iterator<Object[]> outIter = outstream.iterator();
		int items = 0;
		while (outIter.hasNext())
			{
			Object[] objects = outIter.next();
			items++;
			}
		assert items == 3;
		}

	@Test
	public void testSelectSome() throws TupleException
		{
		TupleStore ds = createExampleStore();

		Map<String, Range> constraints = new HashMap<String, Range>();
		constraints.put("b", new DoubleIntervalRange(10., 20., false, false));

		TupleStream outstream = ds.select(constraints);

		assert DSCollectionUtils.isEqualCollection(outstream.getDimensions(), dims);

		Iterator<Object[]> outIter = outstream.iterator();
		int items = 0;
		while (outIter.hasNext())
			{
			Object[] objects = outIter.next();
			items++;
			}
		assert items == 2;
		}
	}
