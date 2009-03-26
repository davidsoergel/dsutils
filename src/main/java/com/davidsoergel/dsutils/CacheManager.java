package com.davidsoergel.dsutils;

import com.google.common.collect.MapMaker;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class CacheManager
	{
	private static final Logger logger = Logger.getLogger(CacheManager.class);
	private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//ClassLoader.getSystemClassLoader();

	public static void setClassLoader(ClassLoader classLoader)
		{
		CacheManager.classLoader = classLoader;
		}

	static
		{
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		public void run()
			{
			logger.debug("Running cleanup thread");
			try
				{
				syncAccumulatingMaps();
				}
			catch (Exception e)
				{
				logger.error("Error", e);
				}
			}
		});
		}

	/**
	 * ideally this is not necessary because each map finalizes itself
	 */
	public static void syncAccumulatingMaps()
		{
		for (AccumulatingMap accumulatingMap : accumulatingMaps.values())
			{
			accumulatingMap.mergeToDisk();
			}
		}

	/**
	 * Load a serialized object from disk
	 *
	 * @param source
	 * @param key
	 * @return
	 */
	public static Object get(Object source, String key)
		{
		String filename = buildFilename(source, key);
		return getFromFile(filename);
		}

	/**
	 * Load a serialized object from disk
	 *
	 * @param classNamePlusKey
	 * @return
	 */
	public static Object get(String classNamePlusKey)
		{
		String filename = buildFilename(classNamePlusKey);
		return getFromFile(filename);
		}

	private static Object getFromFile(String filename)
		{
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try
			{
			fin = new FileInputStream(filename);
			ois = new CustomClassloaderObjectInputStream(fin, classLoader);
			return ois.readObject();
			}
		catch (FileNotFoundException e)
			{
			logger.debug("Cache not found: " + filename, e);
			}
		catch (IOException e)
			{// no problem
			logger.warn("Error reading cache: " + filename, e);
			}
		catch (ClassNotFoundException e)
			{// no problem
			logger.warn("Error reading cache: " + filename, e);
			}
		finally
			{
			try
				{
				if (ois != null)
					{
					ois.close();
					}
				}
			catch (IOException e)
				{
				logger.error(e);
				}
			try
				{
				if (fin != null)
					{
					fin.close();
					}
				}
			catch (IOException e)
				{
				logger.error(e);
				}
			}

		return null;
		}

	/**
	 * Serialize an object to disk, overwriting if it's already there
	 *
	 * @param classNamePlusKey
	 * @param o
	 */
	public static void put(String classNamePlusKey, Object o)
		{
		String filename = buildFilename(classNamePlusKey);
		putToFile(filename, o);
		}


	/**
	 * Serialize an object to disk, overwriting if it's already there
	 *
	 * @param source
	 * @param key
	 * @param o
	 */
	public static void put(Object source, String key, Object o)
		{
		String filename = buildFilename(source, key);
		putToFile(filename, o);
		}

	private static void putToFile(String filename, Object o)
		{
		File cacheFile = new File(filename);
		cacheFile.getParentFile().mkdirs();
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		FileChannel channel = null;
		FileLock lock = null;

		try
			{
			fout = new FileOutputStream(cacheFile);
			channel = fout.getChannel();
			lock = channel.lock();

			oos = new ObjectOutputStream(fout);
			oos.writeObject(o);
			}
		catch (FileNotFoundException e)
			{
			logger.warn("Can't write cache, file not found: " + filename, e);
			}
		catch (IOException e)
			{
			logger.warn("Can't write cache: " + filename, e);
			}
		finally
			{
			try
				{
				if (lock != null)
					{
					lock.release();
					}
				}
			catch (IOException e)
				{
				logger.error(e);
				}
			try
				{
				if (channel != null)
					{
					channel.close();
					}
				}
			catch (IOException e)
				{
				logger.error(e);
				}

			// the closed channel maybe already closes the associated streams??

			try
				{
				if (oos != null)
					{
					oos.close();
					}
				}
			catch (IOException e)
				{
				logger.error(e);
				}
			try
				{
				if (fout != null)
					{
					fout.close();
					}
				}
			catch (IOException e)
				{
				logger.error(e);
				}
			}
		}

	/**
	 * Merge the given map with an existing map on disk, enforcing that there are no disagreements.  Locks the disk version
	 * for the duration.
	 *
	 * @param source
	 * @param key
	 * @param o
	 */
