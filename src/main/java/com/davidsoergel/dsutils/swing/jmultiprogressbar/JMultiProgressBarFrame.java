package com.davidsoergel.dsutils.swing.jmultiprogressbar;

import javax.swing.*;
import java.awt.*;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class JMultiProgressBarFrame extends JFrame
	{
	JMultiProgressBar monitor;

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
