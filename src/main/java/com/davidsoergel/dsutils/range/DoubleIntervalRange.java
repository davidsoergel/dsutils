/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.range;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * @version $Id: DoubleIntervalRange.java 690 2009-07-31 21:17:50Z soergel $
 */
public class DoubleIntervalRange implements Interval<Double>, SerializableRange<Double>
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(DoubleIntervalRange.class);

	final Double min; // = Double.NEGATIVE_INFINITY;
	final Double max; // = Double.POSITIVE_INFINITY;

	//private ContinuousDistribution1D distribution;

	final boolean closedLeft, closedRight;


	public DoubleIntervalRange(final Double min, final Double max, final boolean closedLeft, final boolean closedRight)
		{
		this.closedLeft = closedLeft;
		this.closedRight = closedRight;
		this.max = max;
		this.min = min;
		}

	public boolean isClosedLeft()
		{
		return closedLeft;
		}

	public boolean isClosedRight()
		{
		return closedRight;
		}

	// --------------------------- CONSTRUCTORS ---------------------------

	/*public DoubleIntervalRange()
		{
		super();
		}

	protected DoubleIntervalRange(DoubleIntervalRange doubleIntervalRange)
		{
		super(doubleIntervalRange);
		min = doubleIntervalRange.getMin();
		max = doubleIntervalRange.getMax();
		closedLeft = doubleIntervalRange.isClosedLeft();
		closedRight = doubleIntervalRange.isClosedRight();
		}
*/
	// --------------------- GETTER / SETTER METHODS ---------------------

/*	public ContinuousDistribution1D getDistribution()
		{
		if (distribution == null && min != null && max != null)
			{
			distribution = new UniformDistribution(min, max);
			}
		return distribution;
		}*/

	public Double getMax()
		{
		return max;
		}

	public Double getMin()
		{
		return min;
		}

	// ------------------------ CANONICAL METHODS ------------------------

	/*	public DoubleIntervalRange clone()
		 {
		 return new DoubleIntervalRange(this);
		 }
 */

	public String toString()
		{
		return String.format((closedLeft ? "[" : "(") + "%g,%g" + (closedRight ? "]" : ")"), min, max);
		}

	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface Comparable ---------------------

	public int compareTo(@NotNull Interval<Double> o)
		{
		int result = min.compareTo(o.getMin());
		if (result == 0)
			{
			if (closedLeft && !o.isClosedLeft())
				{
				result = -1;
				}
			else if (!closedLeft && o.isClosedLeft())
				{
				result = 1;
				}
			}
		return result;
		}

	@Override
	public boolean equals(final Object o)
		{
		if (this == o)
			{
			return true;
			}
		if (o == null || getClass() != o.getClass())
			{
			return false;
			}

		final DoubleIntervalRange that = (DoubleIntervalRange) o;

		if (closedLeft != that.closedLeft)
			{
			return false;
			}
		if (closedRight != that.closedRight)
			{
			return false;
			}
		if (max != null ? !max.equals(that.max) : that.max != null)
			{
			return false;
			}
		if (min != null ? !min.equals(that.min) : that.min != null)
			{
			return false;
			}

		return true;
		}

	@Override
	public int hashCode()
		{
		int result = min != null ? min.hashCode() : 0;
		result = 31 * result + (max != null ? max.hashCode() : 0);
		result = 31 * result + (closedLeft ? 1 : 0);
		result = 31 * result + (closedRight ? 1 : 0);
		return result;
		}

	// --------------------- Interface Interval ---------------------

	public boolean encompassesValue(Double value)
		{
		boolean leftOK = min < value || closedLeft && min.equals(value);
		boolean rightOK = max > value || closedRight && max.equals(value);
		return leftOK && rightOK;
		}

	// --------------------- Interface ParameterSetValueOrRange ---------------------

	/*	public Set<String> getStringValues() //throws JandyException
		 {
		 return null;
		 //throw new JandyException("Can't list real numbers");
		 }
 */
	//public SortedSet<Double> getDoubleValues()
/*
	public SortedSet<Double> getValue()

		{
		SortedSet<Double> result = new TreeSet<Double>();

		result.add(getDistribution().sample());

		return result;
		}
*/
	// -------------------------- OTHER METHODS --------------------------

	/*
	   public Set<Double> getValue()
		   {
		   return getDoubleValues();
		   }
		   */

	/*
	 public ParameterSetRangeForKey expandToInclude(ParameterSetValueOrRange v)
		 {
		 if (v == null)
			 {
			 includesNull = true;
			 return this;
			 }

		 if (getParameterKey() == null)
			 {
			 setParameterKey(v.getParameterKey());
			 }
		 else
			 {
			 assert getParameterKey().equals(v.getParameterKey());
			 }

		 if (v instanceof DoubleParameterValue)
			 {
			 Double i = (Double) v.getValue();
			 min = Math.min(min, i);
			 max = Math.max(min, i);
			 return this;
			 }
		 else if (v instanceof DoubleListParameterValue)
			 {
			 for (Double i : (List<Double>) v.getValue())
				 {
				 min = Math.min(min, i);
				 max = Math.max(min, i);
				 }
			 return this;
			 }
		 else if (v instanceof DoubleIntervalRange)
				 {
				 min = Math.min(min, ((DoubleIntervalRange) v).getMin());
				 max = Math.max(min, ((DoubleIntervalRange) v).getMax());
				 return this;
				 }
			 else if (v instanceof DoubleSteppedRange)
					 {
					 min = Math.min(min, ((DoubleSteppedRange) v).getMin());
					 max = Math.max(min, ((DoubleSteppedRange) v).getMax());
					 return this;
					 }
				 else if (v instanceof DoubleSetRange)
						 {
						 for (Double i : (Set<Double>) v.getValue())
							 {
							 min = Math.min(min, i);
							 max = Math.max(min, i);
							 }
						 return this;
						 }

					 else
						 {
						 throw new JandyDbRuntimeException(
								 "Can't expand a DoubleIntervalRange with " + v.getClass());
						 }
		 }

	 public void setMax(Double max)
		 {
		 this.max = max;
		 if (max == null)
			 {
			 this.max = Double.POSITIVE_INFINITY;
			 }
		 distribution = null;
		 }

	 public void setMin(Double min)
		 {
		 this.min = min;
		 if (min == null)
			 {
			 this.min = Double.NEGATIVE_INFINITY;
			 }
		 distribution = null;
		 }
 */

	public int size()// throws JandyException
	{
	return 1;
	//throw new JandyException("Can't count real numbers");
	}

	/*public Double sample()
		{
		throw new NotImplementedException();
		}*/
	}
