package com.davidsoergel.dsutils.stringmapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class BooleanMapper extends StringMapper<Boolean>
	{
	@NotNull
	public Type[] basicTypes()
		{
		return new Type[]{Boolean.class, boolean.class};
		}

	/**
	 * Return false if the given string is "false", "no", "n", "none", or "0"; return true otherwise. Note this is entirely
	 * different from {@link Boolean#parseBoolean(String)}
	 *
	 * @param s
	 * @return
	 */
	@Nullable
	public Boolean parse(@Nullable String s)
		{
		if (s == null || s.trim().equals("")) //s.trim().isEmpty())   // JDK 1.5 compatibility
			{
			return null;
			}
		s = s.trim();

		return !(s.equals("false") || s.equals("no") || s.equals("n") || s.equals("none") || s.equals("0"));
		}

	public String render(Boolean value)
		{
		return value.toString();
		}
	}
