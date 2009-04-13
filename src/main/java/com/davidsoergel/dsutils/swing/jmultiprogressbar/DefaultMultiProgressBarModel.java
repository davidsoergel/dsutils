package com.davidsoergel.dsutils.swing.jmultiprogressbar;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class DefaultMultiProgressBarModel implements MultiProgressBarModel
	{
	List<SwingWorker> theWorkers = new LinkedList<SwingWorker>();

	public void add(SwingWorker sw)
		{
		theWorkers.add(sw);
		}

	public void remove(SwingWorker sw)
		{
		theWorkers.remove(sw);
		}

	public List<SwingWorker> getWorkers()
		{
		return theWorkers;
		}
	}
