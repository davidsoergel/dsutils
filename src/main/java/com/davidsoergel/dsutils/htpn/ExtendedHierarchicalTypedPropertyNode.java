package com.davidsoergel.dsutils.htpn;

import com.davidsoergel.dsutils.Incrementable;

import java.util.Map;
import java.util.SortedSet;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface ExtendedHierarchicalTypedPropertyNode<S extends Comparable, T, H extends ExtendedHierarchicalTypedPropertyNode<S, T, H>>
		extends HierarchicalTypedPropertyNode<S, T, H>
	{
	Map<S, H> getChildrenByName();

	T getDefaultValue();

	String getHelpmessage();

	SortedSet<Class> getPluginOptions(Incrementable incrementor);

	void useDefaultValueIfNeeded() throws HierarchicalPropertyNodeException;

	void defaultValueSanityChecks() throws HierarchicalPropertyNodeException;

	boolean isEditable();

	boolean isNullable();

	boolean isObsolete();

	boolean isChanged();

//	boolean isInherited();

	void setChanged(boolean changed);

	void setDefaultValue(T defaultValue) throws HierarchicalPropertyNodeException;

	void setEditable(boolean editable);

	void setHelpmessage(String helpmessage);

	void setNullable(boolean nullable) throws HierarchicalPropertyNodeException;

	void setObsolete(boolean b);

	//void setParent(HierarchicalTypedPropertyNode<S, T> key);

	void removeObsoletes();
	}
