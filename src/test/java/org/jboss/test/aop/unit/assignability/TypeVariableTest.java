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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


/**
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class TypeVariableTest extends VariableTargetAlgorithmTest
	{
	// -------------------------- OTHER METHODS --------------------------

	<B extends Collection> void called26(B arg)
		{
		}

	<A, B, C extends Runnable & Collection<A>> void called28(A arg1, B arg2, C arg3)
		{
		}

	<A extends ArrayList<?>> void called42(A arg)
		{
		}

	<A extends Runnable> void called44(A arg)
		{
		}

	<A extends Runnable> void called46(A arg)
		{
		}

	<B, D extends B> void called6(D arg1, B arg2, D arg3)
		{
		}

	// Scenario 1

	<B> void caller1(ArrayList<B> arg)
		{
		called1(arg);
		}

	<A> void called1(A arg)
		{
		}

	// Scenario 10

	void caller10(List arg1, Runnable arg2, List arg3)
		{
		//called6(arg1, arg2, arg3);
		}

	// Scenario 11

	<A extends String & Runnable, B extends Runnable> void caller11(A arg, B arg2)
		{
		//called6(arg, arg2, arg);
		}

	// Scenario 12

	<A extends Runnable> void caller12(A arg1, Runnable arg2)
		{
		called12(arg1, arg2);
		}

	<B, D extends B> void called12(D arg, B arg2)
		{
		}

	// Scenario 13

	<A extends Integer & Runnable> void caller13(A arg, Runnable arg2)
		{
		called12(arg, arg2);
		}

	// Scenario 14

	<A extends Integer & Runnable, B extends String> void caller14(A arg, B arg2)
		{
		called14(arg, arg2);
		}

	public <X> void called14(X arg, X arg2)
		{
		}

	// Scenario 15

	void caller15(Collection<?> arg1, Runnable arg2)
		{
		called15(arg1, arg2);
		}

	<B> void called15(B arg1, B arg2)
		{
		}

	// Scenario 16

	void caller16(Integer arg1, Runnable arg2)
		{
		called15(arg1, arg2);
		}

	// Scenario 17

	void caller17(Collection<Integer> arg1, Collection<Runnable> arg2)
		{
		called15(arg1, arg2);
		}

	// Scenario 18

	public void caller18(Collection<Collection<Integer>> arg1, Collection<Collection<Runnable>> arg2)
		{
		called15(arg1, arg2);
		}

	// Scenario 19

	<A extends Collection> void caller19(A arg1, String arg2)
		{
		//called19(arg1, arg2);
		}

	// Scenario 2

	public void caller2(Collection arg)
		{
		called2(arg);
		}

	public <C extends Collection, ArrayList extends C> void called2(C arg)
		{
		}

	// Scenario 20

	<A extends Collection> void caller20(A arg1, Collection arg2)
		{
		called19(arg1, arg2);
		}

	<C, B extends C> void called19(B arg, C arg2)
		{
		}

	// Scenario 21

	<A extends List> void caller21(A arg1, Collection arg2)
		{
		called19(arg1, arg2);
		}

	// Scenario 22

	<A extends Collection> void caller22(A arg1, HashSet arg2)
		{
		//called19(arg1, arg2);
		}

	// Scenario 23

	<A> void caller23(A arg1, List arg2)
		{
		//called19(arg1, arg2);
		}

	// Scenario 24

	void caller24(Collection<Integer>[] arg)
		{
		called24(arg);
		}

	<B> void called24(B arg)
		{
		}

	// Scenario 25

	<A> void caller25(A[] arg)
		{
		called24(arg);
		}

	// Scenario 26

	void caller26(Collection<Integer>[] arg)
		{
		//called26(arg);
		}

	// Scenario 27

	<A> void caller27(A[] arg)
		{
		//called26(arg);
		}

	// Scenario 28

	void caller28(Object arg1, Object arg2, Runnable arg3)
		{
		//called28(arg1, arg2, arg3);
		}

	// Scenario 29

	void caller29(Object arg1, Object arg2, Collection arg3)
		{
		//called28(arg1, arg2, arg3);
		}

	// Scenario 3

	public void caller3(ArrayList arg)
		{
		called3(arg);
		}

	public <C extends Collection, ArrayList extends C> void called3(C arg)
		{
		}

	// Scenario 30

	void caller30(Object arg1, Object arg2, Collection<?> arg3)
		{
		//called28(arg1, arg2, arg3);
		}

	// Scenario 31

	void caller31(Object arg1, Object arg2, Collection<?> arg3)
		{
		//called31(arg1, arg2, arg3);
		}

	// Scenario 32

	void caller32(Object arg1, Object arg2, Collection arg3)
		{
		called31(arg1, arg2, arg3);
		}

	<A, B, C extends Collection<A>> void called31(A arg1, B arg2, C arg3)
		{
		}

	// Scenario 33

	void caller33(Object arg1, Object arg2, Collection arg3)
		{
		//called33(arg1, arg2, arg3);
		}

	// Scenario 34

	void caller34(Object arg1, Object arg2, Collection<? extends Runnable> arg3)
		{
		//called33(arg1, arg2, arg3);
		}

	// Scenario 35

	void caller35(Object arg1, Object arg2, Collection<Runnable> arg3)
		{
		//called33(arg1, arg2, arg3);
		}

	// Scenario 36

	<A extends Runnable> void caller36(Object arg1, Object arg2, Collection<A> arg3)
		{
		//called33(arg1, arg2, arg3);
		}

	// Scenario 37

	void caller37(Runnable arg1, Object arg2, Collection arg3)
		{
		called33(arg1, arg2, arg3);
		}

	<A extends Runnable, B, C extends Collection<A>> void called33(A arg1, B arg2, C arg3)
		{
		}

	// Scenario 38

	void caller38(Runnable arg1, Object arg2, Collection<? extends Runnable> arg3)
		{
		//called33(arg1, arg2, arg3);
		}

	// Scenario 39

	void caller39(Runnable arg1, Object arg2, Collection<Runnable> arg3)
		{
		called33(arg1, arg2, arg3);
		}

	// Scenario 4

	<D extends Collection<D>> void caller4(D arg)
		{
		called4(arg);
		}

	void called4(Collection arg)
		{
		}

	// Scenario 40

	<A extends Runnable> void caller40(Runnable arg1, Object arg2, Collection<A> arg3)
		{
		//called33(arg1, arg2, arg3);
		}

	// Scenario 41

	void caller41(ArrayList<?>[] arg)
		{
		called41(arg);
		}

	<A> void called41(A arg)
		{
		}

	// Scenario 42

	void caller42(ArrayList<?>[] arg)
		{
		//called42(arg);
		}

	// Scenario 43

	<B> void caller43(B[] arg)
		{
		called43(arg);
		}

	<A> void called43(A arg)
		{
		}

	// Scenario 44

	<B> void caller44(B[] arg)
		{
		//called44(arg);
		}

	// Scenario 45

	<B extends Runnable> void caller45(B[] arg)
		{
		called45(arg);
		}

	<A> void called45(A arg)
		{
		}

	// Scenario 46

	<B extends Runnable> void caller46(B[] arg)
		{
		//called44(arg);
		}

	// Scenario 5

	<A extends String & Runnable> void caller5(A arg1, Runnable arg2)
		{
		called5(arg1, arg2);
		}

	<B, D extends B> void called5(D arg1, B arg2)
		{
		}

	// Scenario 6

	<A extends String & Runnable> void caller6(A arg1, Runnable arg2, Object arg3)
		{
		//called6(arg1, arg2, arg3);
		}

	// Scenario 7

	void caller7(Object arg1, Runnable arg2, Object arg3)
		{
		//called6(arg1, arg2, arg3);
		}

	// Scenario 8

	<A extends Runnable> void caller8(A arg1, Runnable arg2, Object arg3)
		{
		//called6(arg1, arg2, arg3);
		}

	// Scenario 9

	<A extends Runnable, X extends A> void caller9(A arg1, Runnable arg2, X arg3)
		{
		// TODO fix algorithm here
		//called6(arg1, arg2, arg3);
		}

	void method1(Collection<?> arg)
		{
		method2(arg);
		}

	<A> void method2(Collection<A> arg)
		{
		}

	public void test1() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller1", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called1", new Class[]{Object.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test10() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller10", new Class[]{
				List.class,
				Runnable.class,
				List.class
		});
		Method called = this.getClass().getDeclaredMethod("called6", new Class[]{
				Object.class,
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test11() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller11", new Class[]{
				String.class,
				Runnable.class
		});
		Method called = this.getClass().getDeclaredMethod("called6", new Class[]{
				Object.class,
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test12() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller12", new Class[]{
				Runnable.class,
				Runnable.class
		});
		Method called = this.getClass().getDeclaredMethod("called12", new Class[]{
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		}

	public void test13() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller13", new Class[]{
				Integer.class,
				Runnable.class
		});
		Method called = this.getClass().getDeclaredMethod("called12", new Class[]{
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		}

	public void test14() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller14", new Class[]{
				Integer.class,
				String.class
		});
		Method called = this.getClass().getDeclaredMethod("called14", new Class[]{
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		}

	public void test15() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller15", new Class[]{
				Collection.class,
				Runnable.class
		});
		Method called = this.getClass().getDeclaredMethod("called15", new Class[]{
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		}

	public void test16() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller16", new Class[]{
				Integer.class,
				Runnable.class
		});
		Method called = this.getClass().getDeclaredMethod("called15", new Class[]{
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		}

	public void test17() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller17", new Class[]{
				Collection.class,
				Collection.class
		});
		Method called = this.getClass().getDeclaredMethod("called15", new Class[]{
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		}

	public void test18() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller18", new Class[]{
				Collection.class,
				Collection.class
		});
		Method called = this.getClass().getDeclaredMethod("called15", new Class[]{
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		}

	public void test19() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller19", new Class[]{
				Collection.class,
				String.class
		});
		Method called = this.getClass().getDeclaredMethod("called19", new Class[]{
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test2() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller2", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called2", new Class[]{Collection.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test20() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller20", new Class[]{
				Collection.class,
				Collection.class
		});
		Method called = this.getClass().getDeclaredMethod("called19", new Class[]{
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		}

	public void test21() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller21", new Class[]{
				List.class,
				Collection.class
		});
		Method called = this.getClass().getDeclaredMethod("called19", new Class[]{
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		}

	public void test22() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller22", new Class[]{
				Collection.class,
				HashSet.class
		});
		Method called = this.getClass().getDeclaredMethod("called19", new Class[]{
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test23() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller23", new Class[]{
				Object.class,
				List.class
		});
		Method called = this.getClass().getDeclaredMethod("called19", new Class[]{
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test24() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller24", new Class[]{Collection[].class});
		Method called = this.getClass().getDeclaredMethod("called24", new Class[]{Object.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test25() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller25", new Class[]{Object[].class});
		Method called = this.getClass().getDeclaredMethod("called24", new Class[]{Object.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test26() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller26", new Class[]{Collection[].class});
		Method called = this.getClass().getDeclaredMethod("called26", new Class[]{Collection.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test27() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller27", new Class[]{Object[].class});
		Method called = this.getClass().getDeclaredMethod("called26", new Class[]{Collection.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test28() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller28", new Class[]{
				Object.class,
				Object.class,
				Runnable.class
		});
		Method called = this.getClass().getDeclaredMethod("called28", new Class[]{
				Object.class,
				Object.class,
				Runnable.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[2], caller.getGenericParameterTypes()[2],
		                                   hierarchy));
		}

	public void test29() throws Exception
		{
		Method caller = this.getClass()
				.getDeclaredMethod("caller29", new Class[]{
						Object.class,
						Object.class,
						Collection.class
				});
		Method called = this.getClass().getDeclaredMethod("called28", new Class[]{
				Object.class,
				Object.class,
				Runnable.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[2], caller.getGenericParameterTypes()[2],
		                                   hierarchy));
		}

	public void test3() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller3", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called3", new Class[]{Collection.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test30() throws Exception
		{
		Method caller = this.getClass()
				.getDeclaredMethod("caller30", new Class[]{
						Object.class,
						Object.class,
						Collection.class
				});
		Method called = this.getClass().getDeclaredMethod("called28", new Class[]{
				Object.class,
				Object.class,
				Runnable.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[2], caller.getGenericParameterTypes()[2],
		                                   hierarchy));
		}

	public void test31() throws Exception
		{
		Method caller = this.getClass()
				.getDeclaredMethod("caller31", new Class[]{
						Object.class,
						Object.class,
						Collection.class
				});
		Method called = this.getClass()
				.getDeclaredMethod("called31", new Class[]{
						Object.class,
						Object.class,
						Collection.class
				});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[2], caller.getGenericParameterTypes()[2],
		                                   hierarchy));
		}

	public void test32() throws Exception
		{
		Method caller = this.getClass()
				.getDeclaredMethod("caller32", new Class[]{
						Object.class,
						Object.class,
						Collection.class
				});
		Method called = this.getClass()
				.getDeclaredMethod("called31", new Class[]{
						Object.class,
						Object.class,
						Collection.class
				});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[2], caller.getGenericParameterTypes()[2],
		                                  hierarchy));
		}

	public void test33() throws Exception
		{
		Method caller = this.getClass()
				.getDeclaredMethod("caller33", new Class[]{
						Object.class,
						Object.class,
						Collection.class
				});
		Method called = this.getClass()
				.getDeclaredMethod("called33", new Class[]{
						Runnable.class,
						Object.class,
						Collection.class
				});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test34() throws Exception
		{
		Method caller = this.getClass()
				.getDeclaredMethod("caller34", new Class[]{
						Object.class,
						Object.class,
						Collection.class
				});
		Method called = this.getClass()
				.getDeclaredMethod("called33", new Class[]{
						Runnable.class,
						Object.class,
						Collection.class
				});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test35() throws Exception
		{
		Method caller = this.getClass()
				.getDeclaredMethod("caller35", new Class[]{
						Object.class,
						Object.class,
						Collection.class
				});
		Method called = this.getClass()
				.getDeclaredMethod("called33", new Class[]{
						Runnable.class,
						Object.class,
						Collection.class
				});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test36() throws Exception
		{
		Method caller = this.getClass()
				.getDeclaredMethod("caller36", new Class[]{
						Object.class,
						Object.class,
						Collection.class
				});
		Method called = this.getClass()
				.getDeclaredMethod("called33", new Class[]{
						Runnable.class,
						Object.class,
						Collection.class
				});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test37() throws Exception
		{
		Method caller = this.getClass()
				.getDeclaredMethod("caller37", new Class[]{
						Runnable.class,
						Object.class,
						Collection.class
				});
		Method called = this.getClass()
				.getDeclaredMethod("called33", new Class[]{
						Runnable.class,
						Object.class,
						Collection.class
				});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[2], caller.getGenericParameterTypes()[2],
		                                  hierarchy));
		}

	public void test38() throws Exception
		{
		Method caller = this.getClass()
				.getDeclaredMethod("caller38", new Class[]{
						Runnable.class,
						Object.class,
						Collection.class
				});
		Method called = this.getClass()
				.getDeclaredMethod("called33", new Class[]{
						Runnable.class,
						Object.class,
						Collection.class
				});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[2], caller.getGenericParameterTypes()[2],
		                                   hierarchy));
		}

	public void test39() throws Exception
		{
		Method caller = this.getClass()
				.getDeclaredMethod("caller39", new Class[]{
						Runnable.class,
						Object.class,
						Collection.class
				});
		Method called = this.getClass()
				.getDeclaredMethod("called33", new Class[]{
						Runnable.class,
						Object.class,
						Collection.class
				});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[2], caller.getGenericParameterTypes()[2],
		                                  hierarchy));
		}

	public void test4() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller4", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called4", new Class[]{Collection.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test40() throws Exception
		{
		Method caller = this.getClass()
				.getDeclaredMethod("caller40", new Class[]{
						Runnable.class,
						Object.class,
						Collection.class
				});
		Method called = this.getClass()
				.getDeclaredMethod("called33", new Class[]{
						Runnable.class,
						Object.class,
						Collection.class
				});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[2], caller.getGenericParameterTypes()[2],
		                                   hierarchy));
		}

	public void test41() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller41", new Class[]{ArrayList[].class});
		Method called = this.getClass().getDeclaredMethod("called41", new Class[]{Object.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test42() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller42", new Class[]{ArrayList[].class});
		Method called = this.getClass().getDeclaredMethod("called42", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test43() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller43", new Class[]{Object[].class});
		Method called = this.getClass().getDeclaredMethod("called43", new Class[]{Object.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test44() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller44", new Class[]{Object[].class});
		Method called = this.getClass().getDeclaredMethod("called44", new Class[]{Runnable.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test45() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller45", new Class[]{Runnable[].class});
		Method called = this.getClass().getDeclaredMethod("called45", new Class[]{Object.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test46() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller46", new Class[]{Runnable[].class});
		Method called = this.getClass().getDeclaredMethod("called46", new Class[]{Runnable.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test5() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller5", new Class[]{
				String.class,
				Runnable.class
		});
		Method called = this.getClass().getDeclaredMethod("called5", new Class[]{
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		}

	public void test6() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller6", new Class[]{
				String.class,
				Runnable.class,
				Object.class
		});
		Method called = this.getClass().getDeclaredMethod("called6", new Class[]{
				Object.class,
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[2], caller.getGenericParameterTypes()[2],
		                                   hierarchy));
		}

	public void test7() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller7", new Class[]{
				Object.class,
				Runnable.class,
				Object.class
		});
		Method called = this.getClass().getDeclaredMethod("called6", new Class[]{
				Object.class,
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test8() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller8", new Class[]{
				Runnable.class,
				Runnable.class,
				Object.class
		});
		Method called = this.getClass().getDeclaredMethod("called6", new Class[]{
				Object.class,
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[2], caller.getGenericParameterTypes()[2],
		                                   hierarchy));
		}

	public void test9() throws Exception
		{
		Method caller = this.getClass()
				.getDeclaredMethod("caller9", new Class[]{
						Runnable.class,
						Runnable.class,
						Runnable.class
				});
		Method called = this.getClass().getDeclaredMethod("called6", new Class[]{
				Object.class,
				Object.class,
				Object.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[2], caller.getGenericParameterTypes()[2],
		                                  hierarchy));
		}

	//   // Scenario 28
	//
	//   void caller28(Collection<Integer>[] arg)
	//   {
	//      called28(arg);
	//   }
	//
	//   <B extends Collection[]> void called28(B arg) {}
	//
	//   public void test28() throws Exception
	//   {
	//      Method caller = this.getClass().getDeclaredMethod("caller28", new Class[]{Collection[].class});
	//      Method called = this.getClass().getDeclaredMethod("called28", new Class[]{Collection[].class});
	//      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
	//            caller.getGenericParameterTypes()[0], hierarchy));
	//   }
	//
	//   // Scenario 25
	//
	//   <A> void caller25(A[] arg)
	//   {
	//      called24(arg);
	//   }
	//
	//   public void test25() throws Exception
	//   {
	//      Method caller = this.getClass().getDeclaredMethod("caller25", new Class[]{Object[].class});
	//      Method called = this.getClass().getDeclaredMethod("called24", new Class[]{Object.class});
	//      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
	//            caller.getGenericParameterTypes()[0], hierarchy));
	//   }
	//
	//// Scenario 24
	//
	//   void caller24(Collection<Integer>[] arg)
	//   {
	//      called24(arg);
	//   }
	//
	//   <B extends Object[]> void called24(B arg) {}
	//
	//   public void test24() throws Exception
	//   {
	//      Method caller = this.getClass().getDeclaredMethod("caller24", new Class[]{Collection[].class});
	//      Method called = this.getClass().getDeclaredMethod("called24", new Class[]{Object.class});
	//      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
	//            caller.getGenericParameterTypes()[0], hierarchy));
	//   }
	//
	//   // Scenario 25
	//
	//   <A> void caller25(A[] arg)
	//   {
	//      called24(arg);
	//   }
	//
	//   public void test25() throws Exception
	//   {
	//      Method caller = this.getClass().getDeclaredMethod("caller25", new Class[]{Object[].class});
	//      Method called = this.getClass().getDeclaredMethod("called24", new Class[]{Object.class});
	//      assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0],
	//            caller.getGenericParameterTypes()[0], hierarchy));
	//   }
	}