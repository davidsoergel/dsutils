package com.davidsoergel.dsutils.collections;

import org.apache.commons.lang.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class UnorderedPairIterator<A extends Comparable<A>> implements Iterator<UnorderedPair<A>>
	{

	@NotNull
	private final Iterator<A> iterA;
	//final Iterator<B> iterB;
	@NotNull
	private final Iterable<A> iterableB;

	private Iterator<A> iterB;

	// State: aTrav is the _current_ item; bTrav is the _previous_ item and must be advanced anew in next() before returning
	@Nullable
	private A aTrav = null;
	@Nullable
	private A bTrav = null;

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

	public UnorderedPairIterator(@NotNull final Iterator<A> iterA, @NotNull final Iterable<A> iterableB)
		{
		this.iterA = iterA;
		this.iterableB = iterableB;
		}

	public UnorderedPairIterator(@NotNull final Iterable<A> iterableA, @NotNull final Iterable<A> iterableB)
		{
		this(iterableA.iterator(), iterableB);
		}

	public synchronized boolean hasNext()
		{
		final boolean canAdvanceB = aTrav != null && iterB.hasNext();
		return canAdvanceB || iterA.hasNext(); //advanceA();
		}

	private synchronized boolean advanceA()
		{
		aTrav = iterA.next();
		iterB = iterableB.iterator();

		final boolean canAdvanceB = aTrav != null && iterB.hasNext();
		return canAdvanceB;
		}

	private synchronized boolean advanceB()
		{
		if (iterB == null || !iterB.hasNext())
			{
			if (!advanceA())
				{
				return false;
				}
			}
		bTrav = iterB.next();
		assert bTrav != null;
		return true;
		}

	@NotNull
	public synchronized UnorderedPair<A> next()
		{
		while (advanceB())
			{
			assert aTrav != null && bTrav != null;

			if (aTrav.compareTo(bTrav) < 0)// avoid redundant pairs
				{
				return new UnorderedPair<A>(aTrav, bTrav);
				}
			// else we advance B and try again
			}
		throw new NoSuchElementException();
		}


	public void remove()
		{
		throw new NotImplementedException();
		}
	}
