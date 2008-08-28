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
package org.jboss.test.aop.unit.assignability;

import junit.framework.TestCase;
import org.jboss.aop.advice.annotation.assignability.AssignabilityAlgorithm;
import org.jboss.aop.advice.annotation.assignability.VariableHierarchy;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class FromVariableAlgorithmTest extends TestCase
	{
	// ------------------------------ FIELDS ------------------------------

	private static Class[] NO_ARGS = new Class[0];
	AssignabilityAlgorithm algorithm;
	VariableHierarchy hierarchy;


	// -------------------------- OTHER METHODS --------------------------

	<A extends String> Collection<A> called4()
		{
		return null;
		}

	// Scenario 1

	Collection<String> caller1()
		{
		return called1();
		}

	<A> Collection<A> called1()
		{
		return null;
		}

	// Scenario 10

	Collection<Runnable> caller10(Collection<Runnable> arg)
		{
		return called8(arg);
		}

	// Scenario 11

	Collection<? extends List> caller11(Collection<Runnable> arg)
		{
		//return called8(arg);
		return null;
		}

	// Scenario 2

	Collection<String> caller2()
		{
		return called2();
		}

	<A extends String> Collection<A> called2()
		{
		return null;
		}

	// Scenario 3

	Collection<Runnable> caller3()
		{
		//return called2();
		return null;
		}

	// Scenario 4

	Collection<?> caller4()
		{
		// (ignore) TODO fix algorithm here
		//return called4();
		return null;
		}

	// Scenario 5

	Collection<? extends Runnable> caller5()
		{
		//return called4();
		return null;
		}

	// Scenario 6

	Collection<?> caller6()
		{
		return called6();
		}

	<A> Collection<A> called6()
		{
		return null;
		}

	// Scenario 7

	Collection<? extends Runnable> caller7()
		{
		return called6();
		}

	// Scenario 8

	Collection<?> caller8(Collection<Runnable> arg)
		{
		return called8(arg);
		}

	<A> Collection<A> called8(Collection<A> arg)
		{
		return null;
		}

	// Scenario 9

	Collection<? extends Runnable> caller9(Collection<Runnable> arg)
		{
		return called8(arg);
		}

	public void setUp()
		{
		this.algorithm = AssignabilityAlgorithm.FROM_VARIABLE;
		hierarchy = new VariableHierarchy();
		}

	public void test1() throws Exception
		{
		assertTrue(runAlgorithm(1, 1));
		}

	private boolean runAlgorithm(int callerNumber, int calledNumber) throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller" + callerNumber, NO_ARGS);
		Method called = this.getClass().getDeclaredMethod("called" + calledNumber, NO_ARGS);
		return algorithm.isAssignable(caller.getGenericReturnType(), called.getGenericReturnType(), hierarchy);
		}

	public void test10() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller10", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called8", new Class[]{Collection.class});
		assertTrue(AssignabilityAlgorithm.VARIABLE_TARGET.isAssignable(called.getGenericParameterTypes()[0],
		                                                               caller.getGenericParameterTypes()[0],
		                                                               hierarchy));
		assertTrue(algorithm.isAssignable(caller.getGenericReturnType(), called.getGenericReturnType(), hierarchy));
		}

	public void test11() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller11", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called8", new Class[]{Collection.class});
		assertTrue(AssignabilityAlgorithm.VARIABLE_TARGET.isAssignable(called.getGenericParameterTypes()[0],
		                                                               caller.getGenericParameterTypes()[0],
		                                                               hierarchy));
		assertFalse(algorithm.isAssignable(caller.getGenericReturnType(), called.getGenericReturnType(), hierarchy));
		}

	public void test2() throws Exception
		{
		assertTrue(runAlgorithm(2, 2));
		}

	public void test3() throws Exception
		{
		assertFalse(runAlgorithm(3, 2));
		}

	public void test4() throws Exception
		{
		assertTrue(runAlgorithm(4, 4));
		}

	public void test5() throws Exception
		{
		assertFalse(runAlgorithm(5, 4));
		}

	public void test6() throws Exception
		{
		assertTrue(runAlgorithm(6, 6));
		}

	public void test7() throws Exception
		{
		assertTrue(runAlgorithm(7, 6));
		}

	public void test8() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller8", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called8", new Class[]{Collection.class});
		assertTrue(AssignabilityAlgorithm.VARIABLE_TARGET.isAssignable(called.getGenericParameterTypes()[0],
		                                                               caller.getGenericParameterTypes()[0],
		                                                               hierarchy));
		assertTrue(algorithm.isAssignable(caller.getGenericReturnType(), called.getGenericReturnType(), hierarchy));
		}

	public void test9() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller9", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called8", new Class[]{Collection.class});
		assertTrue(AssignabilityAlgorithm.VARIABLE_TARGET.isAssignable(called.getGenericParameterTypes()[0],
		                                                               caller.getGenericParameterTypes()[0],
		                                                               hierarchy));
		assertTrue(algorithm.isAssignable(caller.getGenericReturnType(), called.getGenericReturnType(), hierarchy));
		}
	}