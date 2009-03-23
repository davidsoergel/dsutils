package com.davidsoergel.dsutils.swing.jmultilist;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Provides both the data model and the selection model for a st of named multi-selectable lists.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class DefaultMultiListModel implements MultiListModel
	{
	Map<Object, Collection> theValuesPerKey;

	Map<Object, Collection> theSelections;


	/**
	 * The listeners waiting for model changes.
	 */
	protected EventListenerList listenerList = new EventListenerList();


	public void addChangeListener(ChangeListener l)
		{
		listenerList.add(ChangeListener.class, l);
		}


	public void removeChangeListener(ChangeListener l)
		{
		listenerList.remove(ChangeListener.class, l);
		}

	/**
	 * Runs each <code>ChangeListener</code>'s <code>stateChanged</code> method.
	 */
	protected void fireStateChanged()
		{
		ChangeEvent event = new ChangeEvent(this);
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
			{
			if (listeners[i] == ChangeListener.class)
				{
				((ChangeListener) listeners[i + 1]).stateChanged(event);
				}
			}
		}

	/**
	 * Returns an array of all the change listeners registered on this <code>DefaultBoundedRangeModel</code>.
	 *
	 * @return all of this model's <code>ChangeListener</code>s or an empty array if no change listeners are currently
	 *         registered
	 * @see #addChangeListener
	 * @see #removeChangeListener
	 */
	public ChangeListener[] getChangeListeners()
		{
		return (ChangeListener[]) listenerList.getListeners(ChangeListener.class);
		}


	public DefaultMultiListModel()
		{
		}

	public void setLists(Map<Object, Collection> theLists)
		{
		//** this is not a defensive copy; changes to the original list can mess with the UI
		theValuesPerKey = theLists;
		theSelections = new HashMap<Object, Collection>();
		for (Object s : theLists.keySet())
			{
			// selections start off empty
			theSelections.put(s, new HashSet());
			}
		fireStateChanged();
		}

	public Map<Object, Collection> getLists()
		{
		return theValuesPerKey;
		}

	public Map<Object, Collection> getSelections()
		{
		return theSelections;
		}

	public Set<Object> getSelectedKeys()
		{
		Set<Object> result = new HashSet<Object>();
		for (Map.Entry<Object, Collection> entry : theSelections.entrySet())
			{
			if (!entry.getValue().isEmpty())
				{
				result.add(entry.getKey());
				}
			}
		return result;
		}

	public void updateSelections(Object key, Collection selectedValues)
		{
		theSelections.put(key, selectedValues);
		fireStateChanged();
		}
	}
