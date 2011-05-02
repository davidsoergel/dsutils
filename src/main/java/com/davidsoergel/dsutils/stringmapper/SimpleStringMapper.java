package com.davidsoergel.dsutils.stringmapper;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SimpleStringMapper extends StringMapper<String>
	{
	@NotNull
	public Type[] basicTypes()
		{
		return new Type[]{String.class
		                  //, Enum.class
		};
		}

	public String parse(@NotNull String s)
		{
		return s.trim();
		}

	public String render(@NotNull String value)
		{
		// actually it's sometimes legit and important to retain whitespace on the ends
		return value; //.trim();
		}
	}
