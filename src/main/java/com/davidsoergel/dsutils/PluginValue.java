package com.davidsoergel.dsutils;

import java.io.Serializable;

/**
 * Distinguish a String that names an class from a plain String
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class PluginValue implements Serializable, Comparable
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

	@Override
	public boolean equals(final Object o)
		{
		if (this == o)
			{
			return true;
			}
		if (o == null || getClass() != o.getClass())
			{
			return false;
			}

		final PluginValue that = (PluginValue) o;

		if (value != null ? !value.equals(that.value) : that.value != null)
			{
			return false;
			}

		return true;
		}

	@Override
	public int hashCode()
		{
		return value != null ? value.hashCode() : 0;
		}

	public int compareTo(final Object o)
		{
		return toString().compareTo(o.toString());
		}
	}
