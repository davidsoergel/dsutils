/*
 * Copyright (c) 2007 Regents of the University of California
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
 *     * Neither the name of the University of California, Berkeley nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
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

package com.davidsoergel.dsutils.range;

import org.apache.log4j.Logger;

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

	public int compareTo(Interval<Double> o)
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
