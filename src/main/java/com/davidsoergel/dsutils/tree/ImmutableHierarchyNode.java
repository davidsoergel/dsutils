package com.davidsoergel.dsutils.tree;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class ImmutableHierarchyNode<T, I extends ImmutableHierarchyNode<T, I>> implements HierarchyNode<T, I>
	{
	/**
	 * {@inheritDoc}
	 */
	public HierarchyNode<? extends T, ? extends I> newChild()
		{
		throw new TreeRuntimeException("Can't create an immutable node without providing a value");
		}

	/**
	 * {@inheritDoc}
	 */
	public void setValue(T contents)
		{
		throw new TreeRuntimeException("Node is immutable");
		}

	/**
	 * Creates a new child node of the appropriate type
	 *
	 * @param value the value to assign to the node
	 * @return the new child node
	 */
	public abstract I newChild(final T value);
	}