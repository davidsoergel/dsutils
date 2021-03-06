/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	public boolean equals(@Nullable final Object o)
		{
		if (this == o)
			{
			return true;
			}
		if (o == null || getClass() != o.getClass())
			{
			return false;
			}

		@NotNull final PluginValue that = (PluginValue) o;

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

	public int compareTo(@NotNull final Object o)
		{
		return toString().compareTo(o.toString());
		}
	}
