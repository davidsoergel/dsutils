package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.StringMapper;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
 */
public class SimpleStringMapper extends StringMapper<String>
	{
	public String parse(String s)
		{
		return s.trim();
		}

	public String render(String value)
		{
		return value.trim();
		}
	}
