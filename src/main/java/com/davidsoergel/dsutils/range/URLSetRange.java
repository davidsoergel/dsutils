package com.davidsoergel.dsutils.range;


import com.davidsoergel.dsutils.EnumValue;
import org.apache.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class URLSetRange extends BasicSetRange<URL>
	{
	private static final Logger logger = Logger.getLogger(URLSetRange.class);

	// for Hessian

	protected URLSetRange()
		{
		}

	public URLSetRange(final Collection urlValues)
		{
		super(new HashSet<URL>());
		for (Object s : urlValues)
			{
			if (s instanceof String)
				{
				try
					{
					values.add(new URL((String) s));
					}
				catch (MalformedURLException e)
					{
					logger.error("Error", e);
					throw new RangeRuntimeException(e);
					}
				}
			else if (s instanceof EnumValue)
				{
				values.add((URL) s);
				}
			else
				{
				throw new RangeRuntimeException(
						"EnumSetRange must be initialized with EnumValues or Strings, not " + s.getClass());
				}
			}
		}

	protected URLSetRange create(final Collection<URL> values)
		{
		return new URLSetRange(values);
		}

	public Set<String> getStringValues()
		{
		Set<String> result = new HashSet<String>();
		for (URL value : values)
			{
			result.add(value.toString());
			}
		return result;
		}
	}
