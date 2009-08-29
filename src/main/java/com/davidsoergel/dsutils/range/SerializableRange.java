package com.davidsoergel.dsutils.range;

import java.io.Serializable;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public interface SerializableRange<T extends Serializable> extends Range<T>, Serializable
	{
	}
