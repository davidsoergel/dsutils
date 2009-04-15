package com.davidsoergel.dsutils.swing.jmultilist;


import com.davidsoergel.dsutils.stringmapper.StringMapper;
import com.davidsoergel.dsutils.stringmapper.TypedValueStringMapper;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Originally based on JFlexiSlider by Kirill Grouchnikov; see http://today.java.net/pub/a/today/2007/02/22/how-to-write-custom-swing-component.html
 *
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class JMultiList<T> extends JPanel implements Scrollable
	{
	private static final Logger logger = Logger.getLogger(JMultiList.class);
	protected MultiListModel<T> model;

	int maxRows = 10;


	public JMultiList() //throws NullPointerException, IllegalArgumentException
		{
		logger.warn("Init Size: " + getSize());
		logger.warn("Init Preferred Size: " + getPreferredSize());
		model = new DefaultMultiListModel();

		setAlignmentX(0);
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));  //(new WrapLayout(FlowLayout.LEADING)); //
		}

	public void setLists(Map<T, Collection> lists)
		{
		logger.warn("Old Size: " + getSize());
		logger.warn("Old Preferred Size: " + getPreferredSize());
		model.setLists(lists);

		removeAll();
		int width = 0;
		for (Map.Entry<T, Collection> entry : model.getLists().entrySet())
			{
			JNamedList listRow = new JNamedList(entry.getKey(), entry.getValue());
			//listRow.addChangeListener(model);
			listRow.setAlignmentX(0);
			add(listRow);

			width += listRow.getPreferredSize().getWidth();
			}

		if (lists.isEmpty())
			{
			setPreferredSize(new Dimension(0, 0));
			}
		else
			{
			int height = (int) getPreferredSize().getHeight();

			setPreferredSize(new Dimension(width, height));
			}
		logger.warn("Mid Size: " + getSize());
		logger.warn("Mid Preferred Size: " + getPreferredSize());

		revalidate();
		logger.warn("New Preferred Size: " + getPreferredSize());
		logger.warn("New Size: " + getSize());

		repaint();
		}

	public MultiListModel getModel()
		{
		return model;
		}

	public Map<T, Collection> getSelections()
		{
		return model.getSelections();
		}

	public Map<T, Collection> getLists()
		{
		return model.getLists();
		}

	public void addChangeListener(ChangeListener changeListener)
		{
		model.addChangeListener(changeListener);
		}


	private ListCellRenderer renderer = new MyListCellRenderer();


	private class JNamedList extends JPanel //JComponent
		{
		JLabel jlabel;
		JList jlist;


		public JNamedList(final T key, Collection list)
			{
			StringMapper mapper = TypedValueStringMapper.get(key.getClass());
			String s = mapper.renderAbbreviated(key);
			jlabel = new JLabel(s); //key.toString());
			jlabel.setToolTipText(mapper.render(key));

			jlist = new JList(list.toArray());
			jlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			jlist.setLayoutOrientation(JList.HORIZONTAL_WRAP);

			int rows = Math.max(maxRows, list.size());
			int cols = list.size() / rows + 1;
			rows = list.size() / cols;

			jlist.setVisibleRowCount(rows);  //(int) Math.sqrt(list.size()))
			jlist.setCellRenderer(renderer);

			jlist.addListSelectionListener(new ListSelectionListener()
			{
			public void valueChanged(ListSelectionEvent listSelectionEvent)
				{
				model.updateSelections(key, Arrays.asList(jlist.getSelectedValues()));
				}
			});

			/*	Dimension listSize = jlist.getPreferredSize();
			Dimension labelSize = jlabel.getPreferredSize();
			Dimension preferredSize = new Dimension((int)(listSize.getHeight()),(int)(listSize.getWidth()+labelSize.getWidth()));

			setPreferredSize(preferredSize);
			*/
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			add(jlabel);
			add(jlist);
			}
		}
/*
	@Override
	public Dimension getPreferredSize()
		{
		return super.getPreferredSize();
		}

	@Override
	public Dimension getMaximumSize()
		{
		return super.getMaximumSize();
		}

	@Override
	public Dimension getMinimumSize()
		{
		return super.getMinimumSize();
		}
		*/

	private class MyListCellRenderer extends JLabel implements ListCellRenderer
		{
		Border marginBorder;

		public MyListCellRenderer()
			{

			Border lineBorder = BorderFactory.createLineBorder(Color.black);
			Border margin = new EmptyBorder(3, 3, 3, 3);
			marginBorder = new CompoundBorder(lineBorder, margin);
			}

		public Component getListCellRendererComponent(JList list,              // the list
		                                              Object value,            // value to display
		                                              int index,               // cell index
		                                              boolean isSelected,      // is the cell selected
		                                              boolean cellHasFocus)    // does the cell have focus
			{
			StringMapper mapper = TypedValueStringMapper.get(value.getClass());
			String s = mapper.renderAbbreviated(value);

			//** hack workaround...
			if (s.contains("/"))
				{
				s = s.substring(s.lastIndexOf("/") + 1);
				}
			else if (s.contains("."))
				{
				s = s.substring(s.lastIndexOf(".") + 1);
				}
			//= value.toString();
			setText(s);
			setToolTipText(mapper.render(value));
			setBorder(marginBorder);
			if (isSelected)
				{
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
				}
			else
				{
				setBackground(list.getBackground());
				setForeground(list.getForeground());
				}
			setEnabled(list.isEnabled());
			setFont(list.getFont());
			setOpaque(true);
			return this;
			}
		}

	public Dimension getPreferredScrollableViewportSize()
		{
		return getPreferredSize();
		}

	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction)
		{
		return 1;
		}

	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction)
		{
		return 10;
		}

	public boolean getScrollableTracksViewportWidth()
		{
		return false;
		}

	public boolean getScrollableTracksViewportHeight()
		{
		return true;
		}
	}
