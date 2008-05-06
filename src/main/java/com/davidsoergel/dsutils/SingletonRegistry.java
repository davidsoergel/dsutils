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

import java.util.HashMap;

/**
 * Use SingletonRegistry.instance(String) to access a given instance by name.
 */
public abstract class SingletonRegistry
	{
	// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(SingletonRegistry.class);

	static private HashMap _registry = new HashMap();


	// -------------------------- STATIC METHODS --------------------------

	/**
	 * @return The unique instance of the specified class.
	 */
	static public SingletonRegistry instance(String byname) throws ClassNotFoundException
		{
		// byname = byname.toLowerCase();

		logger.debug("SingletonRegistry.instance(\"" + byname + "\")");

		SingletonRegistry result = (SingletonRegistry) (_registry.get(byname.toLowerCase()));

		// make sure the class has been loaded, since it wouldn't yet be in SingletonRegistry's registry otherwise

		if (result == null)
			{
			try
				{
				// ** this doesn't work due to the lowercasing issue!!

				Class.forName(byname).newInstance();

				result = (SingletonRegistry) (_registry.get(byname.toLowerCase()));
				}
			catch (Exception e)
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
		logger.debug("SingletonRegistry registered: " + this.getClass().getName());
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

