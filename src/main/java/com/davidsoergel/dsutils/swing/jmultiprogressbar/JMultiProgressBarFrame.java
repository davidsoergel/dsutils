package com.davidsoergel.dsutils.swing.jmultiprogressbar;

import javax.swing.*;
import java.awt.*;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class JMultiProgressBarFrame extends JFrame
	{
	JMultiProgressBar monitor = new JMultiProgressBar();

	public JMultiProgressBarFrame() throws HeadlessException
		{
		ScrollPane scroller = new ScrollPane();
		scroller.add(monitor);
		add(scroller);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		pack();
		//setSize(1600, 1000);
		setTitle("Jandy Status");
		setVisible(true);
		}

	public JMultiProgressBar getMonitor()
		{
		return monitor;
		}
	}
