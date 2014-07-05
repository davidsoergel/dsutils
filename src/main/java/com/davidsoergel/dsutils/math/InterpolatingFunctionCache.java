/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.math;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.TreeMap;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class InterpolatingFunctionCache
	{
	/*	private class Point
		  {
		  float value;
		  boolean smooth;
		  }

	  private TreeMap<Float, Point> values = new TreeMap<Float, Point>();
	  */


	Method method;
	float maxError;

	public InterpolatingFunctionCache(@NotNull Method method, float maxError, float minX, float maxX)
		{
		this.method = method;
		this.maxError = maxError;
		@NotNull InterpolationInterval i = new InterpolationInterval(minX, maxX);
		try
			{
			i.cielY = ((Double) (method.invoke(null, maxX))).floatValue();
			}
		catch (Exception e)
			{
			throw new Error(e);
			}

		intervals.put(i, i);
		}

	@NotNull
	private TreeMap<InterpolationInterval, InterpolationInterval> intervals =
			new TreeMap<InterpolationInterval, InterpolationInterval>();

	int evalCount;
	int notHits;

	@NotNull
	public String perfString()
		{
		return "" + intervals.size() + " intervals, " + evalCount + " evaluations, " + (evalCount - notHits) + " hits ("
		       + ((float) (evalCount - notHits) / (float) evalCount) + "%)";
		}

	// pull a trick with compareTo, equals, and hashCode so that using TreeMap.get(float x) finds the interval that x is contained in.
	// Hmm, the TreeMap javadoc says "a map performs all key comparisons using its compareTo (or compare) method"
	// so as long as a Float compares as equal to the interval that contains it, this should work, without worrying about equals() and hashCode().
	// oho, but it doesn't work.  Okay, trying a funky equals()

	private class InterpolationInterval implements Comparable<InterpolationInterval>
		{
		// the floor of the interval
		private final float floorX; //floatValue
		private float cielX; // == floorX + extent
		private float floorY;
		private float cielY;
		//	private float extent;
		private float slope;

		//InterpolationInterval next;

		private boolean smooth;

		private InterpolationInterval(float floor)
			{
			// avoid ridiculous problems
			//this.floorX = floor == 0.0f ? -0.0f : floor;
			this.floorX = floor == -0.0f ? 0.0f : floor;
			this.cielX = floorX;
			}

		private InterpolationInterval(float floor, float cielX)
			{
			this(floor);

			this.cielX = cielX;
			try
				{
				this.floorY = ((Double) (method.invoke(null, floorX))).floatValue();
				}
			catch (Exception e)
				{
				throw new Error(e);
				}
			}

		@NotNull
		@Override
		public String toString()
			{
			return "InterpolationInterval{" + floorX + "->" + cielX + " = " + floorY + " + " + slope + " * x "
			       + (smooth ? "SMOOTH" : "") + '}';
			}

		public int compareTo(@NotNull InterpolationInterval o)
			{
			// if there is any overlap at all, assert equality
			// I had assumed that TreeMap would use interval.compareTo(point), but in fact it uses point.compareTo(interval)


			//if (equals(o))
			//if (!(floorX >= o.cielX) && !(o.floorX >= cielX))

			if (floorX < o.cielX && o.floorX < cielX)
				//if ((floorX <= o.floorX && o.floorX < floorX + extent) || (floorX <= o.floorX && o.floorX < floorX + extent))
				{
				return 0;
				}
			return Float.compare(floorX, o.floorX);
			}

		@Override
		public boolean equals(Object obj)
			{
			@NotNull InterpolationInterval o = (InterpolationInterval) obj;
			return !(floorX >= o.cielX) && !(o.floorX >= cielX);
			}

		@Override
		public int hashCode()
			{
			//return floorX;
			return Float.floatToIntBits(floorX);
			}


		public int intValue()
			{
			return (int) floorX;
			}

		public long longValue()
			{
			return (long) floorX;
			}

		public float floatValue()
			{
			return floorX;
			}

		public double doubleValue()
			{
			return floorX;
			}

/*
		@Override
		public int hashCode()
			{
			return floorX.hashCode();
			}

		public int intValue()
			{
			return floorX.intValue();
			}

		public long longValue()
			{
			return floorX.longValue();
			}

		public float floatValue()
			{
			return floorX.floatValue();
			}

		public double doubleValue()
			{
			return floorX.doubleValue();
			}*/

		public float interpolate(float x)
			{
			evalCount++;
			if (!smooth)
				{
				notHits++;
				// split this interval in two.  "this" retains its floor, and we add a new one with a floor halfway along the interval.

				//** could numeric precision issues leave holes between the intervals?  Yes, argh, just so (especially with -0.0, what a travesty)
				// OK, let's just make the extents overlap a bit.

				float halfExtent = (cielX - floorX) / 2f;
				float midX = floorX + halfExtent;


				// note we don't use InterpolationInterval(midX, cielX), since we need to delay setting cielX until after the map put.
				@NotNull InterpolationInterval right = new InterpolationInterval(midX, cielX);

				right.cielY = cielY;
				right.slope = slope; // just temporary for the smoothness test; it'll be reset anyway

				cielX = midX;
				cielY = right.floorY;

				updateSlopeAndSmooth();
				right.updateSlopeAndSmooth();

				intervals.put(right, right);

				// we have to do the put before overlapping the extents, because that screws up equality

				//	extent += Math.ulp(extent) * 10;
				//	right.cielX = cielX; //right.floorX + halfExtent;

				//	if(x == -0.0f) { x = 0f;}

				if (x <= right.floorX)  // equals is OK too; this helps with -0.0 and the 10-ulp overlap stuff
					{
					return interpolate(x);
					}
				else
					{
					return right.interpolate(x);
					}
				}
			return predict(x);
			}

		public void updateSlopeAndSmooth()
			{
			// smooth == false;
			//** this bit is only sensible if there are no inflection points (d^2/dx >= 0).

			// error based on the _old_ slope
			float predictedCielY = predict(cielX);
			float absoluteError = predictedCielY - cielY;
			//float fractionError = absoluteError / (cielY - floorY);
			if (Math.abs(absoluteError) < maxError)
				{
				smooth = true;
				}

			slope = (cielY - floorY) / (cielX - floorX);
			}

		private float predict(float x)
			{
			return floorY + (x - floorX) * slope;
			}
		}

	public float evaluate(float x)
		{
		InterpolationInterval i = intervals.get(new InterpolationInterval(x));
		if (i == null)
			{
			i = intervals.get(new InterpolationInterval(x));
			throw new Error("Argument outside allowable range");
			}
		return i.interpolate(x);
		}
	}
