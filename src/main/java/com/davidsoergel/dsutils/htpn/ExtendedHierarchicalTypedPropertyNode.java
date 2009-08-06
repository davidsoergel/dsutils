package com.davidsoergel.dsutils.htpn;

import com.davidsoergel.dsutils.Incrementable;

import java.util.Map;
import java.util.SortedSet;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface ExtendedHierarchicalTypedPropertyNode<K extends Comparable, V, H extends ExtendedHierarchicalTypedPropertyNode<K, V, H>>
		extends HierarchicalTypedPropertyNode<K, V, H>
	{
	Map<K, H> getChildrenByName();

	V getDefaultValue();

	String getHelpmessage();

	SortedSet<Class> getPluginOptions(Incrementable incrementor);

	void setDefaultAndNullable(V defaultValue, boolean isNullable) throws HierarchicalPropertyNodeException;

	void useDefaultValueIfNeeded() throws HierarchicalPropertyNodeException;

	void defaultValueSanityChecks() throws HierarchicalPropertyNodeException;

	boolean isEditable();

	boolean isNullable();

	boolean isObsolete();

	boolean isChanged();

//	boolean isInherited();

	void setChanged(boolean changed);

	//void setDefaultValue(V defaultValue) throws HierarchicalPropertyNodeException;

	void setEditable(boolean editable);

	void setHelpmessage(String helpmessage);

//	void setNullable(boolean nullable) throws HierarchicalPropertyNodeException;

	void setObsolete(boolean b);

	//void setParent(HierarchicalTypedPropertyNode<S, T> key);

	void removeObsoletes();
	}
