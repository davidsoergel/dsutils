package com.davidsoergel.dsutils.collections;

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
	public void testIntersectionWithComparator() throws Exception
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
		Set<String> result = DSCollectionUtils.intersection(set1, set2, reverseSort);

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
		Set<String> result = DSCollectionUtils.union(set1, set2, reverseSort);

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
		Set<String> result = DSCollectionUtils.subtract(set1, set2, reverseSort);

		assert result.equals(DSCollectionUtils.setOf("abboo", "bbrruu"));
		}
	}