/*	public static void merge(Object source, String key, Map o)
		{
		String filename = EnvironmentUtils.getCacheRoot() + source.getClass().getCanonicalName() + File.separator + key;
		}*/

	static ConcurrentMap<String, AccumulatingMap> accumulatingMaps = new MapMaker().weakValues().makeMap();

	public static <K, V> Map<K, V> getAccumulatingMap(Object source, String key, Map<K, V> prototype)
		{
		String filename = buildFilename(source, key);

		AccumulatingMap<K, V> result = accumulatingMaps.get(filename);
		if (result == null)
			{
			result = new AccumulatingMap<K, V>(filename);
			accumulatingMaps.put(filename, result);
			}

		return result;
		}

	private static String buildFilename(Object source, String key)
		{
		String className = source.getClass().getCanonicalName();
		int i = className.indexOf('$');
		if (i >= 0)
			{
			className = className.substring(0, i);
			}
		return EnvironmentUtils.getCacheRoot() + className + File.separator + key;
		}

	private static String buildFilename(String classNamePlusKey)
		{
		return EnvironmentUtils.getCacheRoot() + classNamePlusKey;
		}


	private static class AccumulatingMap<K, V> extends HashMap<K, V> implements Serializable
		{
		protected void finalize() throws Throwable
			{
			// we'll only reach this point when this object is being removed from the weak map anyway
			// accumulatingMaps.remove(filename);
			try
				{
				mergeToDisk();
				}
			catch (Throwable e)
				{

				logger.error("Error", e);
				}
			finally
				{
				super.finalize();
				}
			}


		transient Set<K> alteredKeys = new HashSet<K>();
		transient private String filename;

		public AccumulatingMap(String filename)
			{
			this.filename = filename;

			Map<K, V> theMap = (Map<K, V>) CacheManager.get(filename);
			if (theMap != null)
				{
				super.putAll(theMap); // avoid messing with alteredKeys
				}
			}

		@Override
		public V put(K k, V v)
			{
			alteredKeys.add(k);
			return super.put(k, v);
			}

		@Override
		public void putAll(Map<? extends K, ? extends V> map)
			{
			alteredKeys.addAll(map.keySet());
			super.putAll(map);
			}

		public void clearAlteredKeys()
			{
			alteredKeys = new HashSet<K>();
			}

		public void mergeToDisk()
			{
			if (alteredKeys.isEmpty())
				{
				logger.warn("AccumulatingMap did not change: " + filename);
				}
			else
				{
				logger.warn("Writing AccumulatingMap: " + filename);

				FileOutputStream fout = null;
				ObjectOutputStream oos = null;
				FileChannel channel = null;
				FileLock lock = null;

				try
					{
					// prepare a new file and lock it
					File cacheFile = new File(filename + ".new");
					cacheFile.getParentFile().mkdirs();
					fout = new FileOutputStream(cacheFile);
					channel = fout.getChannel();
					lock = channel.lock();

					// load the latest version, without locking
					Map<K, V> theMap = (Map<K, V>) CacheManager.get(filename);

					if (theMap == null)
						{
						// it seems inefficient to copy the whole map before writing it out, but this way it's a clean HashMap
						//theMap = new HashMap<K,V>();

						// no problem, just write it as av AccumulatingMap
						theMap = this;
						}
					else
						{
						// merge the local changes into the disk version
						defensiveBidirectionalSync(theMap);
						}

					// write the merged map (or just this, if this is the first time)
					oos = new ObjectOutputStream(fout);
					oos.writeObject(theMap);

					// replace the old file with the new one
					File oldFile = new File(filename);
					if (oldFile.delete())
						{
						if (cacheFile.renameTo(oldFile))
							{
							// great, it's done
							clearAlteredKeys();
							}
						else
							{
							logger.warn("Can't rename merged cached file " + cacheFile + " to " + oldFile);
							}
						}
					else
						{
						logger.warn("Can't delete existing cache: " + oldFile);
						}
					}
				catch (FileNotFoundException e)
					{
					logger.warn("Can't merge cache, file not found: " + filename, e);
					}
				catch (IOException e)
					{
					logger.warn("Can't merge cache: " + filename, e);
					}
				finally
					{
					try
						{
						if (lock != null)
							{
							lock.release();
							}
						}
					catch (IOException e)
						{
						logger.error(e);
						}
					try
						{
						if (channel != null)
							{
							channel.close();
							}
						}
					catch (IOException e)
						{
						logger.error(e);
						}

					// the closed channel maybe already closes the associated streams??

					try
						{
						if (oos != null)
							{
							oos.close();
							}
						}
					catch (IOException e)
						{
						logger.error(e);
						}
					try
						{
						if (fout != null)
							{
							fout.close();
							}
						}
					catch (IOException e)
						{
						logger.error(e);
						}
					}
				}
			}

		private void defensiveBidirectionalSync(@NotNull Map<K, V> theMap)
			{
			for (K alteredKey : alteredKeys)
				{
				V cachedValue = theMap.get(alteredKey);
				V newValue = get(alteredKey);
				if (cachedValue != null)
					{
					// apparently the same entry was made concurrently in another process
					if (!cachedValue.equals(newValue))
						{
						throw new Error("Caches out of sync: " + filename + " for key " + alteredKey);
						}
					// else no problem, the values match so leave the map alone
					}
				else
					{
					theMap.put(alteredKey, newValue);
					}
				}

			// now theMap is canonical; may as well refresh the local cache with any new items from the disk version
			clear();
			putAll(theMap);
			}
		}
	}
