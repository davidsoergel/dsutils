package com.davidsoergel.dsutils;

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

	public abstract T parse(String s) throws StringMapperException;

	public abstract String render(T value);

	public abstract Type[] basicTypes();

	public String renderAbbreviated(T s)
		{
		return render(s);// "renderAbbrevated not implemented";
		}

	public String renderHtml(T s)
		{
		return render(s);//"renderHtml not implemented";
		}
	}
