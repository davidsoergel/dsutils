package com.davidsoergel.dsutils.htpn;

import com.davidsoergel.dsutils.tree.AbstractHierarchyNode;

import java.io.Serializable;
import java.util.Set;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class AbstractDoubleHierarchicalTypedProperties<I, J, K extends Comparable, V, C extends DoubleHierarchicalTypedProperties<I, J, K, V, C>>

		extends AbstractHierarchyNode<HierarchicalTypedPropertyNode<K, V>, C>

		implements Serializable, DoubleHierarchicalTypedProperties<I, J, K, V, C>
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

	public abstract C newChild();


	public C newChild(HierarchicalTypedPropertyNode<K, V> payload)
		{
		C result = newChild();
		//children.add(result);  // setParent calls registerChild
		result.setParent((C) this);
		result.setPayload(payload);
		return result;
		}
	}
