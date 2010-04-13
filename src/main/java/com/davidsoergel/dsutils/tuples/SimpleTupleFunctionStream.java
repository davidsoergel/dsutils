package com.davidsoergel.dsutils.tuples;

import com.google.common.base.Function;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SimpleTupleFunctionStream implements TupleFunctionStream
	{
	TupleStream underlyingStream;
	TupleFunction theTupleFunction;
	Function<Object[], Object[]> theFunction;

	public SimpleTupleFunctionStream(final TupleStream underlyingStream, final TupleFunction theTupleFunction)
			throws TupleException
		{
		// oops this ignores order
		//	if(! DSCollectionUtils.isEqualCollection(underlyingStream.dimensions(), theFunction.getFromDims()))
		if (!Arrays.deepEquals(underlyingStream.getDimensions().toArray(new String[0]),
		                       theTupleFunction.getFromDimensions().toArray(new String[0])))
			{
			throw new TupleException("Can't apply a function to a stream with different dimensions");
			}

		this.theTupleFunction = theTupleFunction;
		this.underlyingStream = underlyingStream;
		this.theFunction = theTupleFunction.getFunction();
		}

	public List<String> getDimensions()
		{
		return theTupleFunction.getToDimensions();
		}

	public Iterator<Object[]> iterator() throws TupleException
		{

		return new Iterator<Object[]>()
		{
		Iterator<Object[]> underlyingIterator = underlyingStream.iterator();

		public boolean hasNext()
			{
			return underlyingIterator.hasNext();
			}

		public Object[] next()
			{
			return theFunction.apply(underlyingIterator.next());
			}

		public void remove()
			{
			underlyingIterator.remove();
			}
		};
		}
	}
