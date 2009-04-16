package com.davidsoergel.dsutils.swing;

import com.davidsoergel.dsutils.Incrementable;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class ProgressMonitorSwingWorker<A, B> extends SwingWorker<A, B>
	{
	public class Incrementor implements Incrementable
		{

		int i = 0;
		private int total;

		public Incrementor(int total)
			{
			this.total = total;
			}

		public void increment()
			{
			i++;
			if (total != 0)
				{
				setProgress(100 * i / total);
				}
			else
				{
				setProgress(0);
				}
			;
			}

		public void setMaximum(int max)
			{
			total = max;
			}

		public void incrementMaximum(int more)
			{
			total += more;
			}

		String oldNote = "";

		public void setNote(String newNote)
			{
			firePropertyChange("note", oldNote, newNote);
			oldNote = newNote;
			}

		public void resetWithNote(String s)
			{
			i = 0;
			setNote(s);
			setProgress(0);
			}
		}

	ProgressMonitor pm;

	public void executeWithProgressMonitor(Component parent, String text, boolean immediate)
		{
		pm = new ProgressMonitor(parent, text, "", 0, 100);

		if (immediate)
			{
			pm.setMillisToPopup(0);
			pm.setMillisToDecideToPopup(0);
			}

		addPropertyChangeListener(new PropertyChangeListener()
		{
		public void propertyChange(PropertyChangeEvent evt)
			{
			if ("progress" == evt.getPropertyName())
				{
				int p = (Integer) evt.getNewValue();
				pm.setProgress(p);
				}
			if ("note" == evt.getPropertyName())
				{
				String n = (String) evt.getNewValue();
				pm.setNote(n);
				}
			}
		});
		execute();
		}

	@Override
	protected void done()
		{
		super.done();
		pm.setProgress(pm.getMaximum());
		pm.close();
		}
	}
