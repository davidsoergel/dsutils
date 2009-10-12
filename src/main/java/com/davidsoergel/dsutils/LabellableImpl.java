package com.davidsoergel.dsutils;

import com.davidsoergel.dsutils.collections.ConcurrentHashWeightedSet;
import com.davidsoergel.dsutils.collections.ImmutableHashWeightedSet;
import com.davidsoergel.dsutils.collections.MutableWeightedSet;
import com.davidsoergel.dsutils.collections.WeightedSet;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class LabellableImpl<T> implements Labellable<T> //, Serializable
	{
	private static final Logger logger = Logger.getLogger(LabellableImpl.class);

	//** we're serializing for the sake of the FastaParser index, where labels shouldn't matter
	protected transient MutableWeightedSet<T> mutableWeightedLabels = new ConcurrentHashWeightedSet<T>();
	private transient WeightedSet<T> immutableWeightedLabels;

	public void doneLabelling()
		{
		if (mutableWeightedLabels == null)
			{
			logger.debug("doneLabelling was already called");
			return;
			}
		immutableWeightedLabels = new ImmutableHashWeightedSet<T>(mutableWeightedLabels);
		mutableWeightedLabels = null;
		}

	/**
	 * once the weighted labels have been queried, they can't be changed
	 *
	 * @return
	 */
	@NotNull
	public WeightedSet<T> getImmutableWeightedLabels()
		{
		if (immutableWeightedLabels == null)
			{
			throw new Error("need to call doneLabelling before getImmutableWeightedLabels");
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
		if (mutableWeightedLabels == null)
			{
			throw new Error("Can't call getMutableWeightedLabels after doneLabelling");
			}

		return mutableWeightedLabels;
		}

	public int getItemCount()
		{
		if (immutableWeightedLabels != null)
			{
			return immutableWeightedLabels.getItemCount();
			}
		return mutableWeightedLabels.getItemCount();
		}
	}
