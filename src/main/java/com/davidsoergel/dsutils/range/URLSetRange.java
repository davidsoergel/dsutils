package com.davidsoergel.dsutils.range;


import java.net.URL;
import java.util.Collection;


/**
 * @version $Id: BooleanSetRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class URLSetRange extends BasicSetRange<URL>
	{
	public URLSetRange(final Collection<URL> values)
		{
		super(values);
		}

	protected URLSetRange create(final Collection<URL> values)
		{
		return new URLSetRange(values);
		}
	}
