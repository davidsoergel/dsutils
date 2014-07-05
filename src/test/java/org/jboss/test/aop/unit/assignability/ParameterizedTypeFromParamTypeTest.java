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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 * @version $Id$
 */
public class ParameterizedTypeFromParamTypeTest extends ParameterizedTypeTest
	{
	// -------------------------- OTHER METHODS --------------------------

	void called133(HashMap<Runnable, Integer> arg)
		{
		}

	void called141(Collection<Object> arg)
		{
		}

	<B> void called53(ArrayList<B> arg1, ArrayList<B> arg2)
		{
		}

	void called54(ArrayList<ArrayList> arg)
		{
		}

	void called57(ArrayList<Runnable> arg)
		{
		}

	<A extends List> void called91(List<A> arg1, List<A> arg2)
		{
		}

	// Scenario 1

	<A> void caller1(ArrayList<A> arg)
		{
		called1(arg);
		}

	// Scenario 10

	<A> void caller10(ArrayList<A> arg)
		{
		called10(arg);
		}

	// Scenario 100

	<B extends List> void caller100(ArrayList<B> arg1, List<String> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 101

	<B extends List> void caller101(List<B> arg1, ArrayList<List> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 102

	<B extends List> void caller102(List<B> arg1, List<ArrayList> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 103

	void caller103(List<? extends ArrayList> arg1, List<List> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 104

	void caller104(List<? extends ArrayList> arg1, List<ArrayList> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 105

	void caller105(List<? extends List> arg1, List<ArrayList> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 106

	void caller106(List<List> arg1, List<? extends ArrayList> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 107

	void caller107(List<ArrayList> arg1, List<? extends ArrayList> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 108

	void caller108(List<ArrayList> arg1, List<? extends List> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 109

	<B> void caller109(List<B[]> arg)
		{
		called109(arg);
		}

	<A> void called109(List<A> arg)
		{
		}

	// Scenario 11

	<A> void caller11(ArrayList<A> arg)
		{
		called11(arg);
		}

	// Scenario 110

	void caller110(List<? super Object[]> arg)
		{
		called109(arg);
		}

	// Scenario 111

	void caller111(List<Object[]> arg)
		{
		called109(arg);
		}

	// Scenario 112

	void caller112(List<List<?>[]> arg)
		{
		called109(arg);
		}

	// Scenario 113

	void caller113(List<?> arg)
		{
		called109(arg);
		}

	// Scenario 114

	void caller114(List<List<?>[]> arg)
		{
		called114(arg, arg);
		}

	<A> void called114(List<A> arg, List<A> arg2)
		{
		}

	// Scenario 115

	void caller115(List<List<?>[]> arg1, List<ArrayList<?>[]> arg2)
		{
		//called114(arg1, arg2);
		}

	// Scenario 116

	<A> void caller116(List<List<?>[]> arg1, List<ArrayList<A>[]> arg2)
		{
		//called114(arg, arg2);
		}

	// Scenario 117

	void caller117(List<Object> arg)
		{
		called117(arg);
		}

	void called117(List<?> arg)
		{
		}

	// Scenario 118

	void caller118(List<List> arg)
		{
		called117(arg);
		}

	// Scenario 119

	void caller119(List<ArrayList> arg)
		{
		called117(arg);
		}

	// Scenario 12

	<A> void caller12(ArrayList<A> arg)
		{
		//called12(arg);
		}

	// Scenario 120

	void caller120(ArrayList<Object> arg)
		{
		called117(arg);
		}

	// Scenario 121

	void caller121(ArrayList<List> arg)
		{
		called117(arg);
		}

	// Scenario 122

	void caller122(ArrayList<ArrayList> arg)
		{
		called117(arg);
		}

	// Scenario 123

	void caller123(List<Object> arg)
		{
		//called123(arg);
		}

	// Scenario 124

	void caller124(List<List> arg)
		{
		called123(arg);
		}

	void called123(List<? extends List> arg)
		{
		}

	// Scenario 125

	void caller125(List<ArrayList> arg)
		{
		called123(arg);
		}

	// Scenario 126

	void caller126(ArrayList<Object> arg)
		{
		//called123(arg);
		}

	// Scenario 127

	void caller127(ArrayList<List> arg)
		{
		called123(arg);
		}

	// Scenario 128

	void caller128(ArrayList<ArrayList> arg)
		{
		called123(arg);
		}

	// Scenario 129

	void caller129(ArrayList<ArrayList> arg)
		{
		called123(arg);
		}

	// Scenario 13

	<A> void caller13(ArrayList<A> arg)
		{
		//called13(arg);
		}

	// Scenario 130

	void caller130(MyClass_<Integer, Runnable> arg)
		{
		called130(arg);
		}

	void called130(HashMap<String, Integer> arg)
		{
		}

	// Scenario 131

	void caller131(MyClass_<Integer, Runnable> arg)
		{
		called130(arg);
		}

	// Scenario 132

	void caller132(MyClass3_<Integer, Runnable> arg)
		{
		called130(arg);
		}

	// Scenario 133

	void caller133(MyClass_<Integer, Runnable> arg)
		{
		//called133(arg);
		}

	// Scenario 134

	void caller134(NewClass1<String> arg)
		{
		called134(arg);
		}

	void called134(ArrayList<Runnable> arg)
		{
		}

	// Scenario 135

	void caller135(NewClass2<String> arg)
		{
		called134(arg);
		}

	// Scenario 136

	void caller136(NewClass3<String> arg)
		{
		called134(arg);
		}

	// Scenario 137

	void caller137(List<String> arg)
		{
		called137(arg);
		}

	void called137(List<String> arg)
		{
		}

	// Scenario 138

	void caller138(List<String> arg)
		{
		called138(arg);
		}

	void called138(List<? extends String> arg)
		{
		}

	// Scenario 139

	void caller139(ArrayList<String> arg)
		{
		called139(arg);
		}

	void called139(List<? extends String> arg)
		{
		}

	// Scenario 14

	<A> void caller14(ArrayList<A> arg)
		{
		//called14(arg);
		}

	// Scenario 140

	void caller140(ArrayList<String> arg)
		{
		called140(arg);
		}

	void called140(List<String> arg)
		{
		}

	// Scenario 141

	void caller141(ArrayList<String> arg)
		{
		//called141(arg);
		}

	// Scenario 142

	void caller142(ArrayList<List> arg)
		{
		called142(arg);
		}

	void called142(ArrayList<? super List> arg)
		{
		}

	// Scenario 143

	void caller143(ArrayList<Collection> arg)
		{
		called142(arg);
		}

	// Scenario 144

	void caller144(ArrayList<ArrayList> arg)
		{
		//called142(arg);
		}

	// Scenario 145

	<A extends List> void caller145(ArrayList<A> arg)
		{
		//called142(arg);
		}

	// Scenario 146

	<A extends Collection> void caller146(ArrayList<A> arg)
		{
		//called142(arg);
		}

	// Scenario 147

	<A extends ArrayList> void caller147(ArrayList<ArrayList> arg)
		{
		//called142(arg);
		}

	// Scenario 15

	<A> void caller15(ArrayList<A> arg)
		{
		//called15(arg);
		}

	// Scenario 16

	<A> void caller16(ArrayList<A> arg)
		{
		//called16(arg);
		}

	// Scenario 17

	<A> void caller17(ArrayList<A> arg)
		{
		//called17(arg);
		}

	// Scenario 18

	<A> void caller18(Class5<String, Integer> arg)
		{
		//called1(arg);
		}

	// Scenario 19

	<A> void caller19(Class5<String, Integer> arg)
		{
		//called2(arg);
		}

	// Scenario 2

	<A> void caller2(ArrayList<A> arg)
		{
		called2(arg);
		}

	// Scenario 20

	<A> void caller20(Class5<String, Integer> arg)
		{
		//called3(arg);
		}

	// Scenario 21

	<A> void caller21(Class5<String, Integer> arg)
		{
		//called4(arg);
		}

	// Scenario 22

	<A> void caller22(Class5<String, Integer> arg)
		{
		//called5(arg);
		}

	// Scenario 23

	<A> void caller23(Class5<String, Integer> arg)
		{
		//called6(arg);
		}

	// Scenario 24

	<A> void caller24(Class5<String, Integer> arg)
		{
		//called7(arg);
		}

	// Scenario 25

	<A> void caller25(Class5<String, Integer> arg)
		{
		//called8(arg);
		}

	// Scenario 26

	<A> void caller26(Class5<String, Integer> arg)
		{
		//called9(arg);
		}

	// Scenario 27

	<A> void caller27(Class5<String, Integer> arg)
		{
		//called10(arg);
		}

	// Scenario 28

	<A> void caller28(Class5<String, Integer> arg)
		{
		//called11(arg);
		}

	// Scenario 29

	<A> void caller29(Class5<String, Integer> arg)
		{
		//called12(arg);
		}

	// Scenario 3

	<A> void caller3(ArrayList<A> arg)
		{
		called3(arg);
		}

	// Scenario 30

	<A> void caller30(Class5<String, Integer> arg)
		{
		//called13(arg);
		}

	// Scenario 31

	<A> void caller31(Class5<String, Integer> arg)
		{
		//called14(arg);
		}

	// Scenario 32

	<A> void caller32(Class5<String, Integer> arg)
		{
		//called15(arg);
		}

	// Scenario 33

	<A> void caller33(Class5<String, Integer> arg)
		{
		//called16(arg);
		}

	// Scenario 34

	<A> void caller34(Class5<String, Integer> arg)
		{
		called17(arg);
		}

	// Scenario 35

	<B extends Integer, A extends ArrayList<B>> void caller35(A arg)
		{
		called1(arg);
		}

	// Scenario 36

	<B extends Integer, A extends ArrayList<B>> void caller36(A arg)
		{
		called2(arg);
		}

	// Scenario 37

	<B extends Integer, A extends ArrayList<B>> void caller37(A arg)
		{
		called3(arg);
		}

	// Scenario 38

	<B extends Integer, A extends ArrayList<B>> void caller38(A arg)
		{
		//called4(arg);
		}

	// Scenario 39

	<B extends Integer, A extends ArrayList<B>> void caller39(A arg)
		{
		//called5(arg);
		}

	// Scenario 4

	<A> void caller4(ArrayList<A> arg)
		{
		//called4(arg);
		}

	// Scenario 40

	<B extends Integer, A extends ArrayList<B>> void caller40(A arg)
		{
		//called6(arg);
		}

	// Scenario 41

	<B extends Integer, A extends ArrayList<B>> void caller41(A arg)
		{
		//called7(arg);
		}

	// Scenario 42

	<B extends Integer, A extends ArrayList<B>> void caller42(A arg)
		{
		called8(arg);
		}

	// Scenario 43

	<B extends Integer, A extends ArrayList<B>> void caller43(A arg)
		{
		called9(arg);
		}

	// Scenario 44

	<B extends Integer, A extends ArrayList<B>> void caller44(A arg)
		{
		called10(arg);
		}

	// Scenario 45

	<B extends Integer, A extends ArrayList<B>> void caller45(A arg)
		{
		called11(arg);
		}

	// Scenario 46

	<B extends Integer, A extends ArrayList<B>> void caller46(A arg)
		{
		//called12(arg);
		}

	// Scenario 47

	<B extends Integer, A extends ArrayList<B>> void caller47(A arg)
		{
		//called13(arg);
		}

	// Scenario 48

	<B extends Integer, A extends ArrayList<B>> void caller48(A arg)
		{
		//called14(arg);
		}

	// Scenario 49

	<B extends Integer, A extends ArrayList<B>> void caller49(A arg)
		{
		//called15(arg);
		}

	// Scenario 5

	<A> void caller5(ArrayList<A> arg)
		{
		//called5(arg);
		}

	// Scenario 50

	<B extends Integer, A extends ArrayList<B>> void caller50(A arg)
		{
		called16(arg);
		}

	// Scenario 51

	<B extends Integer, A extends ArrayList<B>> void caller51(A arg)
		{
		//called17(arg);
		}

	// Scenario 52

	void caller52(ArrayList<?> list)
		{
		called52(list);
		}

	<A> void called52(ArrayList<A> list)
		{
		}

	// Scenario 53

	void caller53(ArrayList<String> arg1, ArrayList<Integer> arg2)
		{
		//called53(arg2, arg1);
		}

	// Scenario 54

	void caller54(ArrayList<ArrayList<String>> arg)
		{
		//called54(arg);
		}

	// Scenario 55

	<A> void caller55(ArrayList<ArrayList<A>> arg)
		{
		//called54(arg);
		}

	// Scenario 56

	<A> void caller56(ArrayList<ArrayList<A>> arg)
		{
		//called54(arg);
		}

	// Scenario 57

	<A extends Runnable> void caller57(ArrayList<A> arg)
		{
		//called57(arg);
		}

	// Scenario 58

	void caller58(ArrayList<List<String>> arg)
		{
		called58(arg);
		}

	void called58(List<List<String>> arg)
		{
		}

	// Scenario 59

	void caller59(ArrayList<ArrayList<String>> arg)
		{
		//called58(arg);
		}

	// Scenario 6

	<A> void caller6(ArrayList<A> arg)
		{
		//called6(arg);
		}

	// Scenario 60

	void caller60(ArrayList<List> arg)
		{
		//called58(arg);
		}

	// Scenario 61

	void caller61(ArrayList<ArrayList<String>> arg)
		{
		//called58(arg);
		}

	// Scenario 62

	void caller62(ArrayList<ArrayList> arg)
		{
		//called58(arg);
		}

	// Scenario 63

	void caller63(List<List<String>> arg)
		{
		called58(arg);
		}

	// Scenario 64

	void caller64(List<ArrayList<String>> arg)
		{
		//called58(arg);
		}

	// Scenario 65

	void caller65(List<List> arg)
		{
		//called58(arg);
		}

	// Scenario 66

	void caller66(List<ArrayList<String>> arg)
		{
		//called58(arg);
		}

	// Scenario 67

	void caller67(List<ArrayList> arg)
		{
		//called58(arg);
		}

	// Scenario 68

	void caller68(List<List> arg)
		{
		called68(arg);
		}

	<A extends List> void called68(List<A> arg)
		{
		}

	// Scenario 69

	void caller69(List<ArrayList> arg)
		{
		called68(arg);
		}

	// Scenario 7

	<A> void caller7(ArrayList<A> arg)
		{
		//called7(arg);
		}

	// Scenario 70

	void caller70(ArrayList<ArrayList> arg)
		{
		called68(arg);
		}

	// Scenario 71

	void caller71(List<Collection> arg)
		{
		//called68(arg);
		}

	// Scenario 72

	void caller72(List<List<String>> arg)
		{
		called68(arg);
		}

	// Scenario 73

	void caller73(ArrayList<List<String>> arg)
		{
		called68(arg);
		}

	// Scenario 74

	void caller74(List<List<? extends Runnable>> arg)
		{
		called68(arg);
		}

	// Scenario 75

	void caller75(ArrayList<List<? extends Runnable>> arg)
		{
		called68(arg);
		}

	// Scenario 76

	void caller76(List<List<List>> arg)
		{
		called68(arg);
		}

	// Scenario 77

	void caller77(ArrayList<List<List>> arg)
		{
		called68(arg);
		}

	// Scenario 78

	<B extends List> void caller78(List<B> arg)
		{
		called68(arg);
		}

	// Scenario 79

	<B extends ArrayList> void caller79(List<B> arg)
		{
		called68(arg);
		}

	// Scenario 8

	<A> void caller8(ArrayList<A> arg)
		{
		//called8(arg);
		}

	// Scenario 80

	<B> void caller80(List<B> arg)
		{
		//called68(arg);
		}

	// Scenario 81

	<B extends List> void caller81(ArrayList<B> arg)
		{
		called68(arg);
		}

	// Scenario 82

	<B extends ArrayList> void caller82(ArrayList<B> arg)
		{
		called68(arg);
		}

	// Scenario 83

	<B> void caller83(ArrayList<B> arg)
		{
		//called68(arg);
		}

	// Scenario 84

	<B extends List> void caller84(List<B[]> arg)
		{
		//called68(arg);
		}

	// Scenario 85

	void caller85(List<?> arg)
		{
		//called68(arg);
		}

	// Scenario 86

	void caller86(List<? extends List> arg)
		{
		called68(arg);
		}

	// Scenario 87

	void caller87(List<? extends List<String>> arg)
		{
		called68(arg);
		}

	// Scenario 88

	void caller88(List<? extends ArrayList<String>> arg)
		{
		called68(arg);
		}

	// Scenario 89

	void caller89(List<? extends ArrayList<?>> arg)
		{
		called68(arg);
		}

	// Scenario 9

	<A> void caller9(ArrayList<A> arg)
		{
		called9(arg);
		}

	// Scenario 90

	void caller90(List<? extends ArrayList> arg)
		{
		called68(arg);
		}

	// Scenario 91

	void caller91(List<List<List>> arg1, ArrayList<List<String>> arg2)
		{
		//called91(arg1, arg2);
		//called68(arg, new ArrayList<List>());
		}

	// Scenario 92

	void caller92(List<List<List>> arg1, ArrayList<List> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 93

	void caller93(List<List<List>> arg1, List<? extends List> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 94

	void caller94(List<List<List>> arg1, List<List<? extends List>> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 95

	<B extends List, C extends List> void caller95(ArrayList<B> arg1, ArrayList<C> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 96

	<B extends List> void caller96(ArrayList<B> arg1, ArrayList<String> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 97

	<B extends List> void caller97(ArrayList<B> arg1, ArrayList<List> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 98

	<B extends List> void caller98(ArrayList<B> arg1, ArrayList<ArrayList> arg2)
		{
		//called91(arg1, arg2);
		}

	// Scenario 99

	<B extends List, C extends List> void caller99(List<B> arg1, ArrayList<C> arg2)
		{
		//called91(arg1, arg2);
		}

	void methodCaller5__(ArrayList<ArrayList[]> arg)
		{
		//methodCalled5(arg);
		}

	public void test1() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller1", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test10() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller10", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test100() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller100", new Class[]{
				ArrayList.class,
				List.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test101() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller101", new Class[]{
				List.class,
				ArrayList.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test102() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller102", new Class[]{
				List.class,
				List.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test103() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller103", new Class[]{
				List.class,
				List.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test104() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller104", new Class[]{
				List.class,
				List.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test105() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller105", new Class[]{
				List.class,
				List.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test106() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller106", new Class[]{
				List.class,
				List.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test107() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller107", new Class[]{
				List.class,
				List.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test108() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller108", new Class[]{
				List.class,
				List.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test109() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller109", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called109", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test11() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller11", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test110() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller110", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called109", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test111() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller111", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called109", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test112() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller112", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called109", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test113() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller113", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called109", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test114() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller114", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called114", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test115() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller115", new Class[]{
				List.class,
				List.class
		});
		Method called = this.getClass().getDeclaredMethod("called114", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test116() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller116", new Class[]{
				List.class,
				List.class
		});
		Method called = this.getClass().getDeclaredMethod("called114", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test117() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller117", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called117", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test118() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller118", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called117", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test119() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller119", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called117", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test12() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller12", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test120() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller120", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called117", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test121() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller121", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called117", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test122() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller122", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called117", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test123() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller123", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called123", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test124() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller124", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called123", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test125() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller125", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called123", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test126() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller126", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called123", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test127() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller127", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called123", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test128() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller128", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called123", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test129() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller129", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called123", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test13() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller13", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test130() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller130", new Class[]{MyClass_.class});
		Method called = this.getClass().getDeclaredMethod("called130", new Class[]{HashMap.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test131() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller131", new Class[]{MyClass_.class});
		Method called = this.getClass().getDeclaredMethod("called130", new Class[]{HashMap.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test132() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller132", new Class[]{MyClass3_.class});
		Method called = this.getClass().getDeclaredMethod("called130", new Class[]{HashMap.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test133() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller133", new Class[]{MyClass_.class});
		Method called = this.getClass().getDeclaredMethod("called133", new Class[]{HashMap.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test134() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller134", new Class[]{NewClass1.class});
		Method called = this.getClass().getDeclaredMethod("called134", new Class[]{ArrayList.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test135() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller135", new Class[]{NewClass2.class});
		Method called = this.getClass().getDeclaredMethod("called134", new Class[]{ArrayList.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test136() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller136", new Class[]{NewClass3.class});
		Method called = this.getClass().getDeclaredMethod("called134", new Class[]{ArrayList.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test137() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller137", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called137", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test138() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller138", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called138", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test139() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller139", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called139", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test14() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller14", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test140() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller140", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called140", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	/*
	 public void test141() throws Exception
		 {
		 Method caller = this.getClass().getDeclaredMethod("caller141", new Class[]{ArrayList.class});
		 Method called = this.getClass().getDeclaredMethod("called141", new Class[]{Collection.class});
		 assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
											hierarchy));
		 }
 */
	public void test142() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller142", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called142", new Class[]{ArrayList.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test143() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller143", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called142", new Class[]{ArrayList.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test144() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller144", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called142", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test145() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller145", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called142", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test146() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller146", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called142", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test147() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller147", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called142", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test15() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller15", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test16() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller16", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test17() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller17", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test18() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller18", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test19() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller19", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test2() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller2", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test20() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller20", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test21() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller21", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}


	public void test22() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller22", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test23() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller23", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test24() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller24", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test25() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller25", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test26() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller26", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test27() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller27", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test28() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller28", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test29() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller29", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test3() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller3", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test30() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller30", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test31() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller31", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test32() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller32", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test33() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller33", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test34() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller34", new Class[]{Class5.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test35() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller35", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called1", new Class[]{ArrayList.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test36() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller36", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called2", new Class[]{ArrayList.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test37() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller37", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called3", new Class[]{ArrayList.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test38() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller38", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test39() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller39", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test4() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller4", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called4", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test40() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller40", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test41() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller41", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test42() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller42", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test43() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller43", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test44() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller44", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called10", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test45() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller45", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called11", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test46() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller46", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called12", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test47() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller47", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called13", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test48() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller48", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called14", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test49() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller49", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called15", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test5() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller5", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called5", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test50() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller50", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called16", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test51() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller51", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called17", new Class[]{HashMap.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test52() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller52", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called52", new Class[]{ArrayList.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test53() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller53", new Class[]{
				ArrayList.class,
				ArrayList.class
		});
		Method called = this.getClass().getDeclaredMethod("called53", new Class[]{
				ArrayList.class,
				ArrayList.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	/*	public void test54() throws Exception
	   {
	   Method caller = this.getClass().getDeclaredMethod("caller54", new Class[]{ArrayList.class});
	   Method called = this.getClass().getDeclaredMethod("called54", new Class[]{ArrayList.class});
	   assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
										  hierarchy));
	   }

   public void test55() throws Exception
	   {
	   Method caller = this.getClass().getDeclaredMethod("caller55", new Class[]{ArrayList.class});
	   Method called = this.getClass().getDeclaredMethod("called54", new Class[]{ArrayList.class});
	   assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
										  hierarchy));
	   }

   public void test56() throws Exception
	   {
	   Method caller = this.getClass().getDeclaredMethod("caller56", new Class[]{ArrayList.class});
	   Method called = this.getClass().getDeclaredMethod("called54", new Class[]{ArrayList.class});
	   assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
										  hierarchy));
	   }*/

	public void test57() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller57", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called57", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test58() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller58", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called58", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	/*
	 public void test59() throws Exception
		 {
		 Method caller = this.getClass().getDeclaredMethod("caller59", new Class[]{ArrayList.class});
		 Method called = this.getClass().getDeclaredMethod("called58", new Class[]{List.class});
		 assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
											hierarchy));
		 }
 */
	public void test6() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller6", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called6", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test60() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller60", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called58", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	/*	public void test61() throws Exception
		 {
		 Method caller = this.getClass().getDeclaredMethod("caller61", new Class[]{ArrayList.class});
		 Method called = this.getClass().getDeclaredMethod("called58", new Class[]{List.class});
		 assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
											hierarchy));
		 }
 */
	public void test62() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller62", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called58", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test63() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller63", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called58", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	/*
	 public void test64() throws Exception
		 {
		 Method caller = this.getClass().getDeclaredMethod("caller64", new Class[]{List.class});
		 Method called = this.getClass().getDeclaredMethod("called58", new Class[]{List.class});
		 assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
											hierarchy));
		 }
 */
	public void test65() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller65", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called58", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	/*
	 public void test66() throws Exception
		 {
		 Method caller = this.getClass().getDeclaredMethod("caller66", new Class[]{List.class});
		 Method called = this.getClass().getDeclaredMethod("called58", new Class[]{List.class});
		 assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
											hierarchy));
		 }
 */
	public void test67() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller67", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called58", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test68() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller68", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test69() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller69", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test7() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller7", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called7", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test70() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller70", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test71() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller71", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test72() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller72", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test73() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller73", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test74() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller74", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test75() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller75", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test76() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller76", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test77() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller77", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test78() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller78", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test79() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller79", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test8() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller8", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called8", new Class[]{ArrayList.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test80() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller80", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test81() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller81", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test82() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller82", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test83() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller83", new Class[]{ArrayList.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test84() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller84", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test85() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller85", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                   hierarchy));
		}

	public void test86() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller86", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test87() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller87", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test88() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller88", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test89() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller89", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test9() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller9", new Class[]{ArrayList.class});
		Method called = ParameterizedTypeTest.class.getDeclaredMethod("called9", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test90() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller90", new Class[]{List.class});
		Method called = this.getClass().getDeclaredMethod("called68", new Class[]{List.class});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		}

	public void test91() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller91", new Class[]{
				List.class,
				ArrayList.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test92() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller92", new Class[]{
				List.class,
				ArrayList.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test93() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller93", new Class[]{
				List.class,
				List.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test94() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller94", new Class[]{
				List.class,
				List.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test95() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller95", new Class[]{
				ArrayList.class,
				ArrayList.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test96() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller96", new Class[]{
				ArrayList.class,
				ArrayList.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test97() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller97", new Class[]{
				ArrayList.class,
				ArrayList.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test98() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller98", new Class[]{
				ArrayList.class,
				ArrayList.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}

	public void test99() throws Exception
		{
		Method caller = this.getClass().getDeclaredMethod("caller99", new Class[]{
				List.class,
				ArrayList.class
		});
		Method called = this.getClass().getDeclaredMethod("called91", new Class[]{
				List.class,
				List.class
		});
		assertTrue(algorithm.isAssignable(called.getGenericParameterTypes()[0], caller.getGenericParameterTypes()[0],
		                                  hierarchy));
		assertFalse(algorithm.isAssignable(called.getGenericParameterTypes()[1], caller.getGenericParameterTypes()[1],
		                                   hierarchy));
		}
	}
