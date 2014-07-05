/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.collections;

import org.jetbrains.annotations.NotNull;
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
		@NotNull CircularBitField c = new CircularBitField(10);
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
		@NotNull CircularBitField c = new CircularBitField(10);
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
		@NotNull CircularBitField c = new CircularBitField(10);
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
		@NotNull CircularBitField c = new CircularBitField(10);
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
		@NotNull CircularBitField c = new CircularBitField(10);
		c.set(3, true);
		c.set(7, true);
		c.set(8, true);
		c.set(9, true);

		@NotNull BitSet mask = new BitSet(10);
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
		@NotNull CircularBitField c = new CircularBitField(10);
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
