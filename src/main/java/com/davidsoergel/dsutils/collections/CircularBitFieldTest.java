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

import org.testng.annotations.Test;

import java.util.BitSet;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class CircularBitFieldTest
	{
	@Test
	public void getAndSetAreConsistentAndCircular()
		{
		CircularBitField c = new CircularBitField(10);
		c.set(3, true);
		c.set(7, true);
		c.set(8, true);
		c.set(9, true);

		assert c.get(0) == false;
		assert c.get(1) == false;
		assert c.get(2) == false;
		assert c.get(3) == true;
		assert c.get(4) == false;
		assert c.get(5) == false;
		assert c.get(6) == false;
		assert c.get(7) == true;
		assert c.get(8) == true;
		assert c.get(9) == true;

		assert c.get(100) == false;
		assert c.get(101) == false;
		assert c.get(102) == false;
		assert c.get(103) == true;
		assert c.get(104) == false;
		assert c.get(105) == false;
		assert c.get(106) == false;
		assert c.get(107) == true;
		assert c.get(108) == true;
		assert c.get(109) == true;


		c.set(108, false);

		assert c.get(8) == false;
		}

	@Test
	public void negativeShiftMovesWindowRightBitsLeft()
		{
		CircularBitField c = new CircularBitField(10);
		c.set(3, true);
		c.set(7, true);
		c.set(8, true);
		c.set(9, true);

		assert c.get(0) == false;
		assert c.get(1) == false;
		assert c.get(2) == false;
		assert c.get(3) == true;
		assert c.get(4) == false;
		assert c.get(5) == false;
		assert c.get(6) == false;
		assert c.get(7) == true;
		assert c.get(8) == true;
		assert c.get(9) == true;

		c.shift(-3);

		assert c.get(0) == true;
		assert c.get(1) == false;
		assert c.get(2) == false;
		assert c.get(3) == false;
		assert c.get(4) == true;
		assert c.get(5) == true;
		assert c.get(6) == true;
		assert c.get(7) == false;
		assert c.get(8) == false;
		assert c.get(9) == false;
		}


	@Test
	public void positiveShiftMovesWindowLeftBitsRight()
		{
		CircularBitField c = new CircularBitField(10);
		c.set(3, true);
		c.set(7, true);
		c.set(8, true);
		c.set(9, true);

		assert c.get(0) == false;
		assert c.get(1) == false;
		assert c.get(2) == false;
		assert c.get(3) == true;
		assert c.get(4) == false;
		assert c.get(5) == false;
		assert c.get(6) == false;
		assert c.get(7) == true;
		assert c.get(8) == true;
		assert c.get(9) == true;

		c.shift(3);

		assert c.get(0) == true;
		assert c.get(1) == true;
		assert c.get(2) == true;
		assert c.get(3) == false;
		assert c.get(4) == false;
		assert c.get(5) == false;
		assert c.get(6) == true;
		assert c.get(7) == false;
		assert c.get(8) == false;
		assert c.get(9) == false;
		}


	@Test
	public void getLastWorksEvenWhenShifted()
		{
		CircularBitField c = new CircularBitField(10);
		c.set(3, true);
		c.set(7, true);
		c.set(8, true);
		c.set(9, true);

		assert c.getLast() == true;

		c.shift(-3);

		assert c.getLast() == false;

		c.shift(3);

		assert c.getLast() == true;

		c.shift(3);

		assert c.getLast() == false;

		c.shift(-3);

		assert c.getLast() == true;
		}


	@Test
	public void retainWorks()
		{
		CircularBitField c = new CircularBitField(10);
		c.set(3, true);
		c.set(7, true);
		c.set(8, true);
		c.set(9, true);

		BitSet mask = new BitSet(10);
		mask.set(5, true);
		mask.set(7, true);
		mask.set(8, true);

		c.retain(mask);

		assert c.get(0) == false;
		assert c.get(1) == false;
		assert c.get(2) == false;
		assert c.get(3) == false;
		assert c.get(4) == false;
		assert c.get(5) == false;
		assert c.get(6) == false;
		assert c.get(7) == true;
		assert c.get(8) == true;
		assert c.get(9) == false;

		c.shift(-1);


		assert c.get(0) == false;
		assert c.get(1) == false;
		assert c.get(2) == false;
		assert c.get(3) == false;
		assert c.get(4) == false;
		assert c.get(5) == false;
		assert c.get(6) == true;
		assert c.get(7) == true;
		assert c.get(8) == false;
		assert c.get(9) == false;

		c.retain(mask);

		assert c.get(0) == false;
		assert c.get(1) == false;
		assert c.get(2) == false;
		assert c.get(3) == false;
		assert c.get(4) == false;
		assert c.get(5) == false;
		assert c.get(6) == false;// at position 7 before, fails mask
		assert c.get(7) == true;// at position 8 before, passes mask
		assert c.get(8) == false;
		assert c.get(9) == false;
		}


	@Test
	public void clearSetsAllBitsFalse()
		{
		CircularBitField c = new CircularBitField(10);
		c.set(3, true);
		c.set(7, true);
		c.set(8, true);
		c.set(9, true);


		assert c.get(0) == false;
		assert c.get(1) == false;
		assert c.get(2) == false;
		assert c.get(3) == true;
		assert c.get(4) == false;
		assert c.get(5) == false;
		assert c.get(6) == false;
		assert c.get(7) == true;
		assert c.get(8) == true;
		assert c.get(9) == true;

		c.clear();


		assert c.get(0) == false;
		assert c.get(1) == false;
		assert c.get(2) == false;
		assert c.get(3) == false;
		assert c.get(4) == false;
		assert c.get(5) == false;
		assert c.get(6) == false;
		assert c.get(7) == false;
		assert c.get(8) == false;
		assert c.get(9) == false;
		}
	}
