/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SortedProperties extends Properties
	{
	@Override
	public synchronized Enumeration keys()
		{
		Enumeration keysEnum = super.keys();
		ArrayList keyList = new ArrayList();
		while (keysEnum.hasMoreElements())
			{
			keyList.add(keysEnum.nextElement());
			}
		Collections.sort(keyList);
		return Collections.enumeration(keyList);
		}
	}
