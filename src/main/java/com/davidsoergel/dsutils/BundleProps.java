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

/* $Id: BundleProps.java,v 1.3 2005/01/31 00:36:41 lorax Exp $ */

package com.davidsoergel.dsutils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Wraps a ResourceBundle.
 *
 * @author David Soergel
 * @version 1.0
 */
public class BundleProps extends Props
	{
// ------------------------------ FIELDS ------------------------------

	Set keySet = null;
	private ResourceBundle bundle;

// --------------------------- CONSTRUCTORS ---------------------------

	public BundleProps(String s)
		{
		super((Properties) null);
		load(s);
		}

	/**
	 * Load the properties
	 *
	 * @param resourcename The name of a valid ResourceBundle
	 * @see ResourceBundle#getBundle(String baseName)
	 */
	public void load(String resourcename)
		{
		// try {

		/*
		 * props = new Properties();
		 * InputStream in = new FileInputStream(resourcename);
		 * //props.getClass().getResourceAsStream(resourcename);
		 * props.load(in);
		 * in.close();
		 *
		 */

		try
			{
			bundle = PropertyResourceBundle.getBundle(resourcename);
			}
		catch (MissingResourceException e)
			{
			ClassLoader c = e.getClass().getClassLoader();

			logger.debug("First Classloader = " + c);
			while (c != null)
				{
				logger.debug("Classloader = " + c);
				c = c.getParent();
				}
			logger.debug(e);
			e.printStackTrace();
			}

		// Util.setEmailHost(props.getString("EmailHost"));
		// Util.setEmailFrom(props.getString("EmailFrom"));
		// Util.setEmailTo(props.getString("EmailTo"));
		// Util.setEmailSubject(props.getString("EmailSubject"));
		logger.info("Loaded properties from " + resourcename + ":");

		logger.debug(bundle);

		/*
		 * } catch (IOException e) {
		 * logger.error(e);
		 * }
		 */
		}

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface Map ---------------------

	public Set keySet()
		{
		if (keySet != null) return keySet;

		keySet = new HashSet();
		Enumeration e = bundle.getKeys();
		while (e.hasMoreElements())
			{
			String s = (String) e.nextElement();
			keySet.add(s);
			}
		return keySet;
		}

// -------------------------- OTHER METHODS --------------------------

	/**
	 * Get a property from the bundle
	 *
	 * @param k The key to look up
	 * @return The value for the given key
	 * @see java.util.ResourceBundle#getString(String key)
	 */
	public String getProperty(String k)
		{
		try
			{
			if (bundle == null)
				{
				throw new IOException("Properties not yet loaded!");
				}

			String s = bundle.getString(k);
			int hashpos = s.indexOf("#");
			if (hashpos >= 0)
				{
				s = s.substring(0, hashpos);
				}
			return s;
			}
		catch (MissingResourceException e)
			{
			logger.warn("Could not find property: " + k);
			}
		catch (Exception e)
			{
			logger.error(e, e);
			System.exit(0);
			}

		return null;
		}

	/**
	 * Check whether the properties have been loaded
	 *
	 * @return true if the properties have been loaded
	 */
	public boolean isLoaded()
		{
		return bundle != null;
		}
	}