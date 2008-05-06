/*
 * Copyright (c) 2001-2008 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * dev@davidsoergel.com
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the names of any contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */



package com.davidsoergel.dsutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A node in a simple hierarchy, where a Map from Strings to Objects is attached at each node.  Implements the Map
 * interface and simply delegates to the contained Map.  The children are stored in a List and are thus ordered.
 */
public class BasicHierarchicalStringObjectMap extends HierarchicalStringObjectMap
	{
	// ------------------------------ FIELDS ------------------------------

	Map<String, Object> contents = new HashMap<String, Object>();

	private List<HierarchyNode<Map<String, Object>>> children = new ArrayList<HierarchyNode<Map<String, Object>>>();

	private HierarchyNode<? extends Map<String, Object>> parent;


	// --------------------- GETTER / SETTER METHODS ---------------------

	public List<HierarchyNode<Map<String, Object>>> getChildren()
		{
		return children;
		}

	public Map<String, Object> getValue()
		{
		return contents;
		}

	public void setValue(Map<String, Object> contents)
		{
		this.contents = contents;
		}

	public HierarchyNode<? extends Map<String, Object>> getParent()
		{
		return parent;
		}

	public void setParent(HierarchyNode<? extends Map<String, Object>> parent)
		{
		this.parent = parent;
		}

	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface HierarchyNode ---------------------

	public HierarchicalStringObjectMap newChild()
		{
		//ListHierarchyNode<Map<String, Object>> result = new ListHierarchyNode<Map<String, Object>>();
		BasicHierarchicalStringObjectMap result = new BasicHierarchicalStringObjectMap();

		//result.setContents(contents);
		children.add(result);
		return result;
		}

	// --------------------- Interface Map ---------------------

	public Object put(String s, Object o)
		{
		return getValue().put(s, o);
		}

	public Object remove(Object o)
		{
		return getValue().remove(o);
		}

	public void putAll(Map<? extends String, ? extends Object> map)
		{
		getValue().putAll(map);
		}

	public void clear()
		{
		getValue().clear();
		}

	// -------------------------- OTHER METHODS --------------------------

	public void merge()
		{
		//To change body of implemented methods use File | Settings | File Templates.
		}
	}
