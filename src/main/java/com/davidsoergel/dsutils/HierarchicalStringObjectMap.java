package com.davidsoergel.dsutils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA. User: lorax Date: May 7, 2007 Time: 1:18:24 AM To change this template use File | Settings
 * | File Templates.
 */
public abstract class HierarchicalStringObjectMap implements HierarchyNode<Map<String, Object>>, Map<String, Object>
	{
	public int size()
		{
		return getContents().size();
		}

	public boolean isEmpty()
		{
		return getContents().isEmpty();
		}

	public boolean containsKey(Object o)
		{
		return getContents().containsKey(o);
		}

	public boolean containsValue(Object o)
		{
		return getContents().containsValue(o);
		}

	public Object get(Object o)
		{
		return getContents().get(o);
		}

	// transactions required for these

	/*
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
 */
	public Set<String> keySet()
		{
		return getContents().keySet();
		}

	public Collection<Object> values()
		{
		return getContents().values();
		}

	public Set<Entry<String, Object>> entrySet()
		{
		return getContents().entrySet();
		}

	public abstract void merge();

	public abstract HierarchicalStringObjectMap newChild();
	}
