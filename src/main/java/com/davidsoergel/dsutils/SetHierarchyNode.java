package com.davidsoergel.dsutils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: lorax
 * Date: Sep 13, 2006
 * Time: 1:36:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class SetHierarchyNode<T> extends HierarchyNode<T>
	{
	private Set<HierarchyNode<T>> children = new HashSet<HierarchyNode<T>>();

	public Set<HierarchyNode<T>> getChildren()
		{
		return children;
		}

	public SetHierarchyNode<T> newChild(T contents)
		{
		SetHierarchyNode<T> result = new SetHierarchyNode<T>();
		result.setContents(contents);
		children.add(result);
		return result;
		}
	}
