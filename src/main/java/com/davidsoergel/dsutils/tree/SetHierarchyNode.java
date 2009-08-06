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

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A node in a simple hierarchy, where a value of the given generic type is attached at each node.  The children are
 * stored in a Set and are thus unordered.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SetHierarchyNode<T extends Serializable> extends AbstractHierarchyNode<T, SetHierarchyNode<T>>
		implements Serializable
	{
	// ------------------------------ FIELDS ------------------------------

	protected Set<SetHierarchyNode<T>> children = new HashSet<SetHierarchyNode<T>>();


	public SetHierarchyNode(T contents)
		{
		super(contents);
		}

	public SetHierarchyNode()
		{
		super();
		}

	public void registerChild(SetHierarchyNode<T> child)
		{
		children.add(child);
		}

	public void unregisterChild(SetHierarchyNode<T> child)
		{
		children.remove(child);
		}

	public Set<SetHierarchyNode<T>> getChildren()
		{
		return children;
		}

	/*	public SetHierarchyNode<T> newChild()
		 {
		 SetHierarchyNode<T> result = new SetHierarchyNode<T>();
		 //children.add(result);  // setParent calls registerChild
		 result.setParent(this);
		 return result;
		 }
 */
	public SetHierarchyNode<T> newChild(T payload)
		{
		SetHierarchyNode<T> result = new SetHierarchyNode<T>();
		//children.add(result);  // setParent calls registerChild
		result.setPayload(payload);
		result.setParent(this);
		return result;
		}

	private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException
		{
		payload = (T) stream.readObject();
		children = (Set<SetHierarchyNode<T>>) stream.readObject();

		for (SetHierarchyNode<T> child : children)
			{
			child.setParent(this);
			}
		}

	private void writeObject(java.io.ObjectOutputStream stream) throws IOException
		{
		stream.writeObject(payload);
		stream.writeObject(children);
		}
	}
