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

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// REVIEW  PluginManager may be redundant with other mechanisms for achieving the same thing...

/**
 * A clearinghouse for dynamically loaded plugin classes, i.e. classes that are not necessarily known at compile time
 * but which must be identified and instantiated at runtime.
 * <p/>
 * This may be redundant with other mechanisms for achieving the same thing, in particular the SubclassFinder.  This
 * class basically wraps SubclassFinder and caches the results.
 *
 * @version $Id$
 */
public class PluginManager<T>
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(PluginManager.class);

	private static Map<Type, PluginManager> _managers = new HashMap<Type, PluginManager>();

	private Type theInterface;
	private Set<String> registeredPackages = new HashSet<String>();
	private HashMap<String, Class> classes = new HashMap<String, Class>();
	private HashMap<String, T> instances = new HashMap<String, T>();

	private static Collection<String> defaultPackageNames;

	public static void setDefaultPackageNames(Collection<String> defaultPackageNames)
		{
		PluginManager.defaultPackageNames = defaultPackageNames;
		}

	public static void setDefaultPackageNames(File defaultPackageNameFile) throws IOException
		{
		PluginManager.defaultPackageNames = new HashSet<String>();
		BufferedReader br = new BufferedReader(new FileReader(defaultPackageNameFile));
		for (String line = br.readLine(); line != null; line = br.readLine())
			{
			defaultPackageNames.add(line);
			}
		}
// -------------------------- STATIC METHODS --------------------------


	public static <T> void registerPluginsFromDefaultPackages(Type T, Incrementable incrementor) throws IOException
		{
		if (defaultPackageNames == null || defaultPackageNames.isEmpty())
			{
			logger.error("Can't search for plugins of type " + T + ": no default search packages have been set");
			}
		PluginManager<T> m = getManagerForInterface(T);
		m.registerPackages(defaultPackageNames, incrementor);
		}

	public static <T> void registerPluginsFromPackages(Type T, Collection<String> packagenames,
	                                                   Incrementable incrementor) throws IOException
		{
		PluginManager<T> m = getManagerForInterface(T);
		m.registerPackages(packagenames, incrementor);
		}

	public static <T> void registerPluginsFromPackage(Type T, String packagename, Incrementable incrementor)
			throws IOException
		{
		PluginManager<T> m = getManagerForInterface(T);
		m.registerPackage(packagename, incrementor);
		}

	public static <T> PluginManager<T> getManagerForInterface(Type T)
		{
		//Map<Type, PluginManager> _managers = _managers_tl.get();
		if (_managers == null)
			{
			_managers = new HashMap<Type, PluginManager>();
			//	_managers_tl.set(_managers);
			}
		PluginManager<T> result = _managers.get(T);
		if (result == null)
			{
			result = new PluginManager<T>(T);
			_managers.put(T, result);
			}
		return result;
		}

	public static <T> T getSingletonByName(Type T, String s) throws PluginException
		{
		return (T) getManagerForInterface(T).getSingletonByName(s);
		}

	public static Class getClassByName(Class T, String s) throws PluginException
		{
		return getManagerForInterface(T).getClassByName(s);
		}

	public static Set<String> getKeySet(Type T) throws PluginException
		{
		return getManagerForInterface(T).getKeySet();
		}

	public static Collection<Class> getPlugins(Type T)
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

	public PluginManager(Type T)
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
			logger.trace("Classes: " + classes);
			logger.trace("Classpath: " + System.getProperty("java.class.path"));


			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			logger.trace("contextClassLoader: " + contextClassLoader);

			ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
			logger.trace("systemClassLoader: " + systemClassLoader);

			String name =
					theInterface instanceof Class ? ((Class) theInterface).getSimpleName() : theInterface.toString();
			throw new PluginException(
					"Can't find plugin " + s + ".  Available plugins of type " + name + ": \n" + org.apache.commons.lang
							.StringUtils.join(classes.keySet().iterator(), "\n"));
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
					logger.error("Error", e);
					}
				catch (IllegalAccessException e)
					{
					logger.error("Error", e);
					}
				}
			else
				{
				String name = theInterface instanceof Class ? ((Class) theInterface).getSimpleName() :
						theInterface.toString();
				throw new PluginException(
						"Can't find plugin " + s + ".  Available plugins of type " + name + ": \n" + org.apache.commons
								.lang.StringUtils.join(classes.keySet().iterator(), "\n"));
				}
			}
		return result;
		}

	public void registerPackages(Collection<String> packagenames, Incrementable incrementor) throws IOException
		{
		for (String packagename : packagenames)
			{
			registerPackage(packagename, incrementor);
			}
		}

	public void registerPackage(String packagename, Incrementable incrementor) throws IOException
		{
		if (!registeredPackages.contains(packagename))
			{
			registeredPackages.add(packagename);
			List<Class> found;
			if (theInterface instanceof Class)
				{
				found = SubclassFinder.findRecursive(packagename, (Class) theInterface, incrementor);
				}
			else if (theInterface instanceof ParameterizedType)
				{
				found = SubclassFinder.findRecursive(packagename, (ParameterizedType) theInterface, incrementor);
				}
			else
				{
				throw new Error("Not a Class or a ParameterizedType: " + theInterface);
				}
			for (Class c : found)
				{
				if (!(c.isInterface() || Modifier.isAbstract(c.getModifiers())))
					{
					classes.put(c.getCanonicalName(), c);
					}
				//put((String)dm.getMethod("getName").invoke(), dm);
				/*try
				{
				classes.put(c.getSimpleName(), c);
				//instances.put(c.getSimpleName(), (T) c.newInstance());
				}
			catch (InstantiationException e)
				{
				logger.error(e);
				}
			catch (IllegalAccessException e)
				{
				logger.error(e);
				}*/
				}
			}
		}
	}


