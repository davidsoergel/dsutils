/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.swing;

import javax.swing.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * http://www.java2s.com/Tutorial/Java/0240__Swing/SortedListModelsortableJList.htm
 *
 * @version $Id$
 */
public class SortedListModel extends AbstractListModel implements Iterable
	{
	SortedSet<Object> model;

	public SortedListModel()
		{
		model = new TreeSet<Object>();
		}

	public int getSize()
		{
		return model.size();
		}

	public Object getElementAt(int index)
		{
		return model.toArray()[index];
		}

	public void add(Object element)
		{
		if (model.add(element))
			{
			fireContentsChanged(this, 0, getSize());
			}
		}

	public void addAll(Object elements[])
		{
		Collection<Object> c = Arrays.asList(elements);
		model.addAll(c);
		fireContentsChanged(this, 0, getSize());
		}

	public void clear()
		{
		model.clear();
		fireContentsChanged(this, 0, getSize());
		}

	public boolean contains(Object element)
		{
		return model.contains(element);
		}

	public Object firstElement()
		{
		return model.first();
		}

	public Iterator iterator()
		{
		return model.iterator();
		}

	public Object lastElement()
		{
		return model.last();
		}

	public boolean removeElement(Object element)
		{
		boolean removed = model.remove(element);
		if (removed)
			{
			fireContentsChanged(this, 0, getSize());
			}
		return removed;
		}
	}
