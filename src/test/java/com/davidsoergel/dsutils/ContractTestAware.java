/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils;


import org.jetbrains.annotations.NotNull;

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
		@NotNull Set result = new HashSet();
		@NotNull Queue<ContractTest> queue = new LinkedList<ContractTest>();

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
