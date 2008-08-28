/*
 * Copyright (c) 2001-2008 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * dev@davidsoergel.com
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the names of any contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
		map = new Symmetric2dBiMap<String, Double>();
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
