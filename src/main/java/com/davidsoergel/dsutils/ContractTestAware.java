package com.davidsoergel.dsutils;

import org.testng.annotations.Factory;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
 */
public abstract class ContractTestAware<T>
	{
	protected TestInstanceFactory<? extends T> tif;

	public abstract void addContractTestsToQueue(Queue<Object> theContractTests);

	@Factory
	public Object[] testInterfaces()
		{
		Set<Object> result = new HashSet<Object>();
		Queue<Object> queue = new LinkedList<Object>();

		addContractTestsToQueue(queue);

		// recursively find all applicable contract tests up the tree
		while (!queue.isEmpty())
			{
			Object contractTest = queue.remove();
			result.add(contractTest);
			if (contractTest instanceof ContractTestAware)
				{
				((ContractTestAware) contractTest).addContractTestsToQueue(queue);
				}
			}

		return result.toArray();
		}
	}
