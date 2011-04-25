package com.davidsoergel.dsutils.collections;

import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class UnorderedPairIteratorTest
	{
	@Test
	public void iteratorBehavesAsExpected()
		{
		@NotNull Set<String> s1 = DSCollectionUtils.setOf("a", "b", "c");
		@NotNull Set<String> s2 = DSCollectionUtils.setOf("d", "e", "f", "g");


		// note some orders reversed
		@NotNull Set<UnorderedPair<String>> expected = DSCollectionUtils
				.setOf(new UnorderedPair<String>("a", "d"), new UnorderedPair<String>("a", "e"),
				       new UnorderedPair<String>("a", "f"), new UnorderedPair<String>("a", "g"),
				       new UnorderedPair<String>("b", "d"), new UnorderedPair<String>("b", "e"),
				       new UnorderedPair<String>("b", "f"), new UnorderedPair<String>("g", "b"),
				       new UnorderedPair<String>("d", "c"), new UnorderedPair<String>("c", "e"),
				       new UnorderedPair<String>("c", "f"), new UnorderedPair<String>("c", "g"));

		@NotNull UnorderedPairIterator<String> iter = new UnorderedPairIterator<String>(s1, s2);

		@NotNull Set<UnorderedPair<String>> expectedCountdown = new HashSet<UnorderedPair<String>>(expected);
		@NotNull Set<UnorderedPair<String>> observed = new HashSet<UnorderedPair<String>>();
		while (iter.hasNext())
			{
			@NotNull final UnorderedPair<String> pair = iter.next();
			assert expectedCountdown.remove(pair);
			observed.add(pair);
			}

		assert expectedCountdown.size() == 0;
		assert DSCollectionUtils.isEqualCollection(expected, observed);
		}
	}
