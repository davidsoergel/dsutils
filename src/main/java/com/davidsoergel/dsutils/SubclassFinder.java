/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils;

import com.davidsoergel.dsutils.increment.Incrementor;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
 * @version $Id$
 */
public class SubclassFinder
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(SubclassFinder.class);

	private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	//ClassLoader.getSystemClassLoader();

	// -------------------------- STATIC METHODS --------------------------

	@NotNull
	public static List<Class> findRecursive(@NotNull String pckgname, @NotNull Class tosubclass,
	                                        @NotNull Incrementor incrementor) throws IOException
		{
		return find(pckgname, tosubclass, true, false, null, incrementor);
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
	@NotNull
	private static List<Class> find(@NotNull String pckgname, @NotNull Class tosubclass, boolean recurse,
	                                boolean includeInterfaces, Class<? extends Annotation> requiredAnnotation,
	                                @NotNull Incrementor incrementor) throws IOException
		{
		return find(pckgname, tosubclass, recurse, includeInterfaces, requiredAnnotation, null, incrementor);
		}

	@NotNull
	public static List<Class> findRecursive(@NotNull String pckgname, @NotNull ParameterizedType tosubclass,
	                                        @NotNull Incrementor incrementor) throws IOException
		{
		//http://www.velocityreviews.com/forums/t524488-raw-type-other-than-a-class-possible.html
		@NotNull Class c = (Class) tosubclass.getRawType();
		return find(pckgname, c, true, false, null, tosubclass, incrementor);
		}

	/**
	 * Display all the classes inheriting or implementing a given class in a given package.
	 *
	 * @param pckgname   the fully qualified name of the package
	 * @param tosubclass the Class object to inherit from
	 */
	@NotNull
	private static List<Class> find(@NotNull String pckgname, @NotNull Class tosubclass, boolean recurse,
	                                boolean includeInterfaces, Class<? extends Annotation> requiredAnnotation,
	                                ParameterizedType requiredParameterizedType, @NotNull Incrementor incrementor)
			throws IOException
	//public static List find(String pckgname, Class tosubclass)
		{
		//Set result = new HashSet();

		@NotNull List result = new ArrayList();
		// Code from JWhich
		// ======
		// Translate the package name into an absolute path
		String name = pckgname;//new String(pckgname);
		/*if (!name.startsWith("/"))
			{
			name = "/" + name;
			}*/
		name = name.replace('.', '/');

		// Get a File object for the package


		logger.trace("Looking for resources: " + name);
		//logger.trace("The first resource found is: " + ClassLoader.getSystemResource(name));
		//try
		//	{
		//e = ClassLoader.getSystemResources(name);
		/*	try
					   {
					   e = tosubclass.getClassLoader().getResources(name);
					   }
				   catch (Throwable e1)
					   {*/
		@NotNull Enumeration e = classLoader.getResources(name);
		//	}
		//	}
		//	catch (IOException e1)
		//		{
		//		logger.debug(e1);
		//		}
		while (e.hasMoreElements())
			{
			@NotNull URL url = (URL) e.nextElement();
			logger.trace("Found resource: " + url);
			result.addAll(find(url, pckgname, tosubclass, recurse, includeInterfaces, requiredAnnotation,
			                   requiredParameterizedType, incrementor));
			}
		return result;
		}

	@NotNull
	public static List<Class> findRecursive(@NotNull String pckgname, @NotNull Class tosubclass,
	                                        Class<? extends Annotation> requiredAnnotation,
	                                        @NotNull Incrementor incrementor) throws IOException
		{
		return find(pckgname, tosubclass, true, false, requiredAnnotation, incrementor);
		}

	@NotNull
	public static List<Class> find(@NotNull String pckgname, @NotNull Class tosubclass,
	                               @NotNull Incrementor incrementor) throws IOException
		{
		return find(pckgname, tosubclass, false, false, null, incrementor);
		}


	@NotNull
	public static List<Class> find(@NotNull String pckgname, @NotNull ParameterizedType tosubclass,
	                               @NotNull Incrementor incrementor) throws IOException
		{
		//http://www.velocityreviews.com/forums/t524488-raw-type-other-than-a-class-possible.html
		@NotNull Class c = (Class) tosubclass.getRawType();
		return find(pckgname, c, false, false, null, tosubclass, incrementor);
		}

	@NotNull
	public static List<Class> find(@NotNull String pckgname, Type tosubclass, @NotNull Incrementor incrementor)
			throws IOException
		{
		if (tosubclass instanceof Class)
			{
			return find(pckgname, (Class) tosubclass, incrementor);
			}

		if (tosubclass instanceof ParameterizedType)
			{
			return find(pckgname, (ParameterizedType) tosubclass, incrementor);
			}

		throw new Error("Unknown Type: " + tosubclass);
		}


	@NotNull
	public static List<Class> find(@NotNull String pckgname, @NotNull Class tosubclass,
	                               Class<? extends Annotation> requiredAnnotation, @NotNull Incrementor incrementor)
			throws IOException
		{
		return find(pckgname, tosubclass, false, false, requiredAnnotation, incrementor);
		}

	@NotNull
	private static List find(@NotNull URL url, String pckgname, @NotNull Class tosubclass, boolean recurse,
	                         boolean includeInterfaces, @Nullable Class<? extends Annotation> requiredAnnotation,
	                         @Nullable ParameterizedType requiredParameterizedType, @NotNull Incrementor incrementor)
			throws IOException
		{
		@NotNull List result = new ArrayList();
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

		@NotNull File directory = new File(url.getFile());

		// New code
		// ======
		if (directory.exists() && directory.isDirectory())
			{
			logger.trace("Directory to check: " + directory);
			// Get the list of the files contained in the package
			String[] files = directory.list();
			incrementor.incrementMaximum(files.length);
			if (files == null)
				{
				//	File test = new File("/bin/sh");
				new FileInputStream("/bin/sh");
				throw new IOException("Could not read directory: " + url);
				}
			logger.trace("Files to check: " + files.length);
			for (int i = 0; i < files.length; i++)
				{
				incrementor.increment();
				// we are only interested in directories and .class files
				logger.trace("Checking: " + url.getFile() + "/" + files[i]);
				if (recurse && new File(url.getFile() + "/" + files[i]).isDirectory())
					//if(files[i].endsWith("/"))
					{
					logger.trace("Recursing into package: " + pckgname + "." + files[i]);
					try
						{
						result.addAll(
								find(new URL(url.toString() + "/" + files[i]), pckgname + "." + files[i], tosubclass,
								     recurse, includeInterfaces, requiredAnnotation, requiredParameterizedType,
								     incrementor));
						}
					catch (MalformedURLException e)
						{
						logger.error("Error", e);
						}
					}
				else if (files[i].endsWith(".class"))
					{
					// removes the .class extension
					String classname = files[i].substring(0, files[i].length() - 6);
					try
						{
						logger.trace("Checking class file: " + pckgname + "." + classname);
						Class c = Class.forName(pckgname + "." + classname, true, classLoader);
						logger.trace("Is " + c.getName() + " an instance of " + tosubclass.getName() + "?");

						if (tosubclass.isAssignableFrom(c) && (includeInterfaces || !(
								Modifier.isAbstract(c.getModifiers()) || c.isInterface())))

						/*Object o = c.newInstance();

						if (tosubclass.isInstance(o))*/
							{
							logger.trace("......YES!");
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
										logger.trace(t);
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
						logger.error("Error", cnfex);
						}
					catch (NoClassDefFoundError cnfex)
						{
						logger.error("Error", cnfex);
						}


					catch (ExceptionInInitializerError ex)
						{
						logger.error("Error", ex.getCause());
						logger.error("Error", ex);
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
				@NotNull JarURLConnection conn = (JarURLConnection) url.openConnection();
				String starts = conn.getEntryName();
				JarFile jfile = conn.getJarFile();
				Enumeration e = jfile.entries();
				while (e.hasMoreElements())
					{
					@NotNull ZipEntry entry = (ZipEntry) e.nextElement();
					incrementor.increment();
					String entryname = entry.getName();

					logger.trace("Checking: " + entryname);
					/*	String shortEntryname;
					 try {shortEntryname= entryname.substring(entryname.lastIndexOf("/"));}
						 catch (IndexOutOfBoundsException j) { shortEntryname = ""; }
					 // we are only interested in directories and .class files
				 if(entryname.startsWith(starts) && entry.isDirectory())
					 {
					 logger.trace("Recursing into package: " + url.toString()+"/"+shortEntryname);
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

							logger.trace("Checking class in jar: " + classname);
							Class c = Class.forName(classname, true, classLoader);
							logger.trace("Is " + c.getName() + " an instance of " + tosubclass.getName() + "?");

							if (tosubclass.isAssignableFrom(c) && (includeInterfaces || !c.isInterface()))

							/*Object o = c.newInstance();

							if (tosubclass.isInstance(o))*/
								{
								logger.trace("......YES!");
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
											logger.trace(t);
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
							logger.error("Error", cnfex);
							}
						catch (ExceptionInInitializerError ex)
							{
							logger.error("Error", ex.getCause());
							logger.error("Error", ex);
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
				logger.error("Error", ioex);
				}
			}
		return result;
		}

	@NotNull
	public static List<Class> findIncludingInterfaces(@NotNull String pckgname, @NotNull Class tosubclass,
	                                                  @NotNull Incrementor incrementor) throws IOException
		{
		return find(pckgname, tosubclass, false, true, null, incrementor);
		}

	@NotNull
	public static List<Class> findIncludingInterfaces(@NotNull String pckgname, @NotNull Class tosubclass,
	                                                  Class<? extends Annotation> requiredAnnotation,
	                                                  @NotNull Incrementor incrementor) throws IOException
		{
		return find(pckgname, tosubclass, false, true, requiredAnnotation, incrementor);
		}

	@NotNull
	public static List<Class> findRecursiveIncludingInterfaces(@NotNull String pckgname, @NotNull Class tosubclass,
	                                                           @NotNull Incrementor incrementor) throws IOException
		{
		return find(pckgname, tosubclass, true, true, null, incrementor);
		}

	@NotNull
	public static List<Class> findRecursiveIncludingInterfaces(@NotNull String pckgname, @NotNull Class tosubclass,
	                                                           Class<? extends Annotation> requiredAnnotation,
	                                                           @NotNull Incrementor incrementor) throws IOException
		{
		return find(pckgname, tosubclass, true, true, requiredAnnotation, incrementor);
		}
	}// RTSI
