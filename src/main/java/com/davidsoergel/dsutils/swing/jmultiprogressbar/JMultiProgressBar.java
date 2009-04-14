package com.davidsoergel.dsutils.swing.jmultiprogressbar;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class JMultiProgressBar extends JPanel implements PropertyChangeListener
	{

	public JMultiProgressBar()
		{
		//setAlignmentX(0);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		Dimension oneBar = new JProgressBar().getPreferredSize();

		setPreferredSize(new Dimension(500, (int) oneBar.getHeight() * 6));
		}

	//java.util.List<SwingWorker> theWorkers = new LinkedList<SwingWorker>();
	Map<SwingWorker, JProgressBar> theBars = new HashMap<SwingWorker, JProgressBar>();
	Map<SwingWorker, JLabel> theLabels = new HashMap<SwingWorker, JLabel>();

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
	private void remove(SwingWorker sw)
		{
		//theWorkers.remove(sw);

		JProgressBar bar = theBars.get(sw);
		JLabel label = theLabels.get(sw);
		if (bar != null)
			{
			remove(bar);
			}
		if (label != null)
			{
			remove(label);
			}
		}


	public void propertyChange(final PropertyChangeEvent evt)
		{
		SwingUtilities.invokeLater(new Runnable()
		{
		public void run()
			{
			SwingWorker sw = (SwingWorker) evt.getSource();
			if (sw.isDone())
				{
				remove(sw);
				revalidate();
				repaint();
				}
			else
				{
				JProgressBar bar = theBars.get(sw);
				JLabel label = theLabels.get(sw);
				if (bar == null && !sw.isDone())
					{
					label = new JLabel("");
					theLabels.put(sw, label);
					add(label);

					bar = new JProgressBar(0, 100);
					//theWorkers.add(sw);
					theBars.put(sw, bar);
					add(bar);
					revalidate();
					repaint();
					}

				if ("progress" == evt.getPropertyName())
					{
					int value = (Integer) evt.getNewValue();
					bar.setValue(value);
					bar.setIndeterminate(value == 0);
					//revalidate(); // unnecessary
					}
				if ("note" == evt.getPropertyName())
					{
					String note = (String) evt.getNewValue();
					label.setText(note);
					revalidate();
					repaint();
					}
				}
			}
		});
		}
	}
