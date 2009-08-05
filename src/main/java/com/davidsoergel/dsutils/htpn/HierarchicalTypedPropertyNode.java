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

package com.davidsoergel.dsutils.htpn;

import com.davidsoergel.dsutils.Incrementable;
import com.davidsoergel.dsutils.collections.OrderedPair;
import com.davidsoergel.dsutils.tree.HierarchyNode;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

/**
 * Describes a node in a tree containing a key-value pair (at this node) and a set of children.  The types of both the
 * key and the value are generic.  The children can be retrieved by key, Map-style.  Also stores various flags (e.g.,
 * nullable, editable, etc.) to support the @Property framework.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id: HierarchicalTypedPropertyNode.java 250 2009-07-29 01:05:06Z soergel $
 */
public interface HierarchicalTypedPropertyNode<S extends Comparable, T> extends Comparable,
                                                                                HierarchyNode<OrderedPair<S, T>, HierarchicalTypedPropertyNode<S, T>> //, Serializable//extends HierarchyNode<Map<String, Object>>, Map<String, Object>//extends TypedProperties
	{
	public enum PropertyConsumerFlags
		{
			INHERITED
		}
	// -------------------------- OTHER METHODS --------------------------

	void addChild(HierarchicalTypedPropertyNode<S, T> n);

	void addChild(S key, T value);

	void addChild(List<S> keyPath, T value);

	void addChild(List<S> keyPath, S leafKey, T value);

	//void addChild(S[] keyPath, T value);

	void collectDescendants(List<S> keyPath, Map<List<S>, HierarchicalTypedPropertyNode<S, T>> result);

	Map<List<S>, HierarchicalTypedPropertyNode<S, T>> getAllDescendants();

	HierarchicalTypedPropertyNode<S, T> getChild(S key);

	Collection<? extends HierarchicalTypedPropertyNode<S, T>> getChildNodes();

	Map<S, HierarchicalTypedPropertyNode<S, T>> getChildrenByName();

	T getDefaultValue();

	String getDefaultValueString();

	Object[] getEnumOptions();

	String getHelpmessage();

	T getInheritedValue(S name);

	HierarchicalTypedPropertyNode<S, T> getInheritedNode(S name);

	S getKey();

	HierarchicalTypedPropertyNode<S, T> getOrCreateDescendant(List<S> keys);

	HierarchicalTypedPropertyNode<S, T> getOrCreateChild(S childKey, T childValue);

	HierarchicalTypedPropertyNode<S, T> getParent();


	SortedSet<Class> getPluginOptions(Incrementable incrementor);

	/**
	 * Returns the runtime type of the value contained in this node.  Naturally this must be assignable to the generic
	 * type
	 *
	 * @return
	 */
	Type getType();

	T getValue();

	void inheritValueIfNeeded();

	void copyChildrenFrom(HierarchicalTypedPropertyNode<S, T> inheritedNode) throws HierarchicalPropertyNodeException;

	//boolean isNamesSubConsumer();

	boolean isClassBoundPlugin();

	//** All PluginMap functionality needs revisiting
	//boolean isPluginMap();

	boolean isEditable();

	boolean isNullable();

	boolean isObsolete();

	boolean isChanged();

//	boolean isInherited();

	void setChanged(boolean changed);

	List<S> keyPath();

	void removeChild(S key);

	void removeObsoletes();

	void setDefaultValueString(String defaultValueString) throws HierarchicalPropertyNodeException;

	void setEditable(boolean editable);

	void setHelpmessage(String helpmessage);

	void setKey(S newKey);

	void setNullable(boolean nullable) throws HierarchicalPropertyNodeException;

	void setObsolete(boolean b);

	void setParent(HierarchicalTypedPropertyNode<S, T> key);

	void setType(Type type);

	//int compareTo(T o);


	//	public void updateValuesFromDefaults() throws PropertyConsumerNodeException;

	//	void updateDynamicConsumerNodesFromDefaults() throws PropertyConsumerNodeException;

	void setValue(T value) throws HierarchicalPropertyNodeException;

	/**
	 * Test whether this node has had any fields set yet.  Returns true only if the node is completely empty of interesting
	 * contents.
	 *
	 * @return
	 */
	//	boolean isNew();

	//void sanityCheck();

//	void putClassBoundPluginSingleton(Object instance);

//	Object getClassBoundPluginSingleton() throws HierarchicalPropertyNodeException;
	}
