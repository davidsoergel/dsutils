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

import java.util.Collection;

/**
 * Created by IntelliJ IDEA. User: lorax Date: Sep 13, 2006 Time: 1:34:37 PM To change this template use File | Settings
 * | File Templates.
 */
public interface HierarchyNode<T>// was abstract class
	{
	// -------------------------- OTHER METHODS --------------------------

	/*private HierarchyNode<T> parent;
	private T contents;
	//private Collection<HierarchyNode<T>> children;
*/

	public Collection<HierarchyNode<T>> getChildren();

	/*		{
	   this.parent = parent;
	   }*/

	public T getContents();

	//	public void addChild(HierarchyNode<T> child);
	/*		{
	   getChildren().add(child);
	   }*/

	public HierarchyNode<T> getParent();

	/*		{
	   this.contents = contents;
	   }*/

	public HierarchyNode<T> newChild();

	/*		{
	   return contents;
	   }*/

	public void setContents(T contents);

	/*		{
	   return parent;
	   }*/

	public void setParent(HierarchyNode<T> parent);

	//	public void beginTaxn();

	//	public void commit();

	//	void merge();
	}
