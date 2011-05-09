package com.davidsoergel.dsutils.collections;

import com.davidsoergel.dsutils.EquivalenceDefinition;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.Set;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class DSCollectionUtilsTest
	{
	@Test
	public void testIntersectionExhaustiveWithComparator() throws Exception
		{
		Set<String> set1 = DSCollectionUtils.setOf("abboo", "utnth", "bbrruu", "ccunu");
		Set<String> set2 = DSCollectionUtils.setOf("utnth", "ccunu", "rcdrc", "hnth");
		EquivalenceDefinition<String> reverseSort = new EquivalenceDefinition<String>()
		{
		public boolean areEquivalent(final String o1, final String o2)
			{
			return o2.compareTo(o1) == 0;
			}
		};
		Set<String> result = DSCollectionUtils.intersectionExhaustive(set1, set2, reverseSort);

		assert result.equals(DSCollectionUtils.setOf("utnth", "ccunu"));
		}

	@Test
	public void testIntersectionFastWithComparator() throws Exception
		{
		Set<String> set1 = DSCollectionUtils.setOf("abboo", "utnth", "bbrruu", "ccunu");
		Set<String> set2 = DSCollectionUtils.setOf("utnth", "ccunu", "rcdrc", "hnth");
		Comparator<String> reverseSort = new Comparator<String>()
		{
		public int compare(final String o1, final String o2)
			{
			return o2.compareTo(o1);
			}
		};
		Set<String> result = DSCollectionUtils.intersectionFast(set1, set2, reverseSort);

		assert result.equals(DSCollectionUtils.setOf("utnth", "ccunu"));
		}


	@Test
	public void testUnionWithComparator() throws Exception
		{
		Set<String> set1 = DSCollectionUtils.setOf("abboo", "utnth", "bbrruu", "ccunu");
		Set<String> set2 = DSCollectionUtils.setOf("utnth", "ccunu", "rcdrc", "hnth");
		Comparator<String> reverseSort = new Comparator<String>()
		{
		public int compare(final String o1, final String o2)
			{
			return o2.compareTo(o1);
			}
		};
		Set<String> result = DSCollectionUtils.unionFast(set1, set2, reverseSort);

		assert result.equals(DSCollectionUtils.setOf("abboo", "utnth", "bbrruu", "ccunu", "rcdrc", "hnth"));
		}


	@Test
	public void testSubtractWithComparator() throws Exception
		{
		Set<String> set1 = DSCollectionUtils.setOf("abboo", "utnth", "bbrruu", "ccunu");
		Set<String> set2 = DSCollectionUtils.setOf("utnth", "ccunu", "rcdrc", "hnth");
		Comparator<String> reverseSort = new Comparator<String>()
		{
		public int compare(final String o1, final String o2)
			{
			return o2.compareTo(o1);
			}
		};
		Set<String> result = DSCollectionUtils.subtractFast(set1, set2, reverseSort);

		assert result.equals(DSCollectionUtils.setOf("abboo", "bbrruu"));
		}
	}
