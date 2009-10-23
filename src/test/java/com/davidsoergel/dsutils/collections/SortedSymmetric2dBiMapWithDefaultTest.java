package com.davidsoergel.dsutils.collections;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SortedSymmetric2dBiMapWithDefaultTest
	{
	SortedSymmetric2dBiMapWithDefault<String, Double> map;

	@BeforeMethod
	public void setUp() throws Exception
		{
		map = new SortedSymmetric2dBiMapWithDefault<String, Double>(Double.MAX_VALUE);
		map.put("a", "b", 1.);
		map.put("a", "c", 2.);
		map.put("a", "d", 3.);

		map.put("b", "c", 4.);
		map.put("b", "d", 5.);

		map.put("c", "d", 6.);
		}

	@Test
	public void keysWithOnlyDefaultValuesAreStored()
		{
		map.put("c", "e", Double.MAX_VALUE);
		map.put("f", "e", Double.MAX_VALUE);
		map.put("g", "h", Double.MAX_VALUE);

		assert map.numKeys() == 8;
		}

	@Test
	public void putNewPairWorks()
		{
		assert map.get("a", "b").equals(1.);
		assert map.get("a", "c").equals(2.);
		assert map.get("a", "d").equals(3.);

		assert map.get("b", "c").equals(4.);
		assert map.get("b", "d").equals(5.);

		assert map.get("c", "d").equals(6.);

		assert map.numKeys() == 4;
		assert map.numPairs() == 6;
		}

	@Test
	public void putPairReplacingWorks()
		{
		map.put("b", "c", 7.);
		map.put("b", "d", 8.);

		assert map.get("a", "b").equals(1.);
		assert map.get("a", "c").equals(2.);
		assert map.get("a", "d").equals(3.);

		assert map.get("b", "c").equals(7.);
		assert map.get("b", "d").equals(8.);

		assert map.get("c", "d").equals(6.);

		assert map.numKeys() == 4;
		assert map.numPairs() == 6;
		}

	@Test
	public void getPairReversedWorks()
		{
		assert map.get("b", "a").equals(1.);
		assert map.get("c", "a").equals(2.);
		assert map.get("d", "a").equals(3.);

		assert map.get("c", "b").equals(4.);
		assert map.get("d", "b").equals(5.);

		assert map.get("d", "c").equals(6.);

		assert map.numKeys() == 4;
		assert map.numPairs() == 6;
		}

	@Test
	public void removeWorks()
		{
		assert map.get("b", "d").equals(5.);
		assert map.numKeys() == 4;
		assert map.numPairs() == 6;

		map.remove("b");
		assert map.get("b", "d").equals(Double.MAX_VALUE);
		assert map.numKeys() == 3;
		assert map.numPairs() == 3;

		map.removalSanityCheck("b", map.getKeys());

		map.remove("a");
		assert map.get("a", "c").equals(Double.MAX_VALUE);
		assert map.numKeys() == 2;
		assert map.numPairs() == 1;

		map.removalSanityCheck("a", map.getKeys());

		assert map.get("c", "d").equals(6.);
		}
	}
