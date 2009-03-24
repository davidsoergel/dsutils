package com.davidsoergel.dsutils.swing.jmultilist;

import javax.swing.event.ChangeListener;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface MultiListModel<T>
	{
	public void setLists(Map<T, Collection> lists);

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

	Map<T, Collection> getSelections();

	Map<T, Collection> getLists();

	void updateSelections(T key, Collection selectedValues);

	SortedSet getSelectedKeys();
	}
