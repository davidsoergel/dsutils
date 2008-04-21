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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author <a href="flavia.rainone@jboss.com">Flavia Rainone</a>
 */
public class ParameterizedTypeTest extends VariableTargetAlgorithmTest
	{
	// -------------------------- OTHER METHODS --------------------------

	protected <A> void called1(ArrayList<A> arg)
		{
		}

	protected void called10(List<?> arg)
		{
		}

	protected void called11(List arg)
		{
		}

	protected void called12(List<String> arg)
		{
		}

	protected void called13(List<Object> arg)
		{
		}

	protected void called14(List<Integer> arg)
		{
		}

	protected void called15(List<? extends String> arg)
		{
		}

	protected void called16(List<? extends Integer> arg)
		{
		}

	protected void called17(HashMap<Integer, String> a)
		{
		}

	protected void called2(ArrayList<?> arg)
		{
		}

	protected void called3(ArrayList arg)
		{
		}

	protected void called4(ArrayList<String> arg)
		{
		}

	protected void called5(ArrayList<Object> arg)
		{
		}

	protected void called6(ArrayList<Integer> arg)
		{
		}

	protected void called7(ArrayList<? extends String> arg)
		{
		}

	protected void called8(ArrayList<? extends Integer> arg)
		{
		}

	protected <A> void called9(List<A> arg)
		{
		}
	}

class MyClass1 extends MyClass2
	{
	}

class MyClass2<A extends Runnable> extends MyClass3<A>
	{
	}

class MyClass3<A> extends ArrayList<A>
	{
	}

class NewClass2<A> extends NewClass1
	{
	}

class NewClass3<A> extends MyClass2
	{
	}

class NewClass1<A> extends NewClass
	{
	}

class NewClass extends MyClass2<Runnable>
	{
	}

class MyClass_<A extends Serializable, B> extends MyClass2_<A>
	{
	}

class MyClass2_<A extends Serializable> extends HashMap<String, A>
	{
	}

class MyClass3_<A extends Serializable, B> extends MyClass2_
	{
	}