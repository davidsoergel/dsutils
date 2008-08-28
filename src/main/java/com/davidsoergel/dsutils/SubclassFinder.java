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
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * RTSI.java
 *
 * Created: Wed Jan 24 11:15:02 2001
 *
 * Adapted from Daniel le Berre
 * http://www.javaworld.com/javaworld/javatips/jw-javatip113.html
 * http://www.javaworld.com/javatips/javatip113/Tip113.zip
 */


/**
 * This utility class is looking for all the classes implementing or inheriting from a given interface or class.
 * (RunTime Subclass Identification)
 *
 * @author <a href="mailto:daniel@satlive.org">Daniel Le Berre</a>
 * @version 1.0
 */
public class SubclassFinder
	{
	// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(SubclassFinder.class);

	private static ClassLoader classLoader =
			Thread.currentThread().getContextClassLoader();//ClassLoader.getSystemClassLoader();

	// -------------------------- STATIC METHODS --------------------------

	public static List<Class> findRecursive(String pckgname, Class tosubclass) throws IOException
		{
		return find(pckgname, tosubclass, true, false, null);
		}

	public static void setClassLoader(ClassLoader classLoader)
		{
		SubclassFinder.classLoader = classLoader;
		}

	/**
	 * Display all the classes inheriting or implementing a given class in a given package.
	 *
	 * @param pckgname   the fully qualified name of the package
	 * @param tosubclass the Class object to inherit from
	 */
	private static List<Class> find(@NotNull String pckgname, @NotNull Class tosubclass, boolean recurse,
	                                boolean includeInterfaces, Class<? extends Annotation> requiredAnnotation)
			throws IOException
		{
		return find(pckgname, tosubclass, recurse, includeInterfaces, requiredAnnotation, null);
		}

	public static List<Class> findRecursive(String pckgname, ParameterizedType tosubclass) throws IOException
		{
		//http://www.velocityreviews.com/forums/t524488-raw-type-other-than-a-class-possible.html
		Class c = (Class) tosubclass.getRawType();
		return find(pckgname, c, true, false, null, tosubclass);
		}

	/**
	 * Display all the classes inheriting or implementing a given class in a given package.
	 *
	 * @param pckgname   the fully qualified name of the package
	 * @param tosubclass the Class object to inherit from
	 */
	private static List<Class> find(@NotNull String pckgname, @NotNull Class tosubclass, boolean recurse,
	                                boolean includeInterfaces, Class<? extends Annotation> requiredAnnotation,
	                                ParameterizedType requiredParameterizedType) throws IOException
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
		//try
		//	{
		//e = ClassLoader.getSystemResources(name);
		/*	try
					   {
					   e = tosubclass.getClassLoader().getResources(name);
					   }
				   catch (Throwable e1)
					   {*/
		e = classLoader.getResources(name);
		//	}
		//	}
		//	catch (IOException e1)
		//		{
		//		logger.debug(e1);
		//		}
		while (e.hasMoreElements())
			{
			URL url = (URL) e.nextElement();
			logger.debug("Found resource: " + url);
			result.addAll(find(url, pckgname, tosubclass, recurse, includeInterfaces, requiredAnnotation,
			                   requiredParameterizedType));
			}
		return result;
		}

	public static List<Class> findRecursive(String pckgname, Class tosubclass,
	                                        Class<? extends Annotation> requiredAnnotation) throws IOException
		{
		return find(pckgname, tosubclass, true, false, requiredAnnotation);
		}

	public static List<Class> find(String pckgname, Class tosubclass) throws IOException
		{
		return find(pckgname, tosubclass, false, false, null);
		}


	public static List<Class> find(String pckgname, ParameterizedType tosubclass) throws IOException
		{
		//http://www.velocityreviews.com/forums/t524488-raw-type-other-than-a-class-possible.html
		Class c = (Class) tosubclass.getRawType();
		return find(pckgname, c, false, false, null, tosubclass);
		}

	public static List<Class> find(String pckgname, Type tosubclass) throws IOException
		{
		if (tosubclass instanceof Class)
			{
			return find(pckgname, (Class) tosubclass);
			}

		if (tosubclass instanceof ParameterizedType)
			{
			return find(pckgname, (ParameterizedType) tosubclass);
			}

		throw new Error("Unknown Type: " + tosubclass);
		}


	public static List<Class> find(String pckgname, Class tosubclass, Class<? extends Annotation> requiredAnnotation)
			throws IOException
		{
		return find(pckgname, tosubclass, false, false, requiredAnnotation);
		}

	private static List find(URL url, String pckgname, Class tosubclass, boolean recurse, boolean includeInterfaces,
	                         Class<? extends Annotation> requiredAnnotation,
	                         ParameterizedType requiredParameterizedType) throws IOException
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
		if (directory.exists() && directory.isDirectory())
			{
			logger.debug("Directory to check: " + directory);
			// Get the list of the files contained in the package
			String[] files = directory.list();
			if (files == null)
				{
				File test = new File("/bin/sh");
				new FileInputStream("/bin/sh");
				throw new IOException("Could not read directory: " + url);
				}
			logger.debug("Files to check: " + files.length);
			for (int i = 0; i < files.length; i++)
				{
				// we are only interested in directories and .class files
				logger.debug("Checking: " + url.getFile() + "/" + files[i]);
				if (recurse && new File(url.getFile() + "/" + files[i]).isDirectory())
					//if(files[i].endsWith("/"))
					{
					logger.debug("Recursing into package: " + pckgname + "." + files[i]);
					try
						{
						result.addAll(find(new URL(url.toString() + "/" + files[i]), pckgname + "." + files[i],
						                   tosubclass, recurse, includeInterfaces, requiredAnnotation,
						                   requiredParameterizedType));
						}
					catch (MalformedURLException e)
						{
						logger.debug(e);
						}
					}
				else if (files[i].endsWith(".class"))
					{
					// removes the .class extension
					String classname = files[i].substring(0, files[i].length() - 6);
					try
						{
						logger.debug("Checking class file: " + pckgname + "." + classname);
						Class c = Class.forName(pckgname + "." + classname, true, classLoader);
						logger.debug("Is " + c.getName() + " an instance of " + tosubclass.getName() + "?");

						if (tosubclass.isAssignableFrom(c) && (includeInterfaces || !(
								Modifier.isAbstract(c.getModifiers()) || c.isInterface())))

						/*Object o = c.newInstance();

						if (tosubclass.isInstance(o))*/
							{
							logger.debug("......YES!");
							if (requiredAnnotation == null || c.isAnnotationPresent(requiredAnnotation))
								{
								if (requiredParameterizedType == null)
									{
									result.add(c);
									}
								else
									{
									List<Type> types = Arrays.asList(c.getGenericInterfaces());
									for (Type t : types)
										{
										logger.info(t);
										if (TypeUtils.isAssignableFrom(requiredParameterizedType, t))
											{
											result.add(c);
											break;
											}
										}
									}
								}
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

					logger.debug("Checking: " + entryname);
					/*	String shortEntryname;
					 try {shortEntryname= entryname.substring(entryname.lastIndexOf("/"));}
						 catch (IndexOutOfBoundsException j) { shortEntryname = ""; }
					 // we are only interested in directories and .class files
				 if(entryname.startsWith(starts) && entry.isDirectory())
					 {
					 logger.debug("Recursing into package: " + url.toString()+"/"+shortEntryname);
					 try
						 {
						 result.addAll(find(new URL(url.toString()+"/"+shortEntryname), pckgname+"."+entryname, tosubclass));
						 }
					 catch (MalformedURLException ex)
						 {
						 logger.debug(ex);
						 }
					 }
				 else*/
					if (entryname.startsWith(starts) && entryname.endsWith(".class"))
						{
						if (recurse == false && (entryname.lastIndexOf('/') > starts.length()))
							{
							continue;
							}
						;
						String classname = entryname.substring(0, entryname.length() - 6);
						if (classname.startsWith("/"))
							{
							classname = classname.substring(1);
							}
						classname = classname.replace('/', '.');
						try
							{
							// Try to create an instance of the object

							logger.debug("Checking class in jar: " + classname);
							Class c = Class.forName(classname, true, classLoader);
							logger.debug("Is " + c.getName() + " an instance of " + tosubclass.getName() + "?");

							if (tosubclass.isAssignableFrom(c) && (includeInterfaces || !c.isInterface()))

							/*Object o = c.newInstance();

							if (tosubclass.isInstance(o))*/
								{
								logger.debug("......YES!");
								if (requiredAnnotation == null || c.isAnnotationPresent(requiredAnnotation))
									{
									if (requiredParameterizedType == null)
										{
										result.add(c);
										}
									else
										{
										List<Type> types = Arrays.asList(c.getGenericInterfaces());
										for (Type t : types)
											{
											logger.info(t);
											if (TypeUtils.isAssignableFrom(requiredParameterizedType, t))
												{
												result.add(c);
												break;
												}
											}
										}
									}
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

	public static List<Class> findIncludingInterfaces(String pckgname, Class tosubclass) throws IOException
		{
		return find(pckgname, tosubclass, false, true, null);
		}

	public static List<Class> findIncludingInterfaces(String pckgname, Class tosubclass,
	                                                  Class<? extends Annotation> requiredAnnotation) throws IOException
		{
		return find(pckgname, tosubclass, false, true, requiredAnnotation);
		}

	public static List<Class> findRecursiveIncludingInterfaces(String pckgname, Class tosubclass) throws IOException
		{
		return find(pckgname, tosubclass, true, true, null);
		}

	public static List<Class> findRecursiveIncludingInterfaces(@NotNull String pckgname, @NotNull Class tosubclass,
	                                                           Class<? extends Annotation> requiredAnnotation)
			throws IOException
		{
		return find(pckgname, tosubclass, true, true, requiredAnnotation);
		}
	}// RTSI
