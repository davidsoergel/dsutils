package com.davidsoergel.dsutils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: lorax
 * Date: Sep 13, 2006
 * Time: 1:36:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class ListHierarchyNode<T> extends HierarchyNode<T>
	{
	private List<HierarchyNode<T>> children = new ArrayList<HierarchyNode<T>>();

	public List<HierarchyNode<T>> getChildren()
		{
		return children;
		}

	public ListHierarchyNode<T> newChild()
		{
		ListHierarchyNode<T> result = new ListHierarchyNode<T>();
		children.add(result);
		return result;
		}
	}
