package com.davidsoergel.dsutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: soergel Date: Jun 7, 2007 Time: 3:41:24 PM To change this template use File |
 * Settings | File Templates.
 */
public class BasicHierarchicalStringObjectMap extends HierarchicalStringObjectMap
	{
	Map<String, Object> contents = new HashMap<String, Object>();


	public Object put(String s, Object o)
		{
		return getContents().put(s, o);
		}

	public Object remove(Object o)
		{
		return getContents().remove(o);
		}

	public void putAll(Map<? extends String, ? extends Object> map)
		{
		getContents().putAll(map);
		}

	public void clear()
		{
		getContents().clear();
		}

	private List<HierarchyNode<Map<String, Object>>> children = new ArrayList<HierarchyNode<Map<String, Object>>>();

	public List<HierarchyNode<Map<String, Object>>> getChildren()
		{
		return children;
		}

	public HierarchicalStringObjectMap newChild()
		{
		//ListHierarchyNode<Map<String, Object>> result = new ListHierarchyNode<Map<String, Object>>();
		BasicHierarchicalStringObjectMap result = new BasicHierarchicalStringObjectMap();

		//result.setContents(contents);
		children.add(result);
		return result;
		}

	public void merge()
		{
		//To change body of implemented methods use File | Settings | File Templates.
		}

	private HierarchyNode<Map<String, Object>> parent;

	public HierarchyNode<Map<String, Object>> getParent()
		{
		return parent;
		}

	public void setParent(HierarchyNode<Map<String, Object>> parent)
		{
		this.parent = parent;
		}

	public Map<String, Object> getContents()
		{
		return contents;
		}

	public void setContents(Map<String, Object> contents)
		{
		this.contents = contents;
		}

	}
