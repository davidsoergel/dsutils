/*
 * Copyright (c) 2007 Regents of the University of California
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
 *     * Neither the name of the University of California, Berkeley nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
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

package com.davidsoergel.dsutils.range;


import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public abstract class BasicSetRange<T> implements DiscreteRange<T>
	{
	protected final SortedSet<T> values = new TreeSet<T>();


	// --------------------------- CONSTRUCTORS ---------------------------

	protected BasicSetRange(Collection<T> values)
		{
		values.addAll(values);
		}

	public SortedSet<T> getValues()
		{
		return values;
		}


	public String toString()
		{
		return "{" + StringUtils.join(values.iterator(), ",") + "}";
		}

	// -------------------------- OTHER METHODS --------------------------

	public BasicSetRange<T> expandToInclude(T v)
		{
		Set<T> newValues = new HashSet<T>(values.size() + 1);
		newValues.addAll(values);
		newValues.add(v);
		return create(values);
		}

	public BasicSetRange<T> expandToInclude(DiscreteRange<T> v)
		{
		Set<T> newValues = new HashSet<T>(values.size() + v.size());
		newValues.addAll(values);
		newValues.addAll(v.getValues());
		return create(values);
		}

	protected abstract BasicSetRange<T> create(final Collection<T> values);


	public int size()
		{
		return values.size();
		}

	public boolean encompassesValue(final T value)
		{
		return values.contains(value);
		}
	}
