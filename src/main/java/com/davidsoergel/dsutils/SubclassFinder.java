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

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;


/**
 * RTSI.java
 *
 * Created: Wed Jan 24 11:15:02 2001
 *
 */


/**
 * This utility class is looking for all the classes implementing or
 * inheriting from a given interface or class.
 * (RunTime Subclass Identification)
 *
 * @author <a href="mailto:daniel@satlive.org">Daniel Le Berre</a>
 * @version 1.0
 */
public class SubclassFinder
	{
// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(SubclassFinder.class);

// -------------------------- STATIC METHODS --------------------------

	/**
	 * Display all the classes inheriting or implementing a given
	 * class in a given package.
	 *
	 * @param pckgname   the fully qualified name of the package
	 * @param tosubclass the Class object to inherit from
	 */
	public static List<Class> find(String pckgname, Class tosubclass)
	//public static List find(String pckgname, Class tosubclass)
		{
		//Set result = new HashSet();

		List result = new ArrayList();
		// Code from JWhich
		// ======
		// Translate the package name into an absolute path
		String name = new String(pckgname);
		/*if (!name.startsWith("/"))
			{
			name = "/" + name;
			}*/
		name = name.replace('.', '/');

		// Get a File object for the package


		Enumeration e = null;
		logger.debug("Looking for resources: " + name);
		//logger.debug("The first resource found is: " + ClassLoader.getSystemResource(name));
		try
			{
			e = ClassLoader.getSystemResources(name);
			}
		catch (IOException e1)
			{
			logger.debug(e1);
			}
		while (e.hasMoreElements())
			{
			URL url = (URL) e.nextElement();
			logger.debug("Found resource: " + url);
			result.addAll(find(url, pckgname, tosubclass));
			}
		return result;
		}

	private static List find(URL url, String pckgname, Class tosubclass)
		{
		List result = new ArrayList();
		// URL url = tosubclass.getResource(name);
		// URL url = ClassLoader.getSystemClassLoader().getResource(name);

		//System.out.println(name+"->"+url);

		// Happens only if the jar file is not well constructed, i.e.
		// if the directories do not appear alone in the jar file like here:
		//
		//          meta-inf/
		//          meta-inf/manifest.mf
		//          commands/                  <== IMPORTANT
		//          commands/Command.class
		//          commands/DoorClose.class
		//          commands/DoorLock.class
		//          commands/DoorOpen.class
		//          commands/LightOff.class
		//          commands/LightOn.class
		//          RTSI.class
		//

		File directory = new File(url.getFile());

		// New code
		// ======
		if (directory.exists())
			{
			// Get the list of the files contained in the package
			String[] files = directory.list();
			logger.debug("Directory to check: " + directory);
			logger.debug("Files to check: " + files.length);
			for (int i = 0; i < files.length; i++)
				{
				// we are only interested in .class files
				if (files[i].endsWith(".class"))
					{
					// removes the .class extension
					String classname = files[i].substring(0, files[i].length() - 6);
					try
						{
						logger.debug("Checking class file: " + pckgname + "." + classname);
						Class c = Class.forName(pckgname + "." + classname);
						logger.debug("Is " + c.getName() + " an instance of " + tosubclass.getName() + "?");

						if (tosubclass.isAssignableFrom(c))

						/*Object o = c.newInstance();

						if (tosubclass.isInstance(o))*/
							{
							logger.debug("......YES!");
							result.add(c); //System.out.println(classname);
							}
						}
					catch (ClassNotFoundException cnfex)
						{
						logger.debug(cnfex);
						}

					catch (ExceptionInInitializerError ex)
						{
						ex.getCause().printStackTrace();
						ex.printStackTrace();
						logger.debug(ex);
						}
					}
				}
			}
		else
			{
			try
				{
				// It does not work with the filesystem: we must
				// be in the case of a package contained in a jar file.
				JarURLConnection conn = (JarURLConnection) url.openConnection();
				String starts = conn.getEntryName();
				JarFile jfile = conn.getJarFile();
				Enumeration e = jfile.entries();
				while (e.hasMoreElements())
					{
					ZipEntry entry = (ZipEntry) e.nextElement();
					String entryname = entry.getName();
					if (entryname.startsWith(starts) && (entryname.lastIndexOf('/') <= starts.length()) && entryname.endsWith(
					        ".class"))
						{
						String classname = entryname.substring(0, entryname.length() - 6);
						if (classname.startsWith("/"))
							classname = classname.substring(1);
						classname = classname.replace('/', '.');
						try
							{
							// Try to create an instance of the object

							logger.debug("Checking class in jar: " + classname);
							Class c = Class.forName(classname);
							logger.debug("Is " + c.getName() + " an instance of " + tosubclass.getName() + "?");

							if (tosubclass.isAssignableFrom(c))

							/*Object o = c.newInstance();

							if (tosubclass.isInstance(o))*/
								{
								logger.debug("......YES!");
								result.add(c); //System.out.println(classname);
								}
							}
						catch (ClassNotFoundException cnfex)
							{
							logger.debug(cnfex);
							}
						catch (ExceptionInInitializerError ex)
							{
							ex.getCause().printStackTrace();
							ex.printStackTrace();
							logger.debug(ex);
							}
						/*catch (InstantiationException iex)
							{
							// We try to instanciate an interface
							// or an object that does not have a
							// default constructor
							}
						catch (IllegalAccessException iaex)
							{
							// The class is not public
							}*/
						}
					}
				}
			catch (IOException ioex)
				{
				logger.debug(ioex);
				}
			}
		return result;
		}
	}// RTSI
