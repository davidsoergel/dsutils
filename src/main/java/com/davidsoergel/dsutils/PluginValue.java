package com.davidsoergel.dsutils;

import java.io.Serializable;

/**
 * Distinguish a String that names an class from a plain String
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class PluginValue implements Serializable
	{
	final String value;

	public PluginValue(final String value)
		{
		this.value = value;
		}

	public String getValue()
		{
		return value;
		}

	@Override
	public String toString()
		{
		return value;
		}
	}
