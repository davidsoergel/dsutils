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
