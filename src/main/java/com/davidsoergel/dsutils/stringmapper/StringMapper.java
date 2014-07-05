/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.stringmapper;

import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class StringMapper<T>
	{
	public StringMapper()
		{
		super();
		}

	@Nullable
	public abstract T parse(String s) throws StringMapperException;

	public abstract String render(T value);

	//** basicTypes should be split into parserForTypes and rendererForTypes, since they're different

	public abstract Type[] basicTypes();

	public String renderAbbreviated(T s)
		{
		return render(s);// "renderAbbrevated not implemented";
		}

	public String renderHtml(T s)
		{
		return render(s);//"renderHtml not implemented";
		}

	//public abstract T getNull();
	}
