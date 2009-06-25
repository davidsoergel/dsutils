package com.davidsoergel.dsutils.swing;

import javax.swing.*;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class ComboBoxWithNoneModel extends DefaultComboBoxModel
	{
	public ComboBoxWithNoneModel(Object[] options)
		{
		super(options);
		insertElementAt("None", 0);
		setSelectedItem("None");
		}
	}
