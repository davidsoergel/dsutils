package com.davidsoergel.dsutils.dhtpn;

import com.davidsoergel.dsutils.htpn.HierarchicalTypedPropertyNode;
import com.davidsoergel.dsutils.tree.AbstractHierarchyNode;

import java.io.Serializable;
import java.util.Set;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class AbstractDoubleHierarchicalTypedProperties<I, J, K extends Comparable, V, C extends DoubleHierarchicalTypedProperties<I, J, K, V, C, H>, H extends HierarchicalTypedPropertyNode<K, V, H>>

		extends AbstractHierarchyNode<HierarchicalTypedPropertyNode<K, V, H>, C>

		implements Serializable, DoubleHierarchicalTypedProperties<I, J, K, V, C, H>
	{
	private I id1;
	private J id2;

	public I getId1()
		{
		return id1;
		}

	public void setId1(final I id1)
		{
		this.id1 = id1;
		}

	public J getId2()
		{
		return id2;
		}

	public void setId2(final J id2)
		{
		this.id2 = id2;
		}

	public void registerChild(C child)
		{
		getChildren().add(child);
		}

	public void unregisterChild(C child)
		{
		getChildren().remove(child);
		}

	public abstract Set<C> getChildren();

	//public abstract C newChild();

	public abstract C newChild(HierarchicalTypedPropertyNode<K, V, H> payload);
	/*
	public C newChild(HierarchicalTypedPropertyNode<K, V, H> payload)
		{
		C result = newChild();
		//children.add(result);  // setParent calls registerChild
		//result.setParent((C) this);
		result.setPayload(payload);
		return result;
		}
		*/

	public String toString()
		{
		return getId1() + " (" + getId2() + ")";
		}

	public int countInnerValues()
		{
		int result = 0;
		for (C c : this)
			{
			result += c.getPayload().countDescendantsIncludingThis();
			}
		return result;
		}
	}