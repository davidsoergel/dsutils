package com.davidsoergel.dsutils.swing;

import com.davidsoergel.dsutils.Incrementable;
import com.davidsoergel.dsutils.swing.jmultiprogressbar.JMultiProgressBar;
import com.davidsoergel.dsutils.swing.jmultiprogressbar.JMultiProgressBarFrame;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class MonitoredSwingWorker<A, B> extends SwingWorker<A, B>
	{
	private static final Logger logger = Logger.getLogger(MonitoredSwingWorker.class);

	//protected JMultiProgressBar monitor;
	private static JMultiProgressBarFrame monitorFrame;
	public static JMultiProgressBar monitor;

	String initialNote;

	protected MonitoredSwingWorker(final String initialNote)
		{
		this.initialNote = initialNote;
		}

	public static void init()
		{
		if (SwingUtilities.isEventDispatchThread())
			{
			monitorFrame = new JMultiProgressBarFrame();
			monitor = monitorFrame.getMonitor();
			}
		else
			{
			try
				{
				SwingUtilities.invokeAndWait(new Runnable()
				{
				public void run()
					{
					monitorFrame = new JMultiProgressBarFrame();
					monitor = monitorFrame.getMonitor();
					}
				});
				}
			catch (InterruptedException e)
				{
				logger.error("Error", e);
				throw new Error(e);
				}
			catch (InvocationTargetException e)
				{
				logger.error("Error", e);
				throw new Error(e);
				}
			}
		}

	protected final Incrementor incrementor = new Incrementor();

	public static void setVisible(boolean visible)
		{
		monitorFrame.setVisible(visible);
		}

	public class Incrementor implements Incrementable
		{
		int i = 0;
		private int total = 0;

		/*	public Incrementor(int total)
			  {
			  this.total = total;
			  }
  */
		public void increment()
			{
			i++;
			if (total != 0)
				{
				int p = 100 * i / total;
				if (p > 100)
					{
					// avoid throwing an exception that breaks the process
					logger.warn("progress counter exceeds maximum");
					p = 100;
					}
				setProgress(p);
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
			setProgress(0);
			setNote(s);
			}

		public int getCount()
			{
			return i;
			}
		}

	public void executeWithProgressMonitor()
		{
		//monitor.update(this, 0, text);
		addPropertyChangeListener(monitor);
		setProgress(0);
		incrementor.setNote(initialNote);
		execute();
		}
	}
