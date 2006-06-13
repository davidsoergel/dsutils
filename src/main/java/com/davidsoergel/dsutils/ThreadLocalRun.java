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

import java.util.Map;
import java.util.HashMap;

/**
 * Represents one instance of a program, holding its inputs and outputs.
 * The program should be thread-isolated, i.e. containing no non-final
 * static variables, so that multiple instances can coexist in the same JVM.
 * 
 * @author lorax
 * @version 1.0
 */
public abstract class ThreadLocalRun
	{
// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(ThreadLocalRun.class);

	protected static ThreadLocal<Props> _props_tl = new ThreadLocal<Props>();
	protected static ThreadLocal<String> _runId_tl = new ThreadLocal<String>();

	protected static ThreadLocal<Map<String, String>> _results_tl = new ThreadLocal<Map<String, String>>();

// -------------------------- STATIC METHODS --------------------------

	public static String getRunId()
		{
		return (_runId_tl.get());
		}

	public static Props getProps()
		{
		return (_props_tl.get());
		}

	public static Map<String, String> getResults()
		{
		return (_results_tl.get());
		}

// --------------------------- CONSTRUCTORS ---------------------------

	//Properties output = new Properties();
	public ThreadLocalRun(String id, Props p)
		{
		//super();
		_runId_tl.set(id);
		//p.setProperty("runId", id);
		_props_tl.set(p);
		//this.setName(id);
		_results_tl.set(new HashMap());
		}

// -------------------------- OTHER METHODS --------------------------

	//public abstract MonteCarlo newMonteCarlo();  // could make a MonteCarloFactory
	//public abstract MonteCarlo newMonteCarlo(MonteCarlo copyfrom);  // could make a MonteCarloFactory
	public abstract void run() throws RunUnsuccessfulException;
	}

