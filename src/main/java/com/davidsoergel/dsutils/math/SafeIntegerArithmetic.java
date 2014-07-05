/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */


package com.davidsoergel.dsutils.math;

import org.jetbrains.annotations.NotNull;

public class SafeIntegerArithmetic
	{
	// ------------------------------ FIELDS ------------------------------

	@NotNull
	public static final PrimTypeIndTy ByteTy = PrimTypeIndTy.ByteTy;
	@NotNull
	public static final PrimTypeIndTy ShortTy = PrimTypeIndTy.ShortTy;
	@NotNull
	public static final PrimTypeIndTy IntTy = PrimTypeIndTy.IntTy;
	@NotNull
	public static final PrimTypeIndTy LongTy = PrimTypeIndTy.LongTy;

	@NotNull
	public static final OperationKindTy AddOp = OperationKindTy.AddOp;
	@NotNull
	public static final OperationKindTy SubOp = OperationKindTy.SubOp;
	@NotNull
	public static final OperationKindTy NegOp = OperationKindTy.NegOp;
	@NotNull
	public static final OperationKindTy MulOp = OperationKindTy.MulOp;
	public static final OperationKindTy DivOp = OperationKindTy.DivOp;
	@NotNull
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

	public static final byte add(byte L, byte R)
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

	public static final short add(short L, short R)
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

	public static final int add(int L, int R)
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

	public static final long add(long L, long R)
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
	public static final byte sub(byte L, byte R)
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

	public static final short sub(short L, short R)
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

	public static final int sub(int L, int R)
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

	public static final long sub(long L, long R)
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
	public static final byte neg(byte L)
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

	public static final short neg(short L)
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

	public static final int neg(int L)
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

	public static final long neg(long L)
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
	public static final byte mul(byte L, byte R)
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

	public static final short mul(short L, short R)
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

	public static final int mul(int L, int R)
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

	public static final long mul(long L, long R)
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
	public static final byte div(byte L, byte R)
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

	public static final short div(short L, short R)
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

	public static final int div(int L, int R)
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

	public static final long div(long L, long R)
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
	public static byte exp(byte L, byte R)
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

	public static short exp(short L, short R)
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

	public static int exp(int L, int R)
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

	public static long exp(long L, long R)
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
			return (long) 1 / exp(L, -R);
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
		@NotNull
		protected static final PrimTypeIndTy ShortTy = new PrimTypeIndTy("ShortTy");
		@NotNull
		protected static final PrimTypeIndTy IntTy = new PrimTypeIndTy("IntTy");
		@NotNull
		protected static final PrimTypeIndTy LongTy = new PrimTypeIndTy("LongTy");

		public String getVal()
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
		@NotNull
		protected static final OperationKindTy AddOp = new OperationKindTy("AddOp");
		@NotNull
		protected static final OperationKindTy SubOp = new OperationKindTy("SubOp");
		@NotNull
		protected static final OperationKindTy NegOp = new OperationKindTy("NegOp");
		@NotNull
		protected static final OperationKindTy MulOp = new OperationKindTy("MulOp");
		protected static final OperationKindTy DivOp = new OperationKindTy("DivOp");
		protected static final OperationKindTy ExpOp = new OperationKindTy("ExpOp");

		public String getV()
			{
			return this.v;
			}

		private OperationKindTy(String Val)
			{
			this.v = Val;
			}

		private String v;
		}

	public static class IllegalArithArgsException extends IllegalArgumentException
		{
		public IllegalArithArgsException(PrimTypeIndTy type, OperationKindTy operation, long L, long R)
			{
			this.typeIndication = type;
			this.operationKind = operation;
			this.L = L;
			this.R = R;
			System.out.println(
					"illegal args: " + "op = " + operationKind.getV() + ", type = " + typeIndication.getVal() + ", L = "
					+ L + ", R = " + R);
			}

		public IllegalArithArgsException(PrimTypeIndTy type, OperationKindTy operation, long L)
			{
			this.typeIndication = type;
			this.operationKind = operation;
			this.L = L;
			System.out.println(
					"illegal args: " + "op = " + operationKind.getV() + ", type = " + typeIndication.getVal() + ", L = "
					+ L);
			}

		public PrimTypeIndTy getType()
			{
			return this.typeIndication;
			}

		public OperationKindTy getOperation()
			{
			return this.operationKind;
			}

		public long getLeftOp()
			{
			return this.L;
			}

		public long getRightOp()
			{
			return this.R;
			}

		protected PrimTypeIndTy typeIndication;
		protected OperationKindTy operationKind;
		protected long L;
		protected long R;
		}
	}
