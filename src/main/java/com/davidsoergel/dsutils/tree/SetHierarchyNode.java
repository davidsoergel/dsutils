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

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A node in a simple hierarchy, where a value of the given generic type is attached at each node.  The children are
 * stored in a Set and are thus unordered.
 */
public class SetHierarchyNode<T> implements HierarchyNode<T, SetHierarchyNode<T>>
	{
	// ------------------------------ FIELDS ------------------------------

	protected Set<HierarchyNode<T, SetHierarchyNode<T>>> children =
			new HashSet<HierarchyNode<T, SetHierarchyNode<T>>>();

	private SetHierarchyNode<T> parent;
	private T contents;


	// --------------------- GETTER / SETTER METHODS ---------------------

	public Set<HierarchyNode<T, SetHierarchyNode<T>>> getChildren()
		{
		return children;
		}

	public T getValue()
		{
		return contents;
		}

	public void setValue(T contents)
		{
		this.contents = contents;
		}

	public SetHierarchyNode<T> getParent()
		{
		return parent;
		}

	public void setParent(SetHierarchyNode<T> parent)
		{
		this.parent = parent;
		}

	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface HierarchyNode ---------------------

	public SetHierarchyNode<T> newChild()
		{
		SetHierarchyNode<T> result = new SetHierarchyNode<T>();
		//result.setContents(contents);
		children.add(result);
		result.setParent(this);
		return result;
		}

	public boolean isLeaf()
		{
		return children == null || children.isEmpty();
		}

	public List<SetHierarchyNode<T>> getAncestorPath()
		{
		List<SetHierarchyNode<T>> result = new LinkedList<SetHierarchyNode<T>>();
		SetHierarchyNode<T> trav = this;

		while (trav != null)
			{
			result.add(0, trav);
			trav = trav.getParent();
			}

		return result;
		}

	/**
	 * Returns an iterator over a set of elements of type T.
	 *
	 * @return an Iterator.
	 */
	public Iterator<SetHierarchyNode<T>> iterator()
		{
		return new DepthFirstTreeIteratorImpl(this);
		}

	public DepthFirstTreeIterator<T, SetHierarchyNode<T>> depthFirstIterator()
		{
		return new DepthFirstTreeIteratorImpl(this);
		}

	public HierarchyNode<T, SetHierarchyNode<T>> getSelfNode()
		{
		return this;
		}
	}
