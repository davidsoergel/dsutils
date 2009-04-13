package com.davidsoergel.dsutils.swing.jmultiprogressbar;

import javax.swing.*;
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
		//setLayout(new WrapLayout(FlowLayout.LEADING)); //new BoxLayout(this, BoxLayout.PAGE_AXIS));
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
			JProgressBar bar = theBars.get(sw);
			JLabel label = theLabels.get(sw);
			if (bar == null)
				{
				label = new JLabel("");
				theLabels.put(sw, label);
				add(label);

				bar = new JProgressBar(0, 100);
				//theWorkers.add(sw);
				theBars.put(sw, bar);
				add(bar);
				}

			if ("progress" == evt.getPropertyName())
				{
				int value = (Integer) evt.getNewValue();
				bar.setValue(value);
				bar.setIndeterminate(value == 0);
				}
			if ("note" == evt.getPropertyName())
				{
				String note = (String) evt.getNewValue();
				label.setText(note);
				}
			if ("state" == evt.getPropertyName())
				{
				SwingWorker.StateValue state = (SwingWorker.StateValue) evt.getNewValue();
				if (state == SwingWorker.StateValue.DONE)
					{
					remove(sw);
					}
				}
			}
		});
		}
	}
