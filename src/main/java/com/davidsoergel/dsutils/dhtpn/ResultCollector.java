package com.davidsoergel.dsutils.dhtpn;

import com.davidsoergel.dsutils.htpn.ExtendedHierarchicalTypedPropertyNodeImpl;

import java.io.Serializable;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface ResultCollector extends
                                 DoubleHierarchicalTypedProperties<Integer, String, String, Serializable, ResultCollector, ExtendedHierarchicalTypedPropertyNodeImpl<String, Serializable>>
		//BasicDoubleHierarchicalTypedProperties<ResultCollector>
	{
	}
