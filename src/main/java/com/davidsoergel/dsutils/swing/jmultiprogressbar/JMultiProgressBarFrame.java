package com.davidsoergel.dsutils.swing.jmultiprogressbar;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class JMultiProgressBarFrame extends JFrame
	{
	private static final Logger logger = Logger.getLogger(JMultiProgressBarFrame.class);

	private JMultiProgressBar monitor;

	private static JMultiProgressBarFrame _instance;

	/**
	 * Allow a subclass to register itself as the primary instance
	 *
	 * @param instance
	 */
	public static void setInstance(final JMultiProgressBarFrame instance)
		{
		JMultiProgressBarFrame._instance = instance;
		}

	public static JMultiProgressBarFrame getInstance()
		{
		if (_instance == null)
			{
			initInstanceOnEDT();
			}
		return _instance;
		}

	private static void initInstanceOnEDT()
		{
		if (SwingUtilities.isEventDispatchThread())
			{
			_instance = new JMultiProgressBarFrame();
			//monitor = _instance.getMonitor();
			}
		else
			{
			try
				{
				SwingUtilities.invokeAndWait(new Runnable()
				{
				public void run()
					{
					_instance = new JMultiProgressBarFrame();
					//monitor = _instance.getMonitor();
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

	public static JMultiProgressBar getInstanceMonitor()
		{
		return getInstance().getMonitor();
		}


	public JMultiProgressBarFrame() throws HeadlessException
		{
		setLocationByPlatform(true);

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		monitor = new JMultiProgressBar();

		JScrollPane scroller = new JScrollPane(monitor);

		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		add(scroller);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		//setPreferredSize(new Dimension(300, 200));
		//setMinimumSize(new Dimension(100, 200));
		pack();
		validate();
		setLocation(0, (int) dimension.getHeight() - getHeight() - 40);
		setTitle("Jandy Status");
		setVisible(true);
		}

	public JMultiProgressBar getMonitor()
		{
		return monitor;
		}
	}
