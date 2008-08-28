/*
 * Copyright (c) 2001-2008 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * dev@davidsoergel.com
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
 *     * Neither the name of the author nor the names of any contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
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

package com.davidsoergel.dsutils.tree;

import com.davidsoergel.dsutils.ContractTestAware;
import com.davidsoergel.dsutils.TestInstanceFactory;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.Queue;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class SortedSetHierarchyNodeTest extends ContractTestAware<SortedSetHierarchyNode<String>>
		implements TestInstanceFactory<SortedSetHierarchyNode<String>>
	{
	public SortedSetHierarchyNode<String> createInstance() throws Exception
		{
		SortedSetHierarchyNode<String> root = new SortedSetHierarchyNode<String>("root");
		SortedSetHierarchyNode<String> a = root.newChild("a");
		SortedSetHierarchyNode<String> b = root.newChild("b");
		SortedSetHierarchyNode<String> c = root.newChild("c");
		SortedSetHierarchyNode<String> d = a.newChild("d");
		SortedSetHierarchyNode<String> e = a.newChild("e");
		SortedSetHierarchyNode<String> f = b.newChild("f");
		SortedSetHierarchyNode<String> g = b.newChild("g");
		SortedSetHierarchyNode<String> h = b.newChild("h");
		SortedSetHierarchyNode<String> i = c.newChild("i");
		SortedSetHierarchyNode<String> j = g.newChild("j");
		SortedSetHierarchyNode<String> k = g.newChild("k");
		SortedSetHierarchyNode<String> l = k.newChild("l");
		SortedSetHierarchyNode<String> m = l.newChild("m");
		return root;
		}

	public void addContractTestsToQueue(Queue theContractTests)
		{
		theContractTests.add(new ImmutableHierarchyNodeInterfaceTest(this)
		{
		});
		}

	@Factory
	public Object[] instantiateAllContractTests()
		{
		return super.instantiateAllContractTests();
		}


	@Test
	public void childrenAreInSortedOrderAfterNewChild() throws Exception
		{
		SortedSetHierarchyNode<String> n = createInstance();
		n.newChild("p");
		n.newChild("o");
		n.newChild("n");

		Iterator<HierarchyNode<String, SortedSetHierarchyNode<String>>> l = n.getChildren().iterator();
		assert l.next().getValue().equals("a");
		assert l.next().getValue().equals("b");
		assert l.next().getValue().equals("c");
		assert l.next().getValue().equals("n");
		assert l.next().getValue().equals("o");
		assert l.next().getValue().equals("p");
		}
	}
