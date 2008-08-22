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

public class SetHierarchyNodeTest extends ContractTestAware<SetHierarchyNode>
		implements TestInstanceFactory<SetHierarchyNode>
	{
	public SetHierarchyNode createInstance() throws Exception
		{
		return null;
		}

	public void addContractTestsToQueue(Queue<Object> theContractTests)
		{
		theContractTests.add(new HierarchyNodeInterfaceTest(this)
		{
		});
		}

	@Factory
	public Object[] testInterfacesNoReally()
		{
		return testInterfaces();
		}

	@Test
	public void bogusTest()
		{
		assert false;
		}
	}
