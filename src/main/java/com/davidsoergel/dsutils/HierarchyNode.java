package com.davidsoergel.dsutils;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: lorax
 * Date: Sep 13, 2006
 * Time: 1:34:37 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class HierarchyNode<T>
	{
	private HierarchyNode<T> parent;
	private T contents;
	//private Collection<HierarchyNode<T>> children;

	public abstract Collection<HierarchyNode<T>> getChildren();

	public void addChild(HierarchyNode<T> child)
		{
		getChildren().add(child);
		}

	public HierarchyNode<T> getParent()
		{
		return parent;
		}

	public void setParent(HierarchyNode<T> parent)
		{
		this.parent = parent;
		}

	public T getContents()
		{
		return contents;
		}

	public void setContents(T contents)
		{
		this.contents = contents;
		}

	public abstract HierarchyNode<T> newChild(T contents);
	}
