package com.davidsoergel.dsutils;

import com.davidsoergel.dsutils.collections.ConcurrentHashWeightedSet;
import com.davidsoergel.dsutils.collections.ImmutableHashWeightedSet;
import com.davidsoergel.dsutils.collections.MutableWeightedSet;
import com.davidsoergel.dsutils.collections.WeightedSet;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class LabellableImpl<T> implements Labellable<T>, Serializable
	{
	private static final Logger logger = Logger.getLogger(LabellableImpl.class);

	//** we're serializing for the sake of the FastaParser index, where labels shouldn't matter
	@Nullable
	protected transient MutableWeightedSet<T> mutableWeightedLabels = null; // = new ConcurrentHashWeightedSet<T>();
	@Nullable
	private transient WeightedSet<T> immutableWeightedLabels = null;

	// jump through some hoops here to avoid allocating the label sets if they're never used

	boolean isDone = false;

	public void doneLabelling()
		{
		if (isDone)
			{
			logger.debug("doneLabelling was already called");
			return;
			}
		if (mutableWeightedLabels != null)
			{
			immutableWeightedLabels = new ImmutableHashWeightedSet<T>(mutableWeightedLabels);
			}
		mutableWeightedLabels = null;
		isDone = true;
		}

	/**
	 * once the weighted labels have been queried, they can't be changed
	 *
	 * @return
	 */
	@NotNull
	public WeightedSet<T> getImmutableWeightedLabels()
		{
		if (!isDone)
			{
			throw new Error("need to call doneLabelling before getImmutableWeightedLabels");
			}
		if (immutableWeightedLabels == null)
			{
			immutableWeightedLabels = new ImmutableHashWeightedSet<T>(new ConcurrentHashWeightedSet<T>());
			}
		return immutableWeightedLabels;
		}

	/**
	 * once the weighted labels have been queried, thay can't be changed
	 *
	 * @return
	 */
	@NotNull
	public MutableWeightedSet<T> getMutableWeightedLabels()
		{
		if (isDone)
			{
			throw new Error("Can't call getMutableWeightedLabels after doneLabelling");
			}

		if (mutableWeightedLabels == null)
			{
			mutableWeightedLabels = new ConcurrentHashWeightedSet<T>();
			}

		return mutableWeightedLabels;
		}

	public int getItemCount()
		{
		if (immutableWeightedLabels != null)
			{
			return immutableWeightedLabels.getItemCount();
			}
		if (mutableWeightedLabels != null)
			{
			return mutableWeightedLabels.getItemCount();
			}
        // ** if we're not using labels at all, then each sample still gets a weight of 1.
		return 1;
		}
	}
