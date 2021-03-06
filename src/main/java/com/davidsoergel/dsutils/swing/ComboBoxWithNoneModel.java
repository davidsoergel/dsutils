/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

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
