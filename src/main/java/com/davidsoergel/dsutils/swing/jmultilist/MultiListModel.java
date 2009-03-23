package com.davidsoergel.dsutils.swing.jmultilist;

import javax.swing.event.ChangeListener;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface MultiListModel
	{
	public void setLists(Map<Object, Collection> lists);

	//public int getListCount();

	//public List getList(String key);


	/**
	 * Adds a ChangeListener to the model's listener list.
	 *
	 * @param x the ChangeListener to add
	 * @see #removeChangeListener
	 */
	void addChangeListener(ChangeListener x);

	/**
	 * Removes a ChangeListener from the model's listener list.
	 *
	 * @param x the ChangeListener to remove
	 * @see #addChangeListener
	 */
	void removeChangeListener(ChangeListener x);

	Map<Object, Collection> getSelections();

	Map<Object, Collection> getLists();

	void updateSelections(Object key, Collection selectedValues);

	Set getSelectedKeys();
	}
