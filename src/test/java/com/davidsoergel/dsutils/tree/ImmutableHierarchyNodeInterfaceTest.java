package com.davidsoergel.dsutils.tree;

import com.davidsoergel.dsutils.TestInstanceFactory;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
 */
public abstract class ImmutableHierarchyNodeInterfaceTest
	{

	private TestInstanceFactory<? extends ImmutableHierarchyNode<String, ?>> tif;


	// --------------------------- CONSTRUCTORS ---------------------------

	public ImmutableHierarchyNodeInterfaceTest(TestInstanceFactory<? extends ImmutableHierarchyNode<String, ?>> tif)
		{
		this.tif = tif;
		}

	// oops, there is no addChild()

	/*
	@Test
	public void addedChildIsIncludedInChildArray() throws Exception
		{
		HierarchyNode n = tif.createInstance();
		HierarchyNode c = tif.createInstance();
		n.addChild(c);
		assert n.getChildren().contains(c);
		}

	@Test
	public void addChildSetsChildParentLink()
		{
		assert false;
		}
		*/

	@Test
	public void newChildIsIncludedInChildArray() throws Exception
		{
		ImmutableHierarchyNode<String, ?> n = tif.createInstance();
		ImmutableHierarchyNode<String, ?> c = n.newChild("bogus");
		assert n.getChildren().contains(c);
		}

	@Test
	public void newChildSetsChildParentLink() throws Exception
		{
		ImmutableHierarchyNode<String, ?> n = tif.createInstance();
		ImmutableHierarchyNode<String, ?> c = n.newChild("bogus");
		assert c.getParent() == n;
		}

	@Test
	public void ancestorPathIncludesThis() throws Exception
		{
		HierarchyNode n = tif.createInstance();
		assert n.getAncestorPath().contains(n);
		}

	@Test
	public void ancestorPathExtendsFromRootToImmediateParent() throws Exception
		{
		HierarchyNode n = tif.createInstance();
		List path = n.getAncestorPath();
		Collections.reverse(path);
		HierarchyNode p = n;
		while (p != null)
			{
			HierarchyNode p2 = p.getParent();

			assert p == path.get(0);
			path.remove(p);
			p = p2;
			}
		assert path.isEmpty();
		}

	@Test
	public void providesDepthFirstIterator() throws Exception
		{
		HierarchyNode n = tif.createInstance();
		assert n.depthFirstIterator() != null;
		}
	}
