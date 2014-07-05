/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.swing;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class ListWithNoneModel extends DefaultListModel
	{
	public ListWithNoneModel(@NotNull Object[] options)
		{
		super();
		addElement("None");
		for (Object o : options)
			{
			addElement(o);
			}
		}
	}
