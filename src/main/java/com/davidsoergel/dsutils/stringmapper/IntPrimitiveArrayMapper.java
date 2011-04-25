package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.DSArrayUtils;
import com.davidsoergel.dsutils.DSStringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class IntPrimitiveArrayMapper extends StringMapper<int[]>
	{
	@NotNull
	public Type[] basicTypes()
		{
		return new Type[]{int[].class};
		}

	public int[] parse(@NotNull String s)
		{
		@NotNull List<Integer> result = new ArrayList<Integer>();
		for (String d : s.split(","))
			{
			result.add(Integer.parseInt(d));
			}
		return DSArrayUtils.toPrimitive(result.toArray(new Integer[]{}));
		}

	public String render(int[] value)
		{
		return DSStringUtils.join(DSArrayUtils.toObject(value), ",");
		}

	public String renderHtml(@Nullable int[] ss)
		{
		if (ss == null || ss.length == 0)
			{
			return "";
			}

		@NotNull StringBuffer sb = new StringBuffer("<html>");
		sb.append("Array of ").append(ss.length).append(" Integers:<P>");
		if (ss.length <= 20)
			{
			for (Integer s : ss)
				{
				sb.append(String.format("%d", s)).append("<BR>");
				}
			}
		else
			{
			//Iterator<Integer> si = ss.iterator();
			for (int i = 0; i < ss.length; i++)
				{
				sb.append(String.format("%d  ", ss[i]));
				if (i % 10 == 0)
					{
					sb.append("<br>");
					}
				if (i == 400)
					{
					sb.append("...");
					break;
					}
				}
			}
		sb.append("</html>");
		return sb.toString();
		}
	}
