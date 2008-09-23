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

import java.util.Collection;

// TODO make ListHierarchyNode, etc. extend this

/**
 * Abstract implementation of some of the most basic HierarchyNode functionality.  Concrete classes extending this need
 * implement only getChildren() and newChild(), because they must choose what kind of Collection to use for the
 * children.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class AbstractHierarchyNode<T, I extends AbstractHierarchyNode<T, I>> implements HierarchyNode<T, I>
	{
	// ------------------------------ FIELDS ------------------------------

	private I parent;//HierarchyNode<? extends T, I>
	private T contents;


	// --------------------- GETTER / SETTER METHODS ---------------------

	/**
	 * {@inheritDoc}
	 */
	public T getValue()
		{
		return contents;
		}

	/**
	 * {@inheritDoc}
	 */
	public void setValue(T contents)
		{
		this.contents = contents;
		}

	/**
	 * {@inheritDoc}
	 */
	public I getParent()//HierarchyNode<? extends T, I>
		{
		return parent;
		}

	public void setParent(I parent)
		{
		this.parent = parent;
		parent.getChildren().add((I) this);
		}

	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface HierarchyNode ---------------------

	//private Collection<HierarchyNode<T>> children;

	/**
	 * {@inheritDoc}
	 */
	public abstract Collection<I> getChildren();


	// -------------------------- OTHER METHODS --------------------------

	/**
	 * {@inheritDoc}
	 */
	/*	public void addChild(HierarchyNode<? extends T, I> child)
	   {
	   getChildren().add(child);
	   // NO!
	   // child.setParent(this);
	   }*/
	public abstract HierarchyNode<T, I> newChild(T contents);

	public HierarchyNode<T, I> getSelf()
		{
		return this;
		}
	}
