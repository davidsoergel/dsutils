/* $Id$ */

/*
 * Copyright (c) 2001-2007 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
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

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by IntelliJ IDEA. User: lorax Date: Sep 13, 2006 Time: 1:36:12 PM To change this template use File | Settings
 * | File Templates.
 */
public class SortedSetHierarchyNode<T extends Comparable> implements HierarchyNode<T>, Comparable
	{
	// ------------------------------ FIELDS ------------------------------

	private SortedSet<HierarchyNode<T>> children = new TreeSet<HierarchyNode<T>>();


	private HierarchyNode<? extends T> parent;
	private T contents;


	// --------------------- GETTER / SETTER METHODS ---------------------

	public SortedSet<HierarchyNode<T>> getChildren()
		{
		return children;
		}

	public T getContents()
		{
		return contents;
		}

	public void setContents(T contents)
		{
		this.contents = contents;
		}

	public HierarchyNode<? extends T> getParent()
		{
		return parent;
		}

	public void setParent(HierarchyNode<? extends T> parent)
		{
		this.parent = parent;
		}

	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface Comparable ---------------------


	public int compareTo(Object o)
		{
		return getContents().compareTo(o);
		}

	// --------------------- Interface HierarchyNode ---------------------

	public SortedSetHierarchyNode<T> newChild()
		{
		SortedSetHierarchyNode<T> result = new SortedSetHierarchyNode<T>();
		//result.setContents(contents);
		children.add(result);
		return result;
		}

	// -------------------------- OTHER METHODS --------------------------

	/**
	 * not needed, non-transactional implementation
	 */
	public void beginTaxn()
		{
		}

	public void commit()
		{
		}

	public void merge()
		{
		//To change body of implemented methods use File | Settings | File Templates.
		}
	}
