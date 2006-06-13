package com.davidsoergel.event;

import org.apache.log4j.Logger;

/**
 * @author lorax
 * @version 1.0
 */
public class EventObject extends java.util.EventObject
	{
// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(EventObject.class);

// --------------------------- CONSTRUCTORS ---------------------------

	public EventObject(Object o)
		{
		super(o);
		}

// -------------------------- OTHER METHODS --------------------------

	//public EventSource getSource()
	public Object getSource()
		{
		return (EventSource)super.getSource();
		}
	}
