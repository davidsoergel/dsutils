package com.davidsoergel.dsutils.swing.jmultilist;


import com.davidsoergel.dsutils.stringmapper.StringMapper;
import com.davidsoergel.dsutils.stringmapper.TypedValueStringMapper;
import com.davidsoergel.dsutils.swing.WrapLayout;
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
public class JMultiList extends JPanel
	{
	private static final Logger logger = Logger.getLogger(JMultiList.class);
	protected MultiListModel model;


	public JMultiList() //throws NullPointerException, IllegalArgumentException
		{
		logger.warn("Init Size: " + getSize());
		logger.warn("Init Preferred Size: " + getPreferredSize());
		model = new DefaultMultiListModel();
		setAlignmentX(0);
		setLayout(new WrapLayout(FlowLayout.LEADING)); //new BoxLayout(this, BoxLayout.PAGE_AXIS));
		}

	public void setLists(Map<Object, Collection> lists)
		{
		logger.warn("Old Size: " + getSize());
		logger.warn("Old Preferred Size: " + getPreferredSize());
		model.setLists(lists);

		removeAll();
		for (Map.Entry<Object, Collection> entry : model.getLists().entrySet())
			{
			JNamedList listRow = new JNamedList(entry.getKey(), entry.getValue());
			//listRow.addChangeListener(model);
			listRow.setAlignmentX(0);
			add(listRow);
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

	public Map<Object, Collection> getSelections()
		{
		return model.getSelections();
		}

	public Map<Object, Collection> getLists()
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


		public JNamedList(final Object key, Collection list)
			{
			StringMapper mapper = TypedValueStringMapper.get(key.getClass());
			String s = mapper.renderAbbreviated(key);
			jlabel = new JLabel(s); //key.toString());
			jlabel.setToolTipText(mapper.render(key));

			jlist = new JList(list.toArray());
			jlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			jlist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			jlist.setVisibleRowCount(1); //list.size());
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
			//setLayout(new FlowLayout());
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
	}
