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
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA. User: lorax Date: May 7, 2007 Time: 1:18:24 AM To change this template use File | Settings
 * | File Templates.
 */
public abstract class HierarchicalStringObjectMap implements HierarchyNode<Map<String, Object>>, Map<String, Object>
	{
	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface HierarchyNode ---------------------


	public abstract HierarchicalStringObjectMap newChild();


	// --------------------- Interface Map ---------------------

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

	// -------------------------- OTHER METHODS --------------------------

	public abstract void merge();
	}
