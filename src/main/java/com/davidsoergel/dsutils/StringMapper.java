package com.davidsoergel.dsutils;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
 */
public abstract class StringMapper<T>
	{
	public StringMapper()
		{
		super();
		}

	public abstract T parse(String s) throws StringMapperException;

	public abstract String render(T value);

	public String renderAbbreviated(T s)
		{
		return render(s);// "renderAbbrevated not implemented";
		}

	public String renderHtml(T s)
		{
		return render(s);//"renderHtml not implemented";
		}
	}
