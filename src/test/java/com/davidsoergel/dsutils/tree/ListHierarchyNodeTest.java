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

import java.util.Queue;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
 */
public class ListHierarchyNodeTest extends ContractTestAware<ListHierarchyNode>
		implements TestInstanceFactory<ListHierarchyNode>
	{
	public ListHierarchyNode createInstance() throws Exception
		{
		ListHierarchyNode root = new ListHierarchyNode();
		ListHierarchyNode a = root.newChild();
		ListHierarchyNode b = root.newChild();
		ListHierarchyNode c = root.newChild();
		ListHierarchyNode d = a.newChild();
		ListHierarchyNode e = a.newChild();
		ListHierarchyNode f = b.newChild();
		ListHierarchyNode g = b.newChild();
		ListHierarchyNode h = b.newChild();
		ListHierarchyNode i = c.newChild();
		ListHierarchyNode j = g.newChild();
		ListHierarchyNode k = g.newChild();
		ListHierarchyNode l = k.newChild();
		ListHierarchyNode m = l.newChild();
		return root;
		}

	public void addContractTestsToQueue(Queue<Object> theContractTests)
		{
		theContractTests.add(new HierarchyNodeInterfaceTest(this)
		{
		});
		}

	@Factory
	public Object[] instantiateAllContractTests()
		{
		return super.instantiateAllContractTests();
		}

	// comment this out to see if TestNG runs these tests under Maven
	@Test
	public void bogusTest()
		{
		}
	}
