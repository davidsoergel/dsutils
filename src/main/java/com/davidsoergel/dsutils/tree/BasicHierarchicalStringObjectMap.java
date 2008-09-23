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


package com.davidsoergel.dsutils.tree;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * A node in a simple hierarchy, where a Map from Strings to Objects is attached at each node.  Implements the Map
 * interface and simply delegates to the contained Map.  The children are stored in a List and are thus ordered.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class BasicHierarchicalStringObjectMap extends HierarchicalStringObjectMap
	{
	// ------------------------------ FIELDS ------------------------------

	Map<String, Object> contents = new HashMap<String, Object>();

	private List<HierarchicalStringObjectMap> children =
			new ArrayList<HierarchicalStringObjectMap>();//HierarchyNode<Map<String, Object>, HierarchicalStringObjectMap>

	private HierarchicalStringObjectMap parent;

	// --------------------- GETTER / SETTER METHODS ---------------------

	/**
	 * {@inheritDoc}
	 */
	public List<HierarchicalStringObjectMap> getChildren()//HierarchyNode<Map<String, Object>, HierarchicalStringObjectMap>
		{
		return children;
		}


	/**
	 * {@inheritDoc}
	 */
	@NotNull
	public HierarchicalStringObjectMap getChild(Map<String, Object> id)
		{// We could map the children collection as a Map; but that's some hassle, and since there are generally just 2 children anyway, this is simpler

		// also, the child id is often not known when it is added to the children Set, so putting the child into a children Map wouldn't work

		for (HierarchicalStringObjectMap child : children)
			{
			if (child.getValue() == id)
				{
				return child;
				}
			}
		throw new NoSuchElementException();
		}


	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> getValue()
		{
		return contents;
		}

	/**
	 * {@inheritDoc}
	 */
	public void setValue(Map<String, Object> contents)
		{
		this.contents = contents;
		}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HierarchicalStringObjectMap getParent()
		{
		return parent;
		}

	public void setParent(HierarchicalStringObjectMap parent)
		{
		this.parent = parent;
		}

	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface HierarchyNode ---------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HierarchicalStringObjectMap newChild()
		{
		//ListHierarchyNode<Map<String, Object>> result = new ListHierarchyNode<Map<String, Object>>();
		BasicHierarchicalStringObjectMap result = new BasicHierarchicalStringObjectMap();

		//result.setContents(contents);
		children.add(result);
		return result;
		}

	// --------------------- Interface Map ---------------------

	/**
	 * {@inheritDoc}
	 */
	public Object put(String s, Object o)
		{
		return getValue().put(s, o);
		}

	/**
	 * {@inheritDoc}
	 */
	public Object remove(Object o)
		{
		return getValue().remove(o);
		}

	/**
	 * {@inheritDoc}
	 */
	public void putAll(Map<? extends String, ? extends Object> map)
		{
		getValue().putAll(map);
		}

	/**
	 * {@inheritDoc}
	 */
	public void clear()
		{
		getValue().clear();
		}

	// -------------------------- OTHER METHODS --------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void merge()
		{
		//To change body of implemented methods use File | Settings | File Templates.
		}


	/**
	 * Returns an iterator over a set of elements of type T.
	 *
	 * @return an Iterator.
	 */
	public Iterator<HierarchicalStringObjectMap> iterator()
		{
		return new DepthFirstTreeIteratorImpl(this);
		}

	/**
	 * {@inheritDoc}
	 */
	public DepthFirstTreeIterator<Map<String, Object>, HierarchicalStringObjectMap> depthFirstIterator()
		{
		return new DepthFirstTreeIteratorImpl(this);
		}
	}
