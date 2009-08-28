package com.davidsoergel.dsutils.tree.dhtpn;

import com.davidsoergel.dsutils.tree.htpn.BasicHierarchicalTypedPropertyNode;

import java.io.Serializable;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class AbstractSerializableDoubleHierarchicalTypedProperties<S extends SerializableDoubleHierarchicalTypedProperties<S>>
		extends
		AbstractDoubleHierarchicalTypedProperties<Integer, String, String, Serializable, S, BasicHierarchicalTypedPropertyNode<String, Serializable>>
		implements SerializableDoubleHierarchicalTypedProperties<S>
	{
	}
