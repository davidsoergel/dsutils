package com.davidsoergel.dsutils.tuples;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
@Deprecated
final class Point
	{
	// Conceptually we want a Map<String, Object> here, but that's way too expensive.

	// we'll use a combination of explicit storage and lookup tables for values with high redundancy

//	private Object[] explicitValues;  // order matches explicitDimensionNames
//
//	private short[] lookupValues;  // order matches lookupDimensionNames
//	private TupleStoreImpl dimensionalStore;
//
//	public Point(final TupleStoreImpl dimensionalStore)
//		{
//		this.dimensionalStore = dimensionalStore;
//		}
//
//	/**
//	 * Get the value of this point on the given dimension number; using ints here is just for performance
//	 *
//	 * @param dimensionNumber
//	 * @return
//	 */
//	public Object get(final int dimensionNumber)
//		{
//		if (dimensionNumber < dimensionalStore.explicitDimensions.size())
//			{
//			return explicitValues[dimensionNumber];
//			}
//		else
//			{
//			final int lookupDimensionNumber = dimensionNumber - dimensionalStore.explicitDimensions.size();
//
//			return dimensionalStore.lookupDimensionValues.get(lookupDimensionNumber)
//					.get(lookupValues[lookupDimensionNumber]);  // whee dereferencing indirections
//			}
//		}
	}
