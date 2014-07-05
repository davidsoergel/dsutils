/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.test.aop.unit.assignability;

import junit.framework.TestCase;
import org.jboss.aop.advice.annotation.assignability.AssignabilityAlgorithm;
import org.jboss.aop.advice.annotation.assignability.VariableHierarchy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * @version $Id$
 */
public class FromVariableAlgorithmTest extends TestCase
	{
	// ------------------------------ FIELDS ------------------------------

	@NotNull
	private static Class[] NO_ARGS = new Class[0];
	AssignabilityAlgorithm algorithm;
	VariableHierarchy hierarchy;


	// -------------------------- OTHER METHODS --------------------------

	@Nullable
	<A extends String> Collection<A> called4()
		{
		return null;
		}

	// Scenario 1

	@Nullable
	Collection<String> caller1()
		{
		return called1();
		}

	@Nullable
	<A> Collection<A> called1()
		{
		return null;
		}

	// Scenario 10

	@Nullable
	Collection<Runnable> caller10(Collection<Runnable> arg)
		{
		return called8(arg);
		}

	// Scenario 11

	@Nullable
	Collection<? extends List> caller11(Collection<Runnable> arg)
		{
		//return called8(arg);
		return null;
		}

	// Scenario 2

	@Nullable
	Collection<String> caller2()
		{
		return called2();
		}

	@Nullable
	<A extends String> Collection<A> called2()
		{
		return null;
		}

	// Scenario 3

	@Nullable
	Collection<Runnable> caller3()
		{
		//return called2();
		return null;
		}

	// Scenario 4

	@Nullable
	Collection<?> caller4()
		{
		// (ignore) TODO fix algorithm here
		//return called4();
		return null;
		}

	// Scenario 5

	@Nullable
	Collection<? extends Runnable> caller5()
		{
		//return called4();
		return null;
		}

	// Scenario 6

	@Nullable
	Collection<?> caller6()
		{
		return called6();
		}

	@Nullable
	<A> Collection<A> called6()
		{
		return null;
		}

	// Scenario 7

	@Nullable
	Collection<? extends Runnable> caller7()
		{
		return called6();
		}

	// Scenario 8

	@Nullable
	Collection<?> caller8(Collection<Runnable> arg)
		{
		return called8(arg);
		}

	@Nullable
	<A> Collection<A> called8(Collection<A> arg)
		{
		return null;
		}

	// Scenario 9

	@Nullable
	Collection<? extends Runnable> caller9(Collection<Runnable> arg)
		{
		return called8(arg);
		}

	/**
	 * {@inheritDoc}
	 */
	@Override
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
		assertTrue(AssignabilityAlgorithm.VARIABLE_TARGET
				           .isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
				                         hierarchy));
		assertTrue(algorithm.isAssignable(caller.getGenericReturnType(), called.getGenericReturnType(), hierarchy));
		}

	public void test11() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller11", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called8", new Class[]{Collection.class});
		assertTrue(AssignabilityAlgorithm.VARIABLE_TARGET
				           .isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
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
		assertTrue(AssignabilityAlgorithm.VARIABLE_TARGET
				           .isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
				                         hierarchy));
		assertTrue(algorithm.isAssignable(caller.getGenericReturnType(), called.getGenericReturnType(), hierarchy));
		}

	public void test9() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller9", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called8", new Class[]{Collection.class});
		assertTrue(AssignabilityAlgorithm.VARIABLE_TARGET
				           .isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
				                         hierarchy));
		assertTrue(algorithm.isAssignable(caller.getGenericReturnType(), called.getGenericReturnType(), hierarchy));
		}
	}
