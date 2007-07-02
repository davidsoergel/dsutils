/* $Id$ */

/*
 * Copyright (c) 2001-2007 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
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

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author lorax
 * @version 1.0
 */
public class PluginManager<T>
	{
	// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(PluginManager.class);

	private static ThreadLocal<Map<Class, PluginManager>> _managers_tl = new ThreadLocal<Map<Class, PluginManager>>();

	private Class theInterface;
	private Set<String> registeredPackages = new HashSet<String>();
	private HashMap<String, Class> classes = new HashMap<String, Class>();
	private HashMap<String, T> instances = new HashMap<String, T>();


	// -------------------------- STATIC METHODS --------------------------

	/*
	 * Note plugins are registered only within a thread!
	 */

	public static <T> void registerPackage(String packagename, Class T)
		{
		PluginManager<T> m = getManagerForInterface(T);
		m.registerPackage(packagename);
		}

	public static <T> PluginManager<T> getManagerForInterface(Class T)
		{
		Map<Class, PluginManager> _managers = _managers_tl.get();
		if (_managers == null)
			{
			_managers = new HashMap<Class, PluginManager>();
			_managers_tl.set(_managers);
			}
		PluginManager<T> result = _managers.get(T);
		if (result == null)
			{
			result = new PluginManager<T>(T);
			_managers.put(T, result);
			}
		return result;
		}

	public static <T> T getSingletonByName(Class T, String s) throws PluginException
		{
		return (T) getManagerForInterface(T).getSingletonByName(s);
		}

	public static Class getClassByName(Class T, String s) throws PluginException
		{
		return getManagerForInterface(T).getClassByName(s);
		}

	public static Set<String> getKeySet(Class T) throws PluginException
		{
		return getManagerForInterface(T).getKeySet();
		}

	public static Collection<Class> getPlugins(Class T)
		{
		return getManagerForInterface(T).getValues();
		}

	public Collection<Class> getValues()
		{
		return classes.values();
		}

	public static <T> T getNewInstanceByName(Class T, String s) throws PluginException
		{
		return (T) getManagerForInterface(T).getNewInstanceByName(s);
		}

	// --------------------------- CONSTRUCTORS ---------------------------

	public PluginManager(Class T)
		{
		theInterface = T;
		}

	// -------------------------- OTHER METHODS --------------------------

	public Class getClassByName(String s) throws PluginException
		{
		if (s == null)
			{
			return null;
			}
		Class result = classes.get(s);
		if (result == null)
			{
			System.err.println("Classes: " + classes);
			System.err.println("Classpath: " + System.getProperty("java.class.path"));


			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			System.out.println("contextClassLoader: " + contextClassLoader);

			ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
			System.out.println("systemClassLoader: " + systemClassLoader);

			throw new PluginException("Can't find plugin " + s + ".  Available plugins of type "
					+ theInterface.getSimpleName() + ": \n" + org.apache.commons.lang.StringUtils
					.join(classes.keySet().iterator(), "\n"));
			}
		return result;
		}

	public Set<String> getKeySet()//throws PluginException
		{
		return classes.keySet();
		}

	public T getNewInstanceByName(String s) throws PluginException
		{
		try
			{
			return (T) getClassByName(s).newInstance();
			}
		catch (Exception e)
			{
			throw new PluginException(e);
			}
		}

	public T getSingletonByName(String s) throws PluginException
		{
		T result = instances.get(s);
		if (result == null)
			{
			Class c = classes.get(s);
			if (c != null)
				{
				try
					{
					instances.put(c.getCanonicalName(), (T) c.newInstance());
					result = instances.get(s);
					}
				catch (InstantiationException e)
					{
					logger.debug(e);
					}
				catch (IllegalAccessException e)
					{
					logger.debug(e);
					}
				}
			else
				{
				throw new PluginException("Can't find plugin " + s + ".  Available plugins of type "
						+ theInterface.getSimpleName() + ": \n" + org.apache.commons.lang.StringUtils
						.join(classes.keySet().iterator(), "\n"));
				}
			}
		return result;
		}

	public void registerPackage(String packagename)
		{
		if (!registeredPackages.contains(packagename))
			{
			registeredPackages.add(packagename);
			for (Class c : SubclassFinder.findRecursive(packagename, theInterface))
				{
				classes.put(c.getCanonicalName(), c);
				//put((String)dm.getMethod("getName").invoke(), dm);
				/*try
				{
				classes.put(c.getSimpleName(), c);
				//instances.put(c.getSimpleName(), (T) c.newInstance());
				}
			catch (InstantiationException e)
				{
				logger.debug(e);
				}
			catch (IllegalAccessException e)
				{
				logger.debug(e);
				}*/
				}
			}
		}
	}


