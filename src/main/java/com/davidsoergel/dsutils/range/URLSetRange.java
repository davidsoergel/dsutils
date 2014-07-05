/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.range;


import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class URLSetRange extends AbstractSetRange<URL>
	{
	private static final Logger logger = Logger.getLogger(URLSetRange.class);

	// for Hessian

	protected URLSetRange()
		{
		}

	public URLSetRange(@NotNull final Collection urlValues)
		{
		super(new HashSet<URL>());
		for (@NotNull Object s : urlValues)
			{
			if (s instanceof String)
				{
				try
					{
					values.add(new URL((String) s));
					}
				catch (MalformedURLException e)
					{
					logger.error("Error", e);
					throw new RangeRuntimeException(e);
					}
				}
			else if (s instanceof URL)
				{
				values.add((URL) s);
				}
			else
				{
				throw new RangeRuntimeException(
						"URLSetRange must be initialized with URLs or Strings, not " + s.getClass());
				}
			}
		}

	@NotNull
	protected URLSetRange create(@NotNull final Collection<URL> values)
		{
		return new URLSetRange(values);
		}

	@NotNull
	public Set<String> getStringValues()
		{
		@NotNull Set<String> result = new HashSet<String>();
		for (@NotNull URL value : values)
			{
			result.add(value.toString());
			}
		return result;
		}
	}
