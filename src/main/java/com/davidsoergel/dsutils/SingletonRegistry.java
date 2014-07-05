/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Use SingletonRegistry.instance(String) to access a given instance by name.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
@Deprecated
public abstract class SingletonRegistry
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(SingletonRegistry.class);

	@NotNull
	static private HashMap _registry = new HashMap();

	@Nullable
	private static ClassLoader classLoader = null;

	// -------------------------- STATIC METHODS --------------------------

	/**
	 * @return The unique instance of the specified class.
	 */
	@NotNull
	static public SingletonRegistry instance(@NotNull String byname) throws ClassNotFoundException
		{
		// byname = byname.toLowerCase();

		logger.trace("SingletonRegistry.instance(\"" + byname + "\")");

		@Nullable SingletonRegistry result = (SingletonRegistry) (_registry.get(byname.toLowerCase()));

		// make sure the class has been loaded, since it wouldn't yet be in SingletonRegistry's registry otherwise

		if (result == null)
			{
			try
				{
				// (ignore, deprecated) BAD this doesn't work due to the lowercasing issue!!

				if (classLoader != null)
					{
					Class.forName(byname, true, classLoader).newInstance();
					}
				else
					{
					Class.forName(byname).newInstance();
					}

				result = (SingletonRegistry) (_registry.get(byname.toLowerCase()));
				}
			catch (InstantiationException e)
				{
				throw new ClassNotFoundException(
						"Couldn't find class " + byname + ".  Check to make sure it was preloaded.");
				}
			catch (IllegalAccessException e)
				{
				throw new ClassNotFoundException(
						"Couldn't find class " + byname + ".  Check to make sure it was preloaded.");
				}
			}

		return result;
		}

	// --------------------------- CONSTRUCTORS ---------------------------

	/**
	 * The constructor could be made private to prevent others from instantiating this class. But this would also make it
	 * impossible to create instances of SingletonRegistry subclasses.
	 */
	protected SingletonRegistry()
		{
		logger.trace("SingletonRegistry registered: " + this.getClass().getName());
		_registry.put(this.getClass().getName().toLowerCase(), this);

		// _registry.put(this.getClass().getName(), this);
		}

	// ------------------------ CANONICAL METHODS ------------------------

	/**
	 * Method declaration
	 *
	 * @return
	 * @see
	 */
	public String toString()
		{
		return this.getClass().getName();
		}
	}

