/*
 * dsutils - a collection of generally useful utility classes
 *
 * Copyright (c) 2001-2006 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * USA.
 *
 */

package com.davidsoergel.dsutils;

import com.martiansoftware.jsap.JSAPResult;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * A simple Properties manager providing casting methods
 * entries in the JSAPResult (if set) override any entries set on this Props directly.
 * @author lorax
 * @version 1.0
 */
public class Props extends Properties
	{
// ------------------------------ FIELDS ------------------------------

	protected static Logger logger = Logger.getLogger(BundleProps.class);
	private JSAPResult jsapResult;

// --------------------------- CONSTRUCTORS ---------------------------

	//private Map<Object, Object> props;

	public Props()
		{
		super();
		}

	public Props(String filename) throws IOException
		{
		//Properties p = new Properties();
		URL res = ClassLoader.getSystemResource(filename);
		InputStream is = res.openStream();
		if(is == null) { is = new FileInputStream(filename); }
		load(is);
		//props = p;
		}

	public Props(Properties p)
		{
		super(p);
		}

// -------------------------- OTHER METHODS --------------------------

	//abstract public String getProperty(String s);
	/** @deprecated */
	public boolean getPropertyBool(String s)
		{
		return getBool(s);
		}

	public boolean getBool(String s)
		{
		String p = getProperty(s);
		if (p == null || p.trim().equals("") || p.trim().equals("false") || p.trim().equals("no") || p.trim().equals("0")) return false;
		return true;
		}

	public String getProperty(String s)
		{
		String result = super.getProperty(s);
		if(result != null) { return result; }
		if(jsapResult != null) { result = jsapResult.getString(s); }
		return result;
		}

	/** @deprecated */
	public double getPropertyDouble(String s)
		{
		return getDouble(s);
		}

	public Double getDouble(String s)
		{
		String p = getProperty(s);
		if (p == null) return Double.NaN;
		return new Double(p);
		}

	/** @deprecated */
	public int getPropertyInt(String s)
		{
		return getInt(s);
		}

	/* throws NullPointerException if not found */
	public int getInt(String s)
		{
		return getInteger(s);
		}

	public Integer getInteger(String s)
		{
		String p = getProperty(s);
		if (p == null) return null;
		return new Integer(p);
		}

	/** @deprecated */
	public Integer getPropertyInteger(String s)
		{
		return getInteger(s);
		}

//
//	/** @deprecated */
//	public void setProperty(String key, String value)
//		{
//		props.put(key, value);
//		}
//
//	/** Does not include JSAPResult entries */
//	public int size()
//		{
//		return props.size();
//		}
//
//	/** Does not include JSAPResult entries */
//	public boolean isEmpty()
//		{
//		return props.isEmpty();
//		}
//
//	/** Does not include JSAPResult entries */
//	public boolean containsKey(Object o)
//		{
//		return props.containsKey(o); // || jsapResult.contains((String)o);
//		}
//
//	/** Does not include JSAPResult entries */
//	public boolean containsValue(Object o)
//		{
//		return props.containsValue(o);
//		}
//
//	/** Does not include JSAPResult entries */
//	public Object get(Object o)
//		{
//		Object result = props.get(o);
//		if(result != null) { return (String)result; }
//		}
//
//	/** Does not include JSAPResult entries */
//	public Object put(String s, Object o)
//		{
//		return props.put(s,o);
//		}
//
//	/** Does not include JSAPResult entries */
//	public Object remove(Object o)
//		{
//		return props.remove(o);
//		}
//
//	/** Does not include JSAPResult entries */
//	public void putAll(Map<? extends String, ? extends Object> map)
//		{
//		props.putAll(map);
//		}
//
//	public void clear()
//		{
//		props.clear();
//		}
//
//	public Set keySet()
//		{
//		return props.keySet();
//		}
//
//	public Collection<Object> values()
//		{
//		return props.values();
//		}
//
//	public Set entrySet()
//		{
//		return props.entrySet();
//		}

	//public Properties getProperties() { return props; }

	public String getString(String s)
		{
		return getProperty(s);
		}

	public String[] getStringArray(String s)
		{
		// ignore any local value for this key
		return jsapResult.getStringArray(s);
		}

	public void setJSAPResult(JSAPResult jsapResult)
		{
		this.jsapResult = jsapResult;
		}
	}
