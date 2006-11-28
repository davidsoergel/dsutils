package com.davidsoergel.dsutils;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: soergel
 * Date: Nov 20, 2006
 * Time: 2:48:55 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractHierarchyNode<T> implements HierarchyNode<T>
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
