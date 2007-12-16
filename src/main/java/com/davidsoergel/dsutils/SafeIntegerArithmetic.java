/*
 * Copyright (c) 2001-2007 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
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

/* $Id$ */

package com.davidsoergel.dsutils;

/**
 * Title              <p> Safe Integer Arithmetic Description        <p> Copyright          Copyright (c) <p> J F H
 * Winkler, 2000, 2001 Company            <p> FSU, Jena, Germany
 * <p/>
 * Copied from Winkler, Jürgen F. H. A Safe Variant of the Unsafe Integer Arithmetic of Java. Software-Practice and
 * Experience, 32 (2002) 669..701. DOI:  10.1002/spe.454 http://psc.informatik.uni-jena.de/Themen/pap-talk/java-arith.pdf
 *
 * @author J F H Winkler
 * @version 1.0
 * @date 2001.Mar.18
 */
public class SafeIntegerArithmetic
	{
	// ------------------------------ FIELDS ------------------------------

	public static final PrimTypeIndTy ByteTy = PrimTypeIndTy.ByteTy;
	public static final PrimTypeIndTy ShortTy = PrimTypeIndTy.ShortTy;
	public static final PrimTypeIndTy IntTy = PrimTypeIndTy.IntTy;
	public static final PrimTypeIndTy LongTy = PrimTypeIndTy.LongTy;

	public static final OperationKindTy AddOp = OperationKindTy.AddOp;
	public static final OperationKindTy SubOp = OperationKindTy.SubOp;
	public static final OperationKindTy NegOp = OperationKindTy.NegOp;
	public static final OperationKindTy MulOp = OperationKindTy.MulOp;
	public static final OperationKindTy DivOp = OperationKindTy.DivOp;
	public static final OperationKindTy ExpOp = OperationKindTy.ExpOp;

	public static final byte MaxB = java.lang.Byte.MAX_VALUE;
	public static final byte MinB = java.lang.Byte.MIN_VALUE;
	public static final short MaxS = java.lang.Short.MAX_VALUE;
	public static final short MinS = java.lang.Short.MIN_VALUE;
	public static final int MaxI = java.lang.Integer.MAX_VALUE;
	public static final int MinI = java.lang.Integer.MIN_VALUE;
	public static final long MaxL = java.lang.Long.MAX_VALUE;
	public static final long MinL = java.lang.Long.MIN_VALUE;


	// -------------------------- STATIC METHODS --------------------------

	// ------------------------------- ADDITION ------------------------------

	public static final byte Add(byte L, byte R)
		{
		short result = (short) (L + R);
		if (result < MinB || result > MaxB)
			{
			throw new IllegalArithArgsException(ByteTy, AddOp, L, R);
			}
		else
			{
			return (byte) result;
			}
		}

	public static final short Add(short L, short R)
		{
		int result = (int) (L + R);
		if (result < MinS || result > MaxS)
			{
			throw new IllegalArithArgsException(ShortTy, AddOp, L, R);
			}
		else
			{
			return (short) result;
			}
		}

	public static final int Add(int L, int R)
		{
		long result = (long) L + (long) R;
		if (result < MinI || result > MaxI)
			{
			throw new IllegalArithArgsException(IntTy, AddOp, L, R);
			}
		else
			{
			return (int) result;
			}
		}

	public static final long Add(long L, long R)
		{
		long result = L + R;
		if (L > 0 && R > 0 && result < 0 || L < 0 && R < 0 && result >= 0)
			{
			throw new IllegalArithArgsException(LongTy, AddOp, L, R);
			}
		else
			{
			return result;
			}
		}

	// ------------------------------- SUBTRACTION ------------------------
	public static final byte Sub(byte L, byte R)
		{
		short result = (short) (L - R);
		if (result < MinB || result > MaxB)
			{
			throw new IllegalArithArgsException(ByteTy, AddOp, L, R);
			}
		else
			{
			return (byte) result;
			}
		}

	public static final short Sub(short L, short R)
		{
		int result = (int) (L - R);
		if (result < MinS || result > MaxS)
			{
			throw new IllegalArithArgsException(ShortTy, AddOp, L, R);
			}
		else
			{
			return (short) result;
			}
		}

	public static final int Sub(int L, int R)
		{
		long result = (long) L - (long) R;
		if (result < MinI || result > MaxI)
			{
			throw new IllegalArithArgsException(IntTy, AddOp, L, R);
			}
		else
			{
			return (int) result;
			}
		}

	public static final long Sub(long L, long R)
		{
		long result = L - R;
		if (L >= 0 && R < 0 && result < 0 || L < 0 && R > 0 && result > 0)
			{
			throw new IllegalArithArgsException(LongTy, SubOp, L, R);
			}
		else
			{
			return result;
			}
		}

	// ------------------------------- NEGATION --------------------------
	public static final byte Neg(byte L)
		{
		if (L == MinB)
			{
			throw new IllegalArithArgsException(ByteTy, NegOp, L);
			}
		else
			{
			return (byte) (-L);
			}
		}

	public static final short Neg(short L)
		{
		if (L == MinS)
			{
			throw new IllegalArithArgsException(ShortTy, NegOp, L);
			}
		else
			{
			return (short) (-L);
			}
		}

	public static final int Neg(int L)
		{
		if (L == MinI)
			{
			throw new IllegalArithArgsException(IntTy, NegOp, L);
			}
		else
			{
			return (int) (-L);
			}
		}

	public static final long Neg(long L)
		{
		if (L == MinL)
			{
			throw new IllegalArithArgsException(LongTy, NegOp, L);
			}
		else
			{
			return (long) (-L);
			}
		}

	// ------------------------------- MULTIPLICATION -----------------------
	public static final byte Mul(byte L, byte R)
		{
		short result = (short) (L * R);
		if (result < MinB || result > MaxB)
			{
			throw new IllegalArithArgsException(ByteTy, AddOp, L, R);
			}
		else
			{
			return (byte) result;
			}
		}

	public static final short Mul(short L, short R)
		{
		int result = (int) (L * R);
		if (result < MinS || result > MaxS)
			{
			throw new IllegalArithArgsException(ShortTy, AddOp, L, R);
			}
		else
			{
			return (short) result;
			}
		}

	public static final int Mul(int L, int R)
		{
		long result = (long) L * (long) R;
		if (result < MinI || result > MaxI)
			{
			throw new IllegalArithArgsException(IntTy, AddOp, L, R);
			}
		else
			{
			return (int) result;
			}
		}

	public static final long Mul(long L, long R)
		{
		if ((L > 0 && (R < MinL / L || R > MaxL / L)) || (L == -1 && R == MinL) || (L < -1 && (R < MaxL / L
				|| R > MinL / L)))
			{
			throw new IllegalArithArgsException(LongTy, MulOp, L, R);
			}
		else
			{
			return L * R;
			}
		}

	// ------------------------------- DIVISION -------------------------
	public static final byte Div(byte L, byte R)
		{
		if (L == MinB && R == -1)
			{
			throw new IllegalArithArgsException(ByteTy, DivOp, L, R);
			}
		else
			{
			return (byte) (L / R);
			}
		}

	public static final short Div(short L, short R)
		{
		if (L == MinS && R == -1)
			{
			throw new IllegalArithArgsException(ShortTy, DivOp, L, R);
			}
		else
			{
			return (short) (L / R);
			}
		}

	public static final int Div(int L, int R)
		{
		if (L == MinI && R == -1)
			{
			throw new IllegalArithArgsException(IntTy, DivOp, L, R);
			}
		else
			{
			return (int) (L / R);
			}
		}

	public static final long Div(long L, long R)
		{
		if (L == MinL && R == -1)
			{
			throw new IllegalArithArgsException(LongTy, DivOp, L, R);
			}
		else
			{
			return L / R;
			}
		}

	// ------------------------------- EXPONENTIATION ----------------------
	public static byte Exp(byte L, byte R)
		{
		if (L == 0 && R < 0)
			{
			throw new IllegalArithArgsException(ByteTy, ExpOp, L, R);
			}
		if ((L >= 2 || L <= -2) && R > 0)
			{
			short ExpVal = 1;
			for (byte i = 1; i <= R; i++)
				{
				ExpVal = (short) (ExpVal * L);
				if (ExpVal > (short) MaxB || ExpVal < (short) MinB)
					{
					throw new IllegalArithArgsException(ByteTy, ExpOp, L, R);
					}
				}
			return (byte) ExpVal;
			}
		return (byte) StrictMath.pow((double) L, (double) R);
		}

	public static short Exp(short L, short R)
		{
		if (L == 0 && R < 0)
			{
			throw new IllegalArithArgsException(ShortTy, ExpOp, L, R);
			}
		if ((L >= 2 || L <= -2) && R > 0)
			{
			int ExpVal = 1;
			for (byte i = 1; i <= R; i++)
				{
				ExpVal = ExpVal * L;
				if (ExpVal > (int) MaxS || ExpVal < (int) MinS)
					{
					throw new IllegalArithArgsException(ShortTy, ExpOp, L, R);
					}
				}
			return (short) ExpVal;
			}
		return (short) StrictMath.pow((double) L, (double) R);
		}

	public static int Exp(int L, int R)
		{
		if (L == 0 && R < 0)
			{
			throw new IllegalArithArgsException(IntTy, ExpOp, L, R);
			}
		if ((L >= 2 || L <= -2) && R > 0)
			{
			long ExpVal = 1;
			for (byte i = 1; i <= R; i++)
				{
				ExpVal = ExpVal * L;
				if (ExpVal > (long) MaxI || ExpVal < (long) MinI)
					{
					throw new IllegalArithArgsException(IntTy, ExpOp, L, R);
					}
				}
			return (int) ExpVal;
			}
		return (int) StrictMath.pow((double) L, (double) R);
		}

	public static long Exp(long L, long R)
		{
		if (L == 0 && R < 0)
			{
			throw new IllegalArithArgsException(LongTy, ExpOp, L, R);
			}
		if ((L >= 2 || L <= -2) && R > 0)
			{
			double ExpVal = 1.0;
			double DL = (double) L;
			double DMaxL = (double) MaxL;// DMaxL = MaxL+1
			double DMinL = (double) MinL;// DMinL = MinL
			for (byte i = 1; i <= R; i++)
				{
				ExpVal = ExpVal * DL;
				if (ExpVal >= DMaxL || ExpVal < DMinL)
					{
					throw new IllegalArithArgsException(LongTy, ExpOp, L, R);
					}
				}
			}// end if
		if (R == 0)
			{
			return 1;
			}
		if (R == 1)
			{
			return L;
			}
		if (L == 0 && R > 0)
			{
			return 0;
			}
		if (L == 1)
			{
			return 1;
			}
		if (L == -1)
			{
			return ((R % 2 == 0) ? 1 : -1);
			}
		if (R < 0)
			{
			return (long) 1 / Exp(L, -R);
			}
		{
		long res = 1;
		for (byte i = 1; i <= R; i++)
			{
			res = res * L;
			}
		return res;
		}
		}

	// -------------------------- INNER CLASSES --------------------------

	// sia = safe integer arithmetic

	public final static class PrimTypeIndTy
		{
		protected static final PrimTypeIndTy ByteTy = new PrimTypeIndTy("ByteTy");
		protected static final PrimTypeIndTy ShortTy = new PrimTypeIndTy("ShortTy");
		protected static final PrimTypeIndTy IntTy = new PrimTypeIndTy("IntTy");
		protected static final PrimTypeIndTy LongTy = new PrimTypeIndTy("LongTy");

		public String GetVal()
			{
			return this.Val;
			}

		private PrimTypeIndTy(String Val)
			{
			this.Val = Val;
			}

		private String Val;
		}

	public final static class OperationKindTy
		{
		protected static final OperationKindTy AddOp = new OperationKindTy("AddOp");
		protected static final OperationKindTy SubOp = new OperationKindTy("SubOp");
		protected static final OperationKindTy NegOp = new OperationKindTy("NegOp");
		protected static final OperationKindTy MulOp = new OperationKindTy("MulOp");
		protected static final OperationKindTy DivOp = new OperationKindTy("DivOp");
		protected static final OperationKindTy ExpOp = new OperationKindTy("ExpOp");

		public String GetVal()
			{
			return this.Val;
			}

		private OperationKindTy(String Val)
			{
			this.Val = Val;
			}

		private String Val;
		}

	public static class IllegalArithArgsException extends IllegalArgumentException
		{
		public IllegalArithArgsException(PrimTypeIndTy type, OperationKindTy operation, long L, long R)
			{
			this.TypeIndication = type;
			this.OperationKind = operation;
			this.L = L;
			this.R = R;
			System.out.println("illegal args: " + "op = " + OperationKind.GetVal() + ", type = "
					+ TypeIndication.GetVal() + ", L = " + L + ", R = " + R);
			}

		public IllegalArithArgsException(PrimTypeIndTy type, OperationKindTy operation, long L)
			{
			this.TypeIndication = type;
			this.OperationKind = operation;
			this.L = L;
			System.out.println("illegal args: " + "op = " + OperationKind.GetVal() + ", type = "
					+ TypeIndication.GetVal() + ", L = " + L);
			}

		public PrimTypeIndTy GetType()
			{
			return this.TypeIndication;
			}

		public OperationKindTy GetOperation()
			{
			return this.OperationKind;
			}

		public long GetLeftOp()
			{
			return this.L;
			}

		public long GetRightOp()
			{
			return this.R;
			}

		protected PrimTypeIndTy TypeIndication;
		protected OperationKindTy OperationKind;
		protected long L;
		protected long R;
		}
	}
