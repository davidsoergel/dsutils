package com.davidsoergel.dsutils.range;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class BasicSteppedRange<T extends Number & Comparable>
		implements DiscreteRange<T> //, Comparable<Range<T>>
	{

	// ------------------------------ FIELDS ------------------------------

	final T min;
	final T max;
	final T step;


	// --------------------------- CONSTRUCTORS ---------------------------

	public BasicSteppedRange(final T max, final T min, final T step)
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


	public BasicSetRange<T> expandToInclude(BasicSteppedRange<T> v)
		{
		return asSetRange().expandToInclude(v);
		}

	protected abstract BasicSetRange<T> asSetRange();
	}

