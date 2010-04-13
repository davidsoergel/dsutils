package com.davidsoergel.dsutils.tuples;

/**
 * This is basically a quick and dirty in-memory database.  Also, because it can be indexed, it's sort of a
 * bidirectional MultiKeyMap.
 * <p/>
 * Data are represented as independent points that contain a value for all relevant dimensions. That is, there is no
 * concept of "series"; the column and row positions of a given number are just more values. that way we can split and
 * filter based on those later.
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class TupleStoreImpl //implements TupleStore
	{
//	private static final Logger logger = Logger.getLogger(TupleStoreImpl.class);
//
//	//** Some thoughts: an indexing store could wrap an explicit store
//	//** function computation should be lazy, but will be needed to make an index, which in turn is needed to select on that dimension.
//	//** all of this would be cleaner with more indirection, but the point is to make it fast...
//	//** Could apply a streaming filter so as to load from disk only the passing points/dimensions
//
//	//** synchronization and multithreading throughout
//
//	private LinkedHashSet<Dimension> explicitDimensions;
//
//	/**
//	 * When using lookupDimensions, all possible values for a given dimension are stored in a table. For continuous double
//	 * data we just end up with an entry per point, with two extra levels of indirection (one for the array index, followed
//	 * by List.get()) the win is largely for strings (e.g. filenames, etc.) for which there are just a few possible values
//	 */
//	private LinkedHashSet<Dimension> lookupDimensions;
//	List<List<Object>> lookupDimensionValues = new ArrayList<List<Object>>();
//
//	// private int numExplicitDimensions = 0;  // PERF useful to cache this?
//
//	private List<String> getExplicitDimensionNames()
//		{
//		List<String> result = new ArrayList<String>();
//		for (Dimension explicitDimension : explicitDimensions)
//			{
//			result.add(explicitDimension.name);
//			}
//		return result;
//		}
//
//	private List<String> getLookupDimensionNames()
//		{
//		List<String> result = new ArrayList<String>();
//		for (Dimension lookupDimension : lookupDimensions)
//			{
//			result.add(lookupDimension.name);
//			}
//		return result;
//		}
//
//	/**
//	 * The dimensions are in a known order; the "dimension number" is the index of the list, considering explicit
//	 * dimensions first and lookup dimensions second.
//	 */
//	public int getDimensionNumber(String name)
//		{
//		int result = getExplicitDimensionNames().indexOf(name);
//		if (result == -1)
//			{
//			result = getLookupDimensionNames().indexOf(name);
//			if (result != -1)
//				{
//				result += explicitDimensions.size();
//				}
//			}
//		return result;
//		}
//
//	public int[] getDimensionNumbers(List<String> dimensionNames)
//		{
//		int[] dimensionNumbers = new int[dimensionNames.size()];
//		int i = 0;
//		for (String dimensionName : dimensionNames)
//			{
//			dimensionNumbers[i] = getDimensionNumber(dimensionName);
//			i++;
//			}
//		return dimensionNumbers;
//		}
//
//	public void addFunctionDimension(String name, String function)
//		{
//		throw new NotImplementedException();
//		}
//
//	public void addDimension(String name)
//		{
//		// assume any new dimension will have few values, until it gets bigger than the threshold
//		lookupDimensions.add(new Dimension(this, name));
//		lookupDimensionValues.add(new ArrayList<Object>());
//		}
//
//	private Set<Point> points = new HashSet<Point>();
//
//	// Build indexes, i.e. maps going the other way to quickly perform selects.
//
//	Set<Dimension> indexedDimensions = new HashSet<Dimension>();
//	Map<String, SortedMap<Object, Point>> pointsByDimensionAndValue = new HashMap<String, SortedMap<Object, Point>>();
//
//	public void addIndexes(Set<Dimension> dims)
//		{
//		indexedDimensions.addAll(dims);
//		for (Point point : points)
//			{
//			for (Dimension dim : dims)
//				{
//				pointsByDimensionAndValue.get(dim.name).put(point.get(dim), point);
//				}
//			}
//		}
//	private int optimizeCounter = 0;
//	private static final int OPTIMIZE_FREQUENCY = 1000;
//
//	public void addPoint(Object[] values)  // matching current dimensionNumbers
//		{
//		Point point = new Point(this, values);
//		if(optimizeCounter >= OPTIMIZE_FREQUENCY)
//			{
//			optimize();
//			optimizeCounter = 0;
//			}
//
//
//		// index it
//		for (Dimension dim : indexedDimensions)
//			{
//			pointsByDimensionAndValue.get(dim.name).put(point.get(dim.number), point);
//			}
//		}
//
//	public void addPoint(Map<String, Object> values)
//		{
//		throw new NotImplementedException("Inefficient, use Object[] version");
//		}
//
//	public void removePoint(Point p)
//		{
//		throw new NotImplementedException();
//		}
//
//	/**
//	 * Return the values for requested dimensions from all the points
//	 *
//	 * @param dimensions
//	 * @return
//	 */
//	Object[][] get(List<String> dimensionNames)
//		{
//		return get(getDimensionNumbers(dimensionNames));
//		}
//
//	 Object[][] get(int[] dimensionNumbers)
//		 {
//		 return get(dimensionNumbers, points);
//		 }
//
//	private Object[][] get(int[] dimensionNumbers, Collection<Point> pointset)
//		{
//		// return an array instead of a set, to avoid computing hashcodes
//		Object[][] result = new Object[points.size()][];
//
//		int i = 0;
//		for (Point point : pointset)
//			{
//			int j = 0;
//			for(int dim : dimensionNumbers)
//				{
//				result[i][j]= point.get(dim);
//				j++;
//				}
//			i++;
//			}
//		return result;
//		}
//
//	/**
//	 * Return the values for requested dimensions from all the points, subject to the filter
//	 *
//	 * @param dimensions
//	 * @return
//	 */
//	Object[][] get(List<String> dimensionNames, Map<String, Range> constraints)
//		{
//		return get(getDimensionNumbers(dimensionNames), constraints);
//		}
//
//	private Object[][] get(int[] dimensionNumbers, Map<String, Range> constraints)
//		{
//		return get(dimensionNumbers, constrainPoints(constraints));
//		}
//
//	private Collection<Point> constrainPoints(Map<String, Range> constraints)
//		{
//		Set<Point> result = new HashSet<Point>();
//
//		asdf
//		// first intersect using indexes
//
//		for (Point point : points)
//			{
//			if(point.matches(constraints))
//				{
//				result.add(point);
//				}
//			}
//		return result;
//		}
//
///*	private void convertToLookup(String dimensionName)
//		{
//		throw new NotImplementedException();
//		}
//*/
//
//	private void convertToExplicit(Dimension dimension)
//		{
//		for (Point point : points)
//			{
//
//			}
//		}
//
//	private static final int MAX_LOOKUP_SIZE = 10000;
//
//	public void optimize()
//		{
//		for (Dimension lookupDimension : lookupDimensions)
//			{
//			int dimensionNumber = lookupDimension.getNumber(); //getDimensionNumber(lookupDimension.name);
//			final int uniqueValues = lookupDimensionValues.get(dimensionNumber).size();
//			if(uniqueValues > MAX_LOOKUP_SIZE)
//				{
//				convertToExplicit(lookupDimension);
//				}
//			}
//		}
	}
