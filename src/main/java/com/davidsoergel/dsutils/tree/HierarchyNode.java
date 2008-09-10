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
import java.util.List;

/**
 * A node in a simple hierarchy, where a value of the given generic type is attached at each node.  A node may have any
 * number of children, which are also HierarchyNodes of the same generic type.  The type of Collection that holds the
 * children is up to the implementation, so they may or may not be ordered.
 *
 * @author <a href="mailto:dev.davidsoergel.com">David Soergel</a>
 * @version $Id$
 * @See com.davidsoergel.runutils.HierarchicalTypedPropertyNode
 */
public interface HierarchyNode<T, I extends HierarchyNode<T, I>> extends Iterable<I>
	{

	Collection<? extends HierarchyNode<T, I>> getChildren();

	boolean isLeaf();

	T getValue();

	void setValue(T contents);

	HierarchyNode<? extends T, I> getParent();

	/**
	 * Returns a List of nodes describing a path down the tree, starting with the root and ending with this node
	 *
	 * @return a List of nodes describing a path down the tree, starting with the root and ending with this node
	 */
	List<? extends HierarchyNode<T, I>> getAncestorPath();

	/**
	 * Creates a new child node of the appropriate type
	 *
	 * @return the new child node
	 */
	HierarchyNode<? extends T, ? extends I> newChild();

	/**
	 * Get an iterator that returns all the nodes of the tree in depth-first order.  The breadth ordering may or may not be
	 * defined, depending on the HierarchyNode implementation.  We provide this depth-first iterator explicitly even though
	 * the HierarchyNode is itself iterable, because the default iterator may be breadth-first or something else.
	 *
	 * @return the DepthFirstTreeIterator<T, I>
	 */
	DepthFirstTreeIterator<T, I> depthFirstIterator();

	/**
	 * In some cases we need to wrap a HierarchyNode in another object that provides a facade.  In this case, wthis method
	 * return the underlying object; otherwise it just returns this.
	 *
	 * @return the most raw available HierarchyNode embedded within this facade (perhps just this object itself)
	 */
	HierarchyNode<T, I> getSelfNode();
	}
