package com.davidsoergel.dsutils;

/**
 * Created by IntelliJ IDEA.
 * User: soergel
 * Date: Dec 7, 2006
 * Time: 4:46:30 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Interval<T extends Number> extends Comparable<Interval<T>>//extends HierarchyNode
	{
	public T getLeft();

	public T getRight();

	// this is a little questionable because Numbers are not necessarily Comparable, but let's just assume they are.
	boolean encompassesValue(T value);
	}
