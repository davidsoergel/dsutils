package com.davidsoergel.dsutils.increment;

import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class Incrementor
	{
	private final String clientName;

	private static final Logger logger = Logger.getLogger(Incrementor.class);

	public abstract void increment();

	public abstract void done();

	public abstract void setMaximum(int i);

	public abstract void setNote(String n);

	public abstract void incrementMaximum(int length);

	public abstract void resetWithNote(String s);

	public abstract int getCount();

	public abstract int getMax();

	public int getPercent()
		{
		int m = getMax();
		if (m != 0)
			{
			int p = 100 * getCount() / m;
			if (p > 100)
				{
				// avoid throwing an exception that breaks the process
				logger.warn("progress counter exceeds maximum");
				p = 100;
				}
			return p;
			}
		else
			{
			return 0;
			}
		}

	/*	public boolean isDone()
		 {
		 return getCount() >= getMax();
		 }

 */
	@NotNull
	private Set<Listener> listeners = new ConcurrentSkipListSet<Listener>();

	public void fireIncrementableUpdatedEvent(Integer changedI, Integer changedMax)
		{
		if (changedI >= changedMax)
			{
			synchronized (this)
				{
				notifyAll();
				}
			@NotNull IncrementorDoneEvent ev = new IncrementorDoneEvent(id);

			for (@NotNull Listener listener : listeners)
				{
				listener.incrementableDone(ev);
				}
			}
		else
			{
			@NotNull IncrementorProgressEvent ev = new IncrementorProgressEvent(id, changedI, changedMax);

			for (@NotNull Listener listener : listeners)
				{
				listener.incrementableProgressUpdated(ev);
				}
			}
		}


	public void fireIncrementableNoteEvent(String note)
		{
		@NotNull IncrementorNoteEvent ev = new IncrementorNoteEvent(clientName, id, note);

		for (@NotNull Listener listener : listeners)
			{
			listener.incrementableNoteUpdated(ev);
			}
		}


	public void addListener(Listener l)
		{
		listeners.add(l);
		}

	public void removeListener(Listener l)
		{
		listeners.remove(l);
		}

	private final int id;

	public int getId()
		{
		return id;
		}

	@NotNull
	private static AtomicInteger idGenerator = new AtomicInteger(0);

	public Incrementor(final String clientName)
		{
		this.id = idGenerator.incrementAndGet();
		this.clientName = clientName;
		}

	public static final class IncrementorEventBatch implements Serializable
		{
		private final Set<IncrementorDoneEvent> doneEvents;
		private final Set<IncrementorNoteEvent> noteEvents;
		private final Set<IncrementorProgressEvent> progressEvents;

		public IncrementorEventBatch(final Set<IncrementorDoneEvent> doneEvents,
		                             final Set<IncrementorNoteEvent> noteEvents,
		                             final Set<IncrementorProgressEvent> progressEvents)
			{
			this.doneEvents = doneEvents;
			this.noteEvents = noteEvents;
			this.progressEvents = progressEvents;
			}

		public boolean isEmpty()
			{
			return progressEvents.isEmpty() && doneEvents.isEmpty() && noteEvents.isEmpty();
			}

		public Set<IncrementorDoneEvent> getDoneEvents()
			{
			return doneEvents;
			}

		public Set<IncrementorNoteEvent> getNoteEvents()
			{
			return noteEvents;
			}

		public Set<IncrementorProgressEvent> getProgressEvents()
			{
			return progressEvents;
			}
		}

	public static abstract class IncrementorEvent implements Serializable
		{
		private final int id;

		public int getId()
			{
			return id;
			}

		protected IncrementorEvent(final int id)
			{
			this.id = id;
			}
		}

	public static final class IncrementorDoneEvent extends IncrementorEvent
		{
		public IncrementorDoneEvent(final int id)
			{
			super(id);
			}
		}

	public static final class IncrementorNoteEvent extends IncrementorEvent
		{
		private final String note;
		private final String clientName;

		public IncrementorNoteEvent(final String clientName, final int id, final String changedNote)
			{
			super(id);
			this.note = changedNote;
			this.clientName = clientName;
			}

		public String getNote()
			{
			return note;
			}

		public String getClientName()
			{
			return clientName;
			}
		}


	public static final class IncrementorProgressEvent extends IncrementorEvent
		{
		private final Integer i;
		private final Integer max;

		public IncrementorProgressEvent(final int id, final int changedI, final int changedMax)
			{
			super(id);
			this.i = changedI;
			this.max = changedMax;
			}


		public Integer getCount()
			{
			return i;
			}


		public Integer getMax()
			{
			return max;
			}

		public int getPercent()
			{
			if (max != 0)
				{
				int p = 100 * i / max;
				if (p > 100)
					{
					// avoid throwing an exception that breaks the process
					logger.warn("progress counter exceeds maximum");
					p = 100;
					}
				return p;
				}
			else
				{
				return 0;
				}
			}
		}

	public interface Listener extends Comparable
		{
		void incrementableProgressUpdated(IncrementorProgressEvent e);

		void incrementableDone(IncrementorDoneEvent e);

		void incrementableNoteUpdated(IncrementorNoteEvent e);
		}
	}
