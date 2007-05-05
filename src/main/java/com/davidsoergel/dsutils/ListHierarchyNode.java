package com.davidsoergel.dsutils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: lorax Date: Sep 13, 2006 Time: 1:36:12 PM To change this template use File | Settings
 * | File Templates.
 */
public class ListHierarchyNode<T> implements HierarchyNode<T>
	{
	private List<HierarchyNode<T>> children = new ArrayList<HierarchyNode<T>>();

	public List<HierarchyNode<T>> getChildren()
		{
		return children;
		}

	public ListHierarchyNode<T> newChild()
		{
		ListHierarchyNode<T> result = new ListHierarchyNode<T>();
		//result.setContents(contents);
		children.add(result);
		return result;
		}

	public void merge()
		{
		//To change body of implemented methods use File | Settings | File Templates.
		}

	private HierarchyNode<T> parent;
	private T contents;

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

	/**
	 * not needed, non-transactional implementation
	 */
	public void beginTaxn()
		{
		}

	public void commit()
		{
		}
	}
