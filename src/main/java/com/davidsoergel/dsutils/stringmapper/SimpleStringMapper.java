package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.StringMapper;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SimpleStringMapper extends StringMapper<String>
	{
	public Type[] basicTypes()
		{
		return new Type[]{
				String.class
				//, Enum.class
		};
		}

	public String parse(String s)
		{
		return s.trim();
		}

	public String render(String value)
		{
		return value.trim();
		}
	}
