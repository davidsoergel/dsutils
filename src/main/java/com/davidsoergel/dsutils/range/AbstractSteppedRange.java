package com.davidsoergel.dsutils.range;

import java.io.Serializable;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class AbstractSteppedRange<T extends Number & Comparable<T> & Serializable>
		implements DiscreteRange<T>, SerializableRange<T> //, Comparable<Range<T>>
	{

	// ------------------------------ FIELDS ------------------------------

	final T min;
	final T max;
	final T step;


	// --------------------------- CONSTRUCTORS ---------------------------

	public AbstractSteppedRange(final T min, final T max, final T step)
		{
		this.max = max;
		this.min = min;
		this.step = step;
		}

	// --------------------- GETTER / SETTER METHODS ---------------------

	public T getMax()
		{
		return max;
		}

	public T getMin()
		{
		return min;
		}


	public T getStep()
		{
		return step;
		}


	public String toString()
		{
		return String.format("[%g,%g] (%g)", min, max, step);
		}

	// -------------------------- OTHER METHODS --------------------------
/*
		public int compareTo(Range<T> o)
			{
			return min.compareTo(o.getMin());
			}
*/


	public AbstractSetRange<T> expandToInclude(AbstractSteppedRange<T> v)
		{
		return asSetRange().expandToInclude(v);
		}

	protected abstract AbstractSetRange<T> asSetRange();
	}
