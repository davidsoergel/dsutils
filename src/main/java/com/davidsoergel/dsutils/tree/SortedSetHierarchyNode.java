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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A node in a simple hierarchy, where a value of the given generic type is attached at each node.  The children are
 * stored in a SortedSet and thus maintain their natural order.
 */
public class SortedSetHierarchyNode<T extends Comparable>
		implements HierarchyNode<T, SortedSetHierarchyNode<T>>, Comparable
	{
	// ------------------------------ FIELDS ------------------------------

	private SortedSet<HierarchyNode<T, SortedSetHierarchyNode<T>>> children =
			new TreeSet<HierarchyNode<T, SortedSetHierarchyNode<T>>>();


	private SortedSetHierarchyNode<T> parent;
	private T contents;


	// --------------------- GETTER / SETTER METHODS ---------------------

	public SortedSet<HierarchyNode<T, SortedSetHierarchyNode<T>>> getChildren()
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

	public SortedSetHierarchyNode<T> getParent()
		{
		return parent;
		}

	public void setParent(SortedSetHierarchyNode<T> parent)
		{
		this.parent = parent;
		}

	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface Comparable ---------------------

	public int compareTo(Object o)
		{
		return getValue().compareTo(o);
		}

	// --------------------- Interface HierarchyNode ---------------------

	public SortedSetHierarchyNode<T> newChild()
		{
		SortedSetHierarchyNode<T> result = new SortedSetHierarchyNode<T>();
		//result.setContents(contents);
		children.add(result);
		return result;
		}


	public boolean isLeaf()
		{
		return children == null || children.isEmpty();
		}

	public List<SortedSetHierarchyNode<T>> getAncestorPath()
		{
		List<SortedSetHierarchyNode<T>> result = new LinkedList<SortedSetHierarchyNode<T>>();
		SortedSetHierarchyNode<T> trav = this;

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
	public Iterator<SortedSetHierarchyNode<T>> iterator()
		{
		return new DepthFirstTreeIteratorImpl(this);
		}

	public DepthFirstTreeIterator<T, SortedSetHierarchyNode<T>> depthFirstIterator()
		{
		return new DepthFirstTreeIteratorImpl(this);
		}
	}
