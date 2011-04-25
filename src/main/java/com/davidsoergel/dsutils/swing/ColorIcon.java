package com.davidsoergel.dsutils.swing;


import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * http://blog.codebeach.com/2007/06/creating-dynamic-icons-in-java.html
 */

public class ColorIcon implements Icon
	{
	private static int HEIGHT = 14;
	private static int WIDTH = 14;

	private Color color;

	public ColorIcon(Color color)
		{
		this.color = color;
		}

	public int getIconHeight()
		{
		return HEIGHT;
		}

	public int getIconWidth()
		{
		return WIDTH;
		}

	public void paintIcon(Component c, @NotNull Graphics g, int x, int y)
		{
		g.setColor(color);
		g.fillRect(x, y, WIDTH - 1, HEIGHT - 1);

		g.setColor(Color.black);
		g.drawRect(x, y, WIDTH - 1, HEIGHT - 1);
		}
	}
