package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.StringMapper;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
 */
public class IntegerMapper extends StringMapper<Integer>
	{
	public Integer parse(String s)
		{
		return new Integer(s);
		}

	public String render(Integer value)
		{
		return value.toString();
		}
	}
