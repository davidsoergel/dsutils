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

package com.davidsoergel.dsutils;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class ContractTestAware<T>
	{

	/**
	 * Instantiate each of the Contract Tests directly applicable at this level of the test hierarchy (i.e., relating to
	 * interfaces directly implemented or an abstract class directly extended), and add them to the given Queue.
	 *
	 * @param theContractTests the Queue to which to add the instantiated tests
	 */
	public abstract void addContractTestsToQueue(Queue<ContractTest> theContractTests);

	/**
	 * Instantiate an instance of each of the applicable Contract Tests, recursively walking up the tree if some of the
	 * Contract Tests are themselves ContractTestAware (e.g., in the case of a hierarchy of interfaces and/or abstract
	 * classes)
	 *
	 * @param testName The name to apply to all of the generated test objects (See {@See ITest#getTestName()})
	 * @return an Object[] of the test instances
	 */
	public Object[] instantiateAllContractTestsWithName(String testName)
		{
		Set result = new HashSet();
		Queue<ContractTest> queue = new LinkedList<ContractTest>();

		addContractTestsToQueue(queue);

		// recursively find all applicable contract tests up the tree
		while (!queue.isEmpty())
			{
			ContractTest contractTest = queue.remove();
			result.add(contractTest);
			contractTest.setTestName(testName);
			if (contractTest instanceof ContractTestAwareContractTest)
				{
				((ContractTestAwareContractTest) contractTest).addContractTestsToQueue(queue);
				}
			}

		return result.toArray();
		}
	}
