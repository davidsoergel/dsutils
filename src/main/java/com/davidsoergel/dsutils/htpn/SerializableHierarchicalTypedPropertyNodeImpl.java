package com.davidsoergel.dsutils.htpn;

import com.davidsoergel.dsutils.collections.OrderedPair;
import com.davidsoergel.dsutils.collections.SerializableOrderedPair;

import java.io.Serializable;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SerializableHierarchicalTypedPropertyNodeImpl<S extends Comparable<S> & Serializable, T extends Serializable>
		extends HierarchicalTypedPropertyNodeImpl<S, T> implements Serializable
	{
	public HierarchicalTypedPropertyNodeImpl<S, T> newChild()
		{
		HierarchicalTypedPropertyNodeImpl<S, T> result = new SerializableHierarchicalTypedPropertyNodeImpl<S, T>();
		//children.add(result);  // setParent calls registerChild
		result.setParent(this);
		return result;
		}

	public HierarchicalTypedPropertyNode<S, T> newChild(final OrderedPair<S, T> payload)
		{
		HierarchicalTypedPropertyNodeImpl<S, T> result = new SerializableHierarchicalTypedPropertyNodeImpl<S, T>();
		//children.add(result);  // setParent calls registerChild
		result.setParent(this);
		result.setPayload(payload);
		return result;
		}

	/**
	 * Set the value of this node.  If this node expects a plugin and the value is a Class (or a String naming a Class),
	 * then create the children array if necessary and populate it with defaults.  Act similarly for a PluginMap.
	 * <p/>
	 * If the children array already exists, and the value was previously null or the same class, then we merge the
	 * defaults non-destructively.
	 *
	 * @param value
	 * @throws HierarchicalPropertyNodeException
	 *
	 */
	public void setValue(T value) throws HierarchicalPropertyNodeException
		{
		if (!editable)
			{
			throw new HierarchicalPropertyNodeException("Node is locked: " + this);
			}
		setValueForce(value);
		}

	private void setValueForce(T value) throws HierarchicalPropertyNodeException
		{
		boolean destructive = true;
		T currentValue = getValue();
		if (currentValue == null || currentValue == value)
			{
			destructive = false;
			}

		// do any required string parsing, including class names
		/*	if (value instanceof String)
		   {
		   value = parseValueString((String) value);
		   }*/
		//this.value = value;
		payload = new SerializableOrderedPair<S, T>(getKey(), value);

		// REVIEW possible hack re setting PluginMap values on an HTPN


//** All PluginMap functionality needs revisiting
		/*
		if (isPluginMap() && value != null)// value instanceof PluginMap
			{
			throw new HierarchicalPropertyNodeException(
					"Can't set a plugin value on a regular HTPN; need to use the StringNamed version");
			}
		else */
		if (isClassBoundPlugin() && value != null)
			{
			throw new HierarchicalPropertyNodeException(
					"Can't set a plugin value on a regular HTPN; need to use the StringNamed version");
			}
		else
			{
			clearChildren();
			}

		// if the value is a String giving a class name that is not currently loadable, we may yet add children later
		}


	public void registerChild(final HierarchicalTypedPropertyNode<S, T> a)
		{
		assert a instanceof SerializableHierarchicalTypedPropertyNodeImpl;
		childrenByName.put(a.getKey(), a);
		}

	public void unregisterChild(final HierarchicalTypedPropertyNode<S, T> a)
		{
		assert a instanceof SerializableHierarchicalTypedPropertyNodeImpl;
		final S key = a.getKey();
		if (childrenByName.get(key).equals(a))
			{
			childrenByName.remove(key);
			}
		}
	}
