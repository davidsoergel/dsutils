/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * http://forums.sun.com/thread.jspa?threadID=5163767
 *
 * @version $Id$
 */
public class CustomClassloaderObjectInputStream extends ObjectInputStream
	{
	private ClassLoader classLoader;

	public CustomClassloaderObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException
		{
		super(in);
		this.classLoader = classLoader;
		}

	protected Class<?> resolveClass(@NotNull ObjectStreamClass desc) throws ClassNotFoundException
		{
		return Class.forName(desc.getName(), false, classLoader);
		}
	}
