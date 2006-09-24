package com.davidsoergel.dsutils;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA.
 * User: lorax
 * Date: Sep 13, 2006
 * Time: 1:36:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class SortedSetHierarchyNode<T extends Comparable> extends HierarchyNode<T> implements Comparable
	{
	private SortedSet<HierarchyNode<T>> children = new TreeSet<HierarchyNode<T>>();

	public SortedSet<HierarchyNode<T>> getChildren()
		{
		return children;
		}


	public SortedSetHierarchyNode<T> newChild(T contents)
		{
		SortedSetHierarchyNode<T> result = new SortedSetHierarchyNode<T>();
		result.setContents(contents);
		children.add(result);
		return result;
		}

	public int compareTo(Object o)
		{
		return getContents().compareTo(o);
		}
	}
