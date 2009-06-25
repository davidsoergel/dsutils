package com.davidsoergel.dsutils.swing;

import javax.swing.*;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class ListWithNoneModel extends DefaultListModel
	{
	public ListWithNoneModel(Object[] options)
		{
		super();
		addElement("None");
		for (Object o : options)
			{
			addElement(o);
			}
		}
	}
