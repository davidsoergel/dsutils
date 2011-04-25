package com.davidsoergel.dsutils.collections;

import org.apache.commons.lang.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class OrderedPairIterator<A, B> implements Iterator<OrderedPair<A, B>>
	{
	@NotNull
	private final Iterator<A> iterA;
	//final Iterator<B> iterB;
	@NotNull
	private final Iterable<B> iterableB;

	private Iterator<B> iterB;

	@Nullable
	private A aTrav = null;

/*
	public OrderedPairIterator(final Iterator<A> iterA, final Iterator<B> iterB)
		{
		this.iterA = iterA;
		this.iterB = iterB;
		}

	public OrderedPairIterator(final Iterable<A> iterableA, final Iterator<B> iterB)
		{
		this(iterableA.iterator(), iterB);
		}
*/

	public OrderedPairIterator(@NotNull final Iterator<A> iterA, @NotNull final Iterable<B> iterableB)
		{
		this.iterA = iterA;
		this.iterableB = iterableB;
		}

	public OrderedPairIterator(@NotNull final Iterable<A> iterableA, @NotNull final Iterable<B> iterableB)
		{
		this(iterableA.iterator(), iterableB);
		}

	public boolean hasNext()
		{
		final boolean canAdvanceB = aTrav != null && iterB.hasNext();
		return canAdvanceB || advanceA();
		}

	private boolean advanceA()
		{
		aTrav = iterA.next();
		iterB = iterableB.iterator();

		final boolean canAdvanceB = aTrav != null && iterB.hasNext();
		return canAdvanceB;
		}

	@Nullable
	public OrderedPair<A, B> next()
		{
		// this advances a as needed to guarantee canAdvanceB
		if (!hasNext())
			{
			return null;
			}

		final B bTrav = iterB.next();

		assert aTrav != null && bTrav != null;

		return new OrderedPair<A, B>(aTrav, bTrav);
		}

	public void remove()
		{
		throw new NotImplementedException();
		}
	}
