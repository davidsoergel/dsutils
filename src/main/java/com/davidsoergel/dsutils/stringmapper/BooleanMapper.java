package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.StringMapper;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
 */
public class BooleanMapper extends StringMapper<Boolean>
	{
	/**
	 * Return false if the given string is "false", "no", "n", "none", or "0"; return true otherwise. Note this is entirely
	 * different from {@link Boolean#parseBoolean(String)}
	 *
	 * @param d
	 * @return
	 */
	public Boolean parse(String d)
		{
		String p = d.trim();
		if (p.equals(""))
			{
			return null;
			}
		return !(p.equals("false") || p.equals("no") || p.equals("n") || p.equals("none") || p.equals("0"));
		}

	public String render(Boolean value)
		{
		return value.toString();
		}
	}
