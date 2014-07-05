/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.swing;

import com.davidsoergel.dsutils.increment.ConcurrentIncrementor;
import com.davidsoergel.dsutils.increment.Incrementor;
import com.davidsoergel.dsutils.swing.jmultiprogressbar.JMultiProgressBarFrame;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class MonitoredSwingWorker<A, B> extends SwingWorker<A, B>
	{
	private static final Logger logger = Logger.getLogger(MonitoredSwingWorker.class);

	//protected JMultiProgressBar monitor;

	String initialNote;

	protected MonitoredSwingWorker(final String initialNote)
		{
		this.initialNote = initialNote;
		}

	@NotNull
	protected final Incrementor incrementor = new ConcurrentIncrementor("localhost", null);

	public void executeWithProgressMonitor()
		{
		//monitor.update(this, 0, text);
		incrementor.addListener(JMultiProgressBarFrame.getInstanceMonitor());
//		addPropertyChangeListener(monitor);
//		setProgress(0);
		incrementor.setNote(initialNote);
		execute();
		}

	@Override
	protected void done()
		{
		super.done();
		incrementor.done();
		}
	}
