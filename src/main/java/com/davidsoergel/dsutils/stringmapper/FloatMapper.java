package com.davidsoergel.dsutils.stringmapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class FloatMapper extends StringMapper<Float>
	{
	@NotNull
	public Type[] basicTypes()
		{
		return new Type[]{Float.class, float.class};
		}

	@Nullable
	public Float parse(@Nullable String s)
		{
		if (s == null || s.trim().equals("")) //s.trim().isEmpty())   // JDK 1.5 compatibility
			{
			return null;
			}
		return new Float(s);
		}

	public String render(@NotNull Float value)
		{
		return value.toString();
		}
	}
