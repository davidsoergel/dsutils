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

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lorax
 * @version 1.0
 */
public class PluginManager<T>
	{
	private static Logger logger = Logger.getLogger(PluginManager.class);

	private static Map<Class, PluginManager> _managers = new HashMap<Class, PluginManager>();

	public static <T> PluginManager<T> getManagerForInterface(Class T)
		{
		PluginManager<T> result = _managers.get(T);
		if(result == null)
			{
			result = new PluginManager<T>(T);
			_managers.put(T, result);
			}
		return result;
		}

	public static <T> void registerPackage(String packagename, Class T)
		{
		PluginManager<T> m = getManagerForInterface(T);
		m.registerPackage(packagename);
		}


	public static <T> T getSingletonByName(Class T, String s) throws PluginException
		{
		return (T) getManagerForInterface(T).getSingletonByName(s);
		}


	public static Class getClassByName(Class T, String s) throws PluginException
		{
		return getManagerForInterface(T).getClassByName(s);
		}


	public static <T> T getNewInstanceByName(Class T, String s) throws PluginException
		{
		return (T) getManagerForInterface(T).getNewInstanceByName(s);
		}

	private Class theInterface;
	private HashMap<String, Class> classes = new HashMap<String, Class>();
	private HashMap<String, T> instances = new HashMap<String, T>();

	public PluginManager(Class T)
		{
		theInterface = T;
		}

	public void registerPackage(String packagename)
		{
		for (Class c : SubclassFinder.find(packagename, theInterface))
			{
			//put((String)dm.getMethod("getName").invoke(), dm);
			try
				{
				classes.put(c.getSimpleName(), c);
				instances.put(c.getSimpleName(), (T) c.newInstance());
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
		}


	public T getSingletonByName(String s) throws PluginException
		{
		T result = instances.get(s);
		if (result == null)
			{
			throw new PluginException("Can't find plugin " + s + ".  Available plugins of type " + theInterface.getSimpleName() + ": \n" + org.apache.commons.lang.StringUtils
					.join(instances.keySet().iterator(), "\n"));
			}
		return result;
		}


	public Class getClassByName(String s) throws PluginException
		{
		Class result = classes.get(s);
		if (result == null)
			{
			throw new PluginException("Can't find plugin " + s + ".  Available plugins of type " + theInterface.getSimpleName() + ": \n" + org.apache.commons.lang.StringUtils
					.join(classes.keySet().iterator(), "\n"));
			}
		return result;
		}


	public T getNewInstanceByName(String s) throws PluginException
		{
		try{
		return (T) getClassByName(s).newInstance();
		}
			catch(Exception e)
				{
				throw new PluginException(e);
				}
		}


	}


