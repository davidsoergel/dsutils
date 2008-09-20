/*
 * Copyright (c) 2001-2008 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * dev@davidsoergel.com
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the names of any contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
 * @author <a href="mailto:dev.davidsoergel.com">David Soergel</a>
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
