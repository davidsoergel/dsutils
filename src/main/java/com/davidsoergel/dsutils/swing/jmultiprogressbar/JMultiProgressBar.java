/*
 * Copyright (c) 2001-2013  David Soergel  <dev@davidsoergel.com>
 * Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.davidsoergel.dsutils.swing.jmultiprogressbar;

import com.davidsoergel.dsutils.increment.Incrementor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class JMultiProgressBar extends JPanel implements Incrementor.Listener
	{
	public JMultiProgressBar()
		{
		//setAlignmentX(0);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		Dimension oneBar = new JProgressBar().getPreferredSize();

		setPreferredSize(new Dimension(500, (int) oneBar.getHeight() * 6));
		}

	// Incrementables have a unique integer id; we use those to match incoming IncrementableEvents to their associated progress bars

	//java.util.List<SwingWorker> theWorkers = new LinkedList<SwingWorker>();
	@NotNull
	Map<Integer, JProgressBar> theBars = new HashMap<Integer, JProgressBar>();
	@NotNull
	Map<Integer, JLabel> theLabels = new HashMap<Integer, JLabel>();

	/*	private void update(SwingWorker sw, int value, String note)
		 {
		 JProgressBar bar = theBars.get(sw);
		 JLabel label = theLabels.get(sw);
		 if (bar == null)
			 {
			 label = new JLabel(note);
			 theLabels.put(sw, label);
			 add(label);

			 bar = new JProgressBar(0, 100);
			 //theWorkers.add(sw);
			 theBars.put(sw, bar);
			 add(bar);
			 }
		 bar.setValue(value);
		 label.setText(note);
		 }
 */
	private void remove(Integer id)
		{
		//theWorkers.remove(sw);

		JProgressBar bar = theBars.get(id);
		JLabel label = theLabels.get(id);
		if (bar != null)
			{
			remove(bar);
			}
		if (label != null)
			{
			remove(label);
			}
		}

	public void incrementableDone(@NotNull final Incrementor.IncrementorDoneEvent e)
		{
		SwingUtilities.invokeLater(new Runnable()
		{
		public void run()
			{
			Integer id = e.getId();
			remove(id);
			revalidate();
			repaint();
			}
		});
		}

	public void incrementableNoteUpdated(@NotNull final Incrementor.IncrementorNoteEvent e)
		{
		SwingUtilities.invokeLater(new Runnable()
		{
		public void run()
			{
			Integer id = e.getId();
			JLabel label = theLabels.get(id);
			if (label == null)
				{
				createBar(id);
				label = theLabels.get(id);
				}

			label.setText(e.getClientName() + " : " + e.getNote());
			//revalidate();
			//repaint();}
			}
		});
		}

	public void incrementableProgressUpdated(@NotNull final Incrementor.IncrementorProgressEvent e)
		{
		SwingUtilities.invokeLater(new Runnable()
		{
		public void run()
			{
			Integer id = e.getId();
			JProgressBar bar = theBars.get(id);
			if (bar == null)
				{
				createBar(id);
				bar = theBars.get(id);
				}

			int percent = e.getPercent();

			bar.setValue(percent);
			bar.setIndeterminate(percent == 0);
			}
		});
		}

	private void createBar(final Integer id)
		{
		@NotNull final JProgressBar bar;
		@NotNull JLabel label = new JLabel("");
		theLabels.put(id, label);
		add(label);

		bar = new JProgressBar(0, 100);
		//theWorkers.add(sw);
		theBars.put(id, bar);
		add(bar);
		revalidate();
		repaint();
		}

	public int compareTo(@NotNull final Object o)
		{
		return new Integer(hashCode()).compareTo(o.hashCode());
		}

	@Override
	public boolean equals(final Object o)
		{
		if (this == o)
			{
			return true;
			}
		if (o == null || getClass() != o.getClass())
			{
			return false;
			}

		final JMultiProgressBar that = (JMultiProgressBar) o;

		if (!theBars.equals(that.theBars))
			{
			return false;
			}
		if (!theLabels.equals(that.theLabels))
			{
			return false;
			}

		return true;
		}

	@Override
	public int hashCode()
		{
		int result = theBars.hashCode();
		result = 31 * result + theLabels.hashCode();
		return result;
		}
	}
