/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


/**
 * An Adapter that makes a Properties object appear as a Map<String, Object>.
 * <p/>
 * For historical reasons,java.util.Properties is a Hashtable<Object,Object>but the keys must all be Strings. Here we
 * just enforce that and make the generic stuff work right.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */

public class PropertiesToMapAdapter implements Map<String, Object>
	{
	// ------------------------------ FIELDS ------------------------------

	// For historical reasons, java.util.Properties is a Hashtable<Object, Object> but the keys must all be Strings.
	// Here we just enforce that and make the generic stuff work right.
	private final Properties properties;

	private Set<String> keySetOfStrings;


	private Set<Entry<String, Object>> entrySetOfStrings;


	// --------------------------- CONSTRUCTORS ---------------------------

	public PropertiesToMapAdapter(Properties p)
		{
		properties = p;
		}

	/**
	 * Convenience method to load a standard properties file
	 *
	 * @param filename The properties file to load
	 * @throws IOException If an error is encountered during loading
	 */
	public PropertiesToMapAdapter(String filename) throws IOException
		{
		URL res = ClassLoader.getSystemResource(filename);
		InputStream is;
		if (res != null)
			{
			is = res.openStream();
			}
		else
			{
			is = new FileInputStream(filename);
			}
		if (is == null)
			{
			is = new FileInputStream(filename);
			}
		try
			{
			properties = new Properties();
			properties.load(is);
			}
		finally
			{
			is.close();
			}
		}

	// --------------------- GETTER / SETTER METHODS ---------------------

	public Properties getProperties()
		{
		return properties;
		}

	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface Map ---------------------

	public int size()
		{
		return properties.size();
		}

	public boolean isEmpty()
		{
		return properties.isEmpty();
		}

	public boolean containsKey(Object o)
		{
		return properties.containsKey(o);
		}

	public boolean containsValue(Object o)
		{
		return properties.containsValue(o);
		}

	public Object get(Object o)
		{
		return properties.get(o);
		}

	public Object put(String s, Object o)
		{
		return properties.put(s, o);
		}

	public Object remove(Object o)
		{
		return properties.remove(o);
		}

	public void putAll(Map<? extends String, ? extends Object> map)
		{
		properties.putAll(map);
		}

	public void clear()
		{
		properties.clear();
		}

	public Set<String> keySet()
		{
		if (keySetOfStrings == null)
			{
			keySetOfStrings = new HashSet<String>();
			for (Object o : properties.keySet())
				{
				//let the ClassCastException be, don't bother rethrowing a different type
				keySetOfStrings.add((String) o);
				}
			}
		return keySetOfStrings;
		}

	public Collection<Object> values()
		{
		return properties.values();
		}

	public Set<Entry<String, Object>> entrySet()
		{
		if (entrySetOfStrings == null)
			{
			entrySetOfStrings = new HashSet<Entry<String, Object>>();
			for (Entry o : properties.entrySet())
				{
				//let the ClassCastException be, don't bother rethrowing a different type
				entrySetOfStrings.add(o);// cheating the Generics system, but it should work.  Does it?
				}
			}
		return entrySetOfStrings;
		}
	}
