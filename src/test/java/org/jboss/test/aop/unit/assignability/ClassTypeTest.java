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

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class ClassTypeTest extends VariableTargetAlgorithmTest
	{
	// -------------------------- OTHER METHODS --------------------------

	void called11(Throwable arg)
		{
		}

	void called13(Runnable arg)
		{
		}

	void called15(Runnable[] arg)
		{
		}

	void called17(Runnable arg)
		{
		}

	void called24(Map arg)
		{
		}

	void called25(Map[] arg)
		{
		}

	void called27(String arg)
		{
		}

	void called40(Runnable arg)
		{
		}

	void called5(Map arg)
		{
		}

	void called7(String arg)
		{
		}

	void called9(Set arg)
		{
		}

	// Scenario 10

	<A extends Runnable> void caller10(Collection<A> arg)
		{
		called10(arg);
		}

	void called10(Object arg)
		{
		}

	// Scenario 11

	<A extends Runnable> void caller11(Collection<A> arg)
		{
		//called11(arg);
		}

	// Scenario 12

	<A> void caller12(A[] arg)
		{
		called12(arg);
		}

	void called12(Object arg)
		{
		}

	// Scenario 13

	<A> void caller13(A[] arg)
		{
		//called13(arg);
		}

	// Scenario 14

	<A> void caller14(A[] arg)
		{
		called14(arg);
		}

	void called14(Object[] arg)
		{
		}

	// Scenario 15

	<A> void caller15(A[] arg)
		{
		//called15(arg);
		}

	// Scenario 16

	<A extends Runnable> void caller16(A[] arg)
		{
		called16(arg);
		}

	void called16(Object arg)
		{
		}

	// Scenario 17

	<A extends Runnable> void caller17(A[] arg)
		{
		//called17(arg);
		}

	// Scenario 18

	<A extends Runnable> void caller18(A[] arg)
		{
		called18(arg);
		}

	void called18(Object[] arg)
		{
		}

	// Scenario 19

	<A extends Runnable> void caller19(A[] arg)
		{
		called19(arg);
		}

	void called19(Runnable[] arg)
		{
		}

	// Scenario 20

	void caller20(ArrayList<String>[] arg)
		{
		called20(arg);
		}

	void called20(ArrayList[] arg)
		{
		}

	// Scenario 21

	void caller21(ArrayList<String>[] arg)
		{
		called21(arg);
		}

	void called21(List[] arg)
		{
		}

	// Scenario 22

	void caller22(ArrayList<String>[] arg)
		{
		called22(arg);
		}

	void called22(Object[] arg)
		{
		}

	// Scenario 23

	void caller23(ArrayList<String>[] arg)
		{
		called23(arg);
		}

	void called23(Object arg)
		{
		}

	// Scenario 24

	void caller24(ArrayList<String>[] arg)
		{
		//called24(arg);
		}

	// Scenario 25

	void caller25(ArrayList<String>[] arg)
		{
		//called25(arg);
		}

	// Scenario 26

	<A> void caller26(A arg)
		{
		called26(arg);
		}

	void called26(Object arg)
		{
		}

	// Scenario 27

	<A> void caller27(A arg)
		{
		//called27(arg);
		}

	// Scenario 28

	<A extends Runnable> void caller28(A arg)
		{
		called28(arg);
		}

	void called28(Runnable arg)
		{
		}

	// Scenario 29

	<A extends Runnable> void caller29(A arg)
		{
		called29(arg);
		}

	void called29(Object arg)
		{
		}

	// Scenario 30

	<B extends Collection & Runnable & Serializable, A extends B> void caller30(A arg)
		{
		called30(arg);
		}

	void called30(Serializable arg)
		{
		}

	// Scenario 31

	<E, D extends List<E> & Runnable & Serializable, C extends D, B extends C, A extends B> void caller31(A arg)
		{
		called31(arg);
		}

	void called31(Collection arg)
		{
		}

	// Scenario 32

	<D extends ArrayList & Runnable> void caller32(D arg)
		{
		called32(arg);
		}

	void called32(List arg)
		{
		}

	// Scenario 33

	<A extends List> void caller33(A arg)
		{
		called33(arg);
		}

	void called33(List arg)
		{
		}

	// Scenario 34

	<A extends ArrayList> void caller34(A arg)
		{
		called33(arg);
		}

	// Scenario 35

	<B extends List, A extends B> void caller35(A arg)
		{
		called33(arg);
		}

	// Scenario 36

	<A extends Collection> void caller36(A arg)
		{
		//called33(arg);
		}

	// Scenario 37

	<A> void caller37(A arg)
		{
		//called33(arg);
		}

	// Scenario 38

	<B extends Collection, A extends B> void caller38(A arg)
		{
		//called33(arg);
		}

	// Scenario 39

	<B extends Collection, C extends B, A extends B> void caller39(A arg)
		{
		//called33(arg);
		}

	// Scenario 4

	void caller4(ArrayList<?> arg)
		{
		called4(arg);
		}

	void called4(Collection arg)
		{
		}

	// Scenario 40

	<B extends Collection, A extends B> void caller40(A arg)
		{
		//called40(arg);
		}

	// Scenario 5

	void caller5(ArrayList<?> arg)
		{
		//called5(arg);
		}

	// Scenario 6

	void caller6(ArrayList<String> arg)
		{
		called6(arg);
		}

	void called6(ArrayList arg)
		{
		}

	// Scenario 7

	void caller7(ArrayList<String> arg)
		{
		//called7(arg);
		}

	// Scenario 8

	<A> void caller8(ArrayList<A> arg)
		{
		called8(arg);
		}

	void called8(List arg)
		{
		}

	// Scenario 9

	<A> void caller9(ArrayList<A> arg)
		{
		//called9(arg);
		}

	public void test1()
		{
		assertTrue(algorithm.isAssignable(Object.class, String.class, hierarchy));
		}

	public void test10() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller10", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called10", new Class[]{Object.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test11() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller11", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called11", new Class[]{Throwable.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test12() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller12", new Class[]{Object[].class});
		Method called = this.getClass().getDeclaredMethod("called12", new Class[]{Object.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test13() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller13", new Class[]{Object[].class});
		Method called = this.getClass().getDeclaredMethod("called13", new Class[]{Runnable.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test14() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller14", new Class[]{Object[].class});
		Method called = this.getClass().getDeclaredMethod("called14", new Class[]{Object[].class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test15() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller15", new Class[]{Object[].class});
		Method called = this.getClass().getDeclaredMethod("called15", new Class[]{Runnable[].class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test16() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller16", new Class[]{Runnable[].class});
		Method called = this.getClass().getDeclaredMethod("called16", new Class[]{Object.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test17() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller17", new Class[]{Runnable[].class});
		Method called = this.getClass().getDeclaredMethod("called17", new Class[]{Runnable.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test18() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller18", new Class[]{Runnable[].class});
		Method called = this.getClass().getDeclaredMethod("called15", new Class[]{Runnable[].class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test19() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller19", new Class[]{Runnable[].class});
		Method called = this.getClass().getDeclaredMethod("called19", new Class[]{Runnable[].class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test2()
		{
		assertTrue(algorithm.isAssignable(Runnable.class, Runnable.class, hierarchy));
		}

	public void test20() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller20", new Class[]{ArrayList[].class});
		Method called = this.getClass().getDeclaredMethod("called20", new Class[]{ArrayList[].class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test21() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller21", new Class[]{ArrayList[].class});
		Method called = this.getClass().getDeclaredMethod("called21", new Class[]{List[].class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test22() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller22", new Class[]{ArrayList[].class});
		Method called = this.getClass().getDeclaredMethod("called22", new Class[]{Object[].class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test23() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller23", new Class[]{ArrayList[].class});
		Method called = this.getClass().getDeclaredMethod("called23", new Class[]{Object.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test24() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller24", new Class[]{ArrayList[].class});
		Method called = this.getClass().getDeclaredMethod("called24", new Class[]{Map.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test25() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller25", new Class[]{ArrayList[].class});
		Method called = this.getClass().getDeclaredMethod("called25", new Class[]{Map[].class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test26() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller26", new Class[]{Object.class});
		Method called = this.getClass().getDeclaredMethod("called26", new Class[]{Object.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test27() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller27", new Class[]{Object.class});
		Method called = this.getClass().getDeclaredMethod("called27", new Class[]{String.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test28() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller28", new Class[]{Runnable.class});
		Method called = this.getClass().getDeclaredMethod("called28", new Class[]{Runnable.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test29() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller29", new Class[]{Runnable.class});
		Method called = this.getClass().getDeclaredMethod("called29", new Class[]{Object.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test3()
		{
		assertFalse(algorithm.isAssignable(Runnable.class, Object.class, hierarchy));
		}

	public void test30() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller30", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called30", new Class[]{Serializable.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test31() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller31", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called31", new Class[]{Collection.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test32() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller32", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called32", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test33() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller33", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called33", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test34() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller34", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called33", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test35() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller35", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called33", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test36() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller36", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called33", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test37() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller37", new Class[]{Object.class});
		Method called = this.getClass().getDeclaredMethod("called33", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test38() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller38", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called33", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test39() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller39", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called33", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test4() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller4", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called4", new Class[]{Collection.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test40() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller40", new Class[]{Collection.class});
		Method called = this.getClass().getDeclaredMethod("called40", new Class[]{Runnable.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test5() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller5", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called5", new Class[]{Map.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test6() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller6", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called6", new Class[]{ArrayList.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test7() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller7", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called7", new Class[]{String.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test8() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller8", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called8", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test9() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller9", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called9", new Class[]{Set.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}
	}