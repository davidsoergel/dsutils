package com.davidsoergel.dsutils.htpn;

import com.davidsoergel.dsutils.tree.HierarchyNode;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface DoubleHierarchicalTypedProperties<I, J, K extends Comparable, V, C extends DoubleHierarchicalTypedProperties<I, J, K, V, C, H>, H extends HierarchicalTypedPropertyNode<K, V, H>>

		extends HierarchyNode<HierarchicalTypedPropertyNode<K, V, H>, C>
	{
	I getId1();

	J getId2();

	void setId1(I id1);

	void setId2(J id2);

	H newPayload();
	}
