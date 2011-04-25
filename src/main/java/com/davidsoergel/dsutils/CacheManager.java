package com.davidsoergel.dsutils;

import com.google.common.collect.MapMaker;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
		for (@NotNull AccumulatingMap accumulatingMap : accumulatingMaps.values())
			{
			accumulatingMap.mergeToDisk();
			}
		}


	private static String hashAndVerify(@NotNull final Object source, @NotNull final String idString)
		{
		String idHash = String.valueOf(idString.hashCode());
		@Nullable String cachedString = (String) get(source, idHash + ".idString");

		if (cachedString == null)  // idstring file didn't exist
			{
			CacheManager.put(source, idHash + ".idString", idString); // just to test for hash collisions
			}
		else if (!cachedString.equals(idString))
			{
			throw new Error("Hashcode collision, this is highly unlikely");
			}

		return idHash;
		}

	private static final int MAX_KEY_LENGTH = 100;

	/**
	 * Load a serialized object from disk
	 *
	 * @param source
	 * @param key
	 * @return
	 */
	@Nullable
	public static Serializable get(@NotNull Object source, @NotNull String key)
		{
		if (key.length() > MAX_KEY_LENGTH)  // OR key contains invalid characters?
			{
			key = hashAndVerify(source, key);
			}

		@NotNull String filename = buildFilename(source, key);
		return getFromFile(filename);
		}

	/**
	 * Load a serialized object from disk
	 *
	 * @param classNamePlusKey
	 * @return
	 */
	@Nullable
	public static Serializable get(String classNamePlusKey)
		{
		@NotNull String filename = buildFilename(classNamePlusKey);
		return getFromFile(filename);
		}

	@Nullable
	private static Serializable getFromFile(String filename)
		{
		logger.info("Loading cache: " + filename);
		@Nullable FileInputStream fin = null;
		@Nullable ObjectInputStream ois = null;
		try
			{
			fin = new FileInputStream(filename);
			ois = new CustomClassloaderObjectInputStream(fin, classLoader);
			return (Serializable) ois.readObject();
			}
		catch (FileNotFoundException e)
			{
			logger.debug("Cache not found: " + filename + ", " + e.getMessage());
			}
		catch (IOException e)
			{// no problem
			logger.warn("Error reading cache: " + filename + ", " + e.getMessage());
			}
		catch (ClassNotFoundException e)
			{// no problem
			logger.warn("Error reading cache: " + filename + ", " + e.getMessage());
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
				logger.error("Error", e);
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
				logger.error("Error", e);
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
	public static void put(String classNamePlusKey, Serializable o)
		{
		@NotNull String filename = buildFilename(classNamePlusKey);
		putToFile(filename, o);
		}


	/**
	 * Serialize an object to disk, overwriting if it's already there
	 *
	 * @param source
	 * @param key
	 * @param o
	 */
	public static void put(@NotNull Object source, @NotNull String key, Serializable o)
		{
		if (key.length() > MAX_KEY_LENGTH)  // OR key contains invalid characters?
			{
			key = hashAndVerify(source, key);
			}

		@NotNull String filename = buildFilename(source, key);
		putToFile(filename, o);
		}

	private static void putToFile(String filename, Serializable o)
		{
		@NotNull File cacheFile = new File(filename);
		cacheFile.getParentFile().mkdirs();
		@Nullable FileOutputStream fout = null;
		@Nullable ObjectOutputStream oos = null;
		@Nullable FileChannel channel = null;
		@Nullable FileLock lock = null;

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
				logger.error("Error", e);
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
				logger.error("Error", e);
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
				logger.error("Error", e);
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
				logger.error("Error", e);
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

	public static Map getAccumulatingMapAssumeSerializable(@NotNull Object source,
	                                                       @NotNull String key) //, Map<K, V> prototype)
	{
	return getAccumulatingMap(source, key);
	}

	public static <K extends Serializable, V extends Serializable> Map<K, V> getAccumulatingMap(@NotNull Object source,
	                                                                                            @NotNull String key) //, Map<K, V> prototype)
	{
	if (key.length() > MAX_KEY_LENGTH)  // OR key contains invalid characters?
		{
		key = hashAndVerify(source, key);
		}

	@NotNull String filename = buildFilename(source, key);

	AccumulatingMap<K, V> result = accumulatingMaps.get(filename);
	if (result == null)
		{
		result = new AccumulatingMap<K, V>(filename);
		accumulatingMaps.put(filename, result);
		}

	return result;
	}

	@NotNull
	private static String buildFilename(@NotNull Object source, String key)
		{
		String className = source.getClass().getCanonicalName();
		int i = className.indexOf('$');
		if (i >= 0)
			{
			className = className.substring(0, i);
			}
		return EnvironmentUtils.getCacheRoot() + className + File.separator + key;
		}

	@NotNull
	private static String buildFilename(String classNamePlusKey)
		{
		return EnvironmentUtils.getCacheRoot() + classNamePlusKey;
		}


	private static class AccumulatingMap<K extends Serializable, V extends Serializable> extends HashMap<K, V>
			implements Serializable
		{
		private static final long serialVersionUID = 20090326L;

/*		protected void finalize() throws Throwable
			{
			// we'll only reach this point when this object is being removed from the weak map anyway
			// accumulatingMaps.remove(filename);
			try
				{
				// when we reload a serialized instance, the transient values are null
				// we copied out what we needed anyway though, so there's no need to merge
				if (filename != null)
					{
					mergeToDisk();
					}
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
*/

		@NotNull
		transient Set<K> alteredKeys = new HashSet<K>();
		transient private String filename;

		public AccumulatingMap(String filename)
			{
			this.filename = filename;

			@Nullable Map<K, V> theMap = (Map<K, V>) CacheManager.getFromFile(filename);
			if (theMap != null)
				{

				// too bad we have to copy
				// but not like this, since super.putAll calls the local put which then calls mergeToDisk unnecessarily
				//	super.putAll(theMap);

				for (@NotNull Map.Entry<K, V> entry : theMap.entrySet())
					{
					super.put(entry.getKey(), entry.getValue());
					}
				}
			//clearAlteredKeys();
			}

		@Override
		public synchronized V put(K k, V v)
			{
			alteredKeys.add(k);
			V result = super.put(k, v);

			// write to disk whenever the new items are 10% or more of the existing items
			if ((double) alteredKeys.size() / (double) size() > 0.1)
				{
				mergeToDisk();
				}

			return result;
			}

		@Override
		public synchronized void putAll(@NotNull Map<? extends K, ? extends V> map)
			{
			alteredKeys.addAll(map.keySet());
			super.putAll(map);

			// super.putAll just calls this.put() anyway

			// write to disk whenever the new items are 10% or more of the existing items
//			if ((double) alteredKeys.size() / (double) size() > 0.1)
//				{
//				mergeToDisk();
//				}
			}

		private synchronized void clearAlteredKeys()
			{
			alteredKeys = new HashSet<K>();
			}

		public synchronized void mergeToDisk()
			{
			if (alteredKeys.isEmpty())
				{
				logger.warn("AccumulatingMap did not change: " + filename);
				}
			else
				{
				logger.warn("Writing AccumulatingMap: " + filename + ", " + this.size() + " entries, " + alteredKeys
				            + " new.");

				@Nullable FileOutputStream fout = null;
				@Nullable ObjectOutputStream oos = null;
				@Nullable FileChannel channel = null;
				@Nullable FileLock lock = null;

				try
					{
					// prepare a new file and lock it
					@NotNull File cacheFile = new File(filename + ".new");
					cacheFile.getParentFile().mkdirs();
					fout = new FileOutputStream(cacheFile);
					channel = fout.getChannel();
					lock = channel.lock();

					// load the latest version, without locking
					@NotNull AccumulatingMap<K, V> theMap = (AccumulatingMap<K, V>) CacheManager.get(filename);

					if (theMap == null)
						{
						// it seems inefficient to copy the whole map before writing it out, but this way it's a clean HashMap
						//theMap = new HashMap<K,V>();

						// no problem, just write it as an AccumulatingMap
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
					@NotNull File oldFile = new File(filename);
					if (!oldFile.exists() || oldFile.delete())
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
						logger.error("Error", e);
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
						logger.error("Error", e);
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
						logger.error("Error", e);
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
						logger.error("Error", e);
						}
					}
				}
			}

		private synchronized void defensiveBidirectionalSync(@NotNull AccumulatingMap<K, V> theMap)
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

	/**
	 * Load a serialized object from disk
	 *
	 * @param source
	 * @param key
	 * @return
	 */
	@NotNull
	public static LazyStub getLazy(@NotNull Object source, @NotNull String key)
		{
		if (key.length() > MAX_KEY_LENGTH)  // OR key contains invalid characters?
			{
			key = hashAndVerify(source, key);
			}

		@NotNull String filename = buildFilename(source, key);

		//if (new File(filename).exists())
		//	{
		return new LazyStub(filename);
		//	}
		//else
		//	{
		//	return null;
		//	}
		}

	// this can't usefully be generic because getLazy above has no way of knowing the type (it's not in the arguments, and can't sensibly be)
	public static class LazyStub
		{
		private String filename;
		@Nullable
		private Serializable thing;
		private boolean fileExists;

		public LazyStub(final String filename)
			{
			this.filename = filename;
			this.fileExists = new File(filename).exists();
			}

		public synchronized boolean notCached()
			{
			return !fileExists;
			}

		@Nullable
		public synchronized Serializable get()
			{
			if (thing == null)
				{
				logger.info("Lazy loading triggered for " + filename);
				thing = getFromFile(filename);
				}
			return thing;
			}

		/**
		 * Note this gets committed immediately, so should only be called once the object is fully populated
		 *
		 * @param o
		 */
		public synchronized void put(@Nullable Serializable o)
			{
			if (o == null)
				{
				logger.warn("Trying to put null object into cache stub " + filename);
				}
			else
				{
				thing = o;
				CacheManager.putToFile(filename, o);
				fileExists = true;
				}
			}
		}
	}
