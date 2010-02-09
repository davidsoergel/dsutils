package com.davidsoergel.dsutils.collections;

import org.testng.annotations.Test;


/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class InsertionTrackingSetTest
	{
	@Test
	public void insertionOrderIsTracked()
		{
		InsertionTrackingSet<String> t = new InsertionTrackingSet<String>();
		t.add("a");
		t.add("b");
		t.add("c");
		t.add("d");
		t.add("e");

		assert t.indexOf("a") == 0;
		assert t.indexOf("b") == 1;
		assert t.indexOf("c") == 2;
		assert t.indexOf("d") == 3;
		assert t.indexOf("e") == 4;
		}

	@Test
	public void insertionOrderIsNotAffectedByRemoval()
		{
		InsertionTrackingSet<String> t = new InsertionTrackingSet<String>();
		t.add("a");
		t.add("b");
		t.add("c");
		t.add("d");
		t.add("e");

		t.remove("b");
		t.remove("d");

		assert t.indexOf("a") == 0;
		assert t.indexOf("b") == null;
		assert t.indexOf("c") == 2;
		assert t.indexOf("d") == null;
		assert t.indexOf("e") == 4;

		t.add("f");
		assert t.indexOf("f") == 5;
		}

	@Test
	public void getHonorsInsertionOrder()
		{
		InsertionTrackingSet<String> t = new InsertionTrackingSet<String>();
		t.add("a");
		t.add("b");
		t.add("c");
		t.add("d");
		t.add("e");

		assert t.get(0).equals("a");
		assert t.get(1).equals("b");
		assert t.get(2).equals("c");
		assert t.get(3).equals("d");
		assert t.get(4).equals("e");
		}

	@Test
	public void getByInsertionOrderIsNotAffectedByRemoval()
		{
		InsertionTrackingSet<String> t = new InsertionTrackingSet<String>();
		t.add("a");
		t.add("b");
		t.add("c");
		t.add("d");
		t.add("e");

		t.remove("b");
		t.remove("d");

		assert t.get(0).equals("a");
		assert t.get(1) == null;
		assert t.get(2).equals("c");
		assert t.get(3) == null;
		assert t.get(4).equals("e");

		t.add("f");
		assert t.get(5).equals("f");
		}
	}