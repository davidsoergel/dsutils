/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class Symmetric2dBiMapTest
	{
	Symmetric2dBiMap<String, Double> map;

	@BeforeMethod
	public void setUp() throws Exception
		{
		map = new SortedSymmetric2dBiMapImpl<String, Double>();
		map.put("a", "b", 1.);
		map.put("a", "c", 2.);
		map.put("a", "d", 3.);

		map.put("b", "c", 4.);
		map.put("b", "d", 5.);

		map.put("c", "d", 6.);
		}


	@Test
	public void putNewPairWorks()
		{
		assert map.get("a", "b") == 1.;
		assert map.get("a", "c") == 2.;
		assert map.get("a", "d") == 3.;

		assert map.get("b", "c") == 4.;
		assert map.get("b", "d") == 5.;

		assert map.get("c", "d") == 6.;

		assert map.numKeys() == 4;
		assert map.numPairs() == 6;
		}

	@Test
	public void putPairReplacingWorks()
		{
		map.put("b", "c", 7.);
		map.put("b", "d", 8.);

		assert map.get("a", "b") == 1.;
		assert map.get("a", "c") == 2.;
		assert map.get("a", "d") == 3.;

		assert map.get("b", "c") == 7.;
		assert map.get("b", "d") == 8.;

		assert map.get("c", "d") == 6.;

		assert map.numKeys() == 4;
		assert map.numPairs() == 6;
		}

	@Test
	public void getPairReversedWorks()
		{
		assert map.get("b", "a") == 1.;
		assert map.get("c", "a") == 2.;
		assert map.get("d", "a") == 3.;

		assert map.get("c", "b") == 4.;
		assert map.get("d", "b") == 5.;

		assert map.get("d", "c") == 6.;

		assert map.numKeys() == 4;
		assert map.numPairs() == 6;
		}

	@Test
	public void removeWorks()
		{
		assert map.get("b", "d") == 5.;
		assert map.numKeys() == 4;
		assert map.numPairs() == 6;

		map.remove("b");
		assert map.get("b", "d") == null;
		assert map.numKeys() == 3;
		assert map.numPairs() == 3;

		map.remove("a");
		assert map.get("a", "c") == null;
		assert map.numKeys() == 2;
		assert map.numPairs() == 1;

		assert map.get("c", "d") == 6.;
		}
	}
