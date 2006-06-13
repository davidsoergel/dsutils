/*
 * dsutils - a collection of generally useful utility classes
 *
 * Copyright (c) 2001-2006 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * david@davidsoergel.com
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * USA.
 *
 */

package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;


/** Convenience ThreadLocal Singleton class to hold an EntityManagerFactory
 * and the current EntityManager and Transaction.
 * If more than one context is needed in the same thread, this won't work.
 *
 * assert there is always exactly one live entity manager
 * assert if a transaction is known at all, then it is still open.
 * @author lorax
 * @version 1.0
 */
public class HibernateDB
	{
// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(HibernateDB.class);

	private static ThreadLocal<HibernateDB> _theDB_TL = new ThreadLocal(); // = new HibernateDB();

	//private Session session;
	//private SessionFactory sf;
	//private EntityManager em;

	// in some situations we'll want to share the emf between threads, see the init methods below
	private EntityManagerFactory emf;

	private EntityManager em;
	private EntityTransaction taxn;

// -------------------------- STATIC METHODS --------------------------

	public static void init(String context)
		{
		HibernateDB db = _theDB_TL.get();
		if(db != null)
			{
			db.close();
			}
		db = new HibernateDB(context);
		_theDB_TL.set(db);
		}

	public void close()
		{
		if(taxn != null) { taxn.rollback(); }
		if(em != null) { em.close(); }
		if(emf != null) { emf.close(); }
		taxn = null;
		em = null;
		emf = null;
		}

	public static void init(EntityManagerFactory _emf)
		{
		HibernateDB db = new HibernateDB(_emf);
		_theDB_TL.set(db);
		}


	public static HibernateDB getDb()
		{
		return _theDB_TL.get();
		}

/*	public static List findObjectsFromIDArray(String tablename, long[] ids)
		{
		EntityManager em = null;
		try
			{
			StringBuffer idlist = new StringBuffer();
			for (int i = 0; i < ids.length; i++)
				{
				idlist.append(ids[i]);
				if (i < ids.length - 1)
					{
					idlist.append(", ");
					}
				}

			em = HibernateDB.getDb().openEntityManager();
			Query query = em.createQuery("from " + tablename + " where id in (" + idlist + ")");
            return query.getResultList();  // generify??

			}
		catch (HibernateException e)
			{
			logger.debug(e);
			}
		finally
			{
			try
				{
				em.close();
				}
			catch (HibernateException e)
				{
				logger.debug(e);
				}
			}
		return null;
		}
*/

	public void beginTaxn() {
	if(taxn != null) { throw new HibernateException("Transaction already underway"); }// This naive implementation allows no concurrent transactions")
	taxn = em.getTransaction();
	taxn.begin();
	}

	public void commit() {
	if(taxn == null)
		{
		//throw new HibernateException("No current transaction");
		// No, we still want to commit any queued modifications
		beginTaxn();
		}
	taxn.commit(); taxn = null; }

	public void rollback() {
	if(taxn == null) { throw new HibernateException("No current transaction"); }
	taxn.rollback(); taxn = null; }

	// perhaps these methods should check for taxn.isActive() ?

// --------------------------- CONSTRUCTORS ---------------------------

	public HibernateDB(String context)
		{
		//logger.info("Initializing database...");
		try
			{
			//Configuration cfg = new Configuration().configure();
			//sf = cfg.buildSessionFactory();
			emf = Persistence.createEntityManagerFactory(context);
			}
		catch (Throwable e)
			{
			e.printStackTrace();
			logger.debug(e);
			}
		//logger.info("...done.");
		sync();
		}

	public HibernateDB(EntityManagerFactory emf)
		{
		this.emf = emf;
		sync();
		}

// ------------------------ CANONICAL METHODS ------------------------

	protected void finalize() throws Throwable
		{
		//if(taxn != null) { taxn.rollback(); }
		// will an open transaction be committed?
		if(em != null) { em.close(); }
		emf.close();
		super.finalize();    //To change body of overridden methods use File | Settings | File Templates.
		}

// -------------------------- OTHER METHODS --------------------------

/** AVOID since this requires discarding or merging all entities in memory.
The concern is that entities in memory get stale.  Maybe we can just refresh() them
 before any sensitive operation. */
	public void sync() throws HibernateException
		{
		if(em != null) {
		// the EntityManager is the extended kind, so it may have cached changes that
		// occurred outside a transaction.

		// does em.close save those automatically? No!

		taxn = em.getTransaction();
		if(!taxn.isActive()) { taxn.begin(); }
		em.flush();
		taxn.commit();
		em.close();
		}
		em = emf.createEntityManager();
		taxn = null;
		}
	/*
	public void updateSchema()
		{
		try
			{
			Configuration cfg = new Configuration().configure();
			new SchemaUpdate(cfg).execute(false, true);
			}
		catch (Throwable e)
			{
			e.printStackTrace();
			logger.debug(e);
			}
		}*/

	public EntityManager getEntityManager()
		{
		return em;
		}

	public boolean inTransaction()
		{
		if(taxn != null) { assert taxn.isActive(); }
		return taxn != null;
		}

	public <T> T find(Class<T> c, Object o)
		{
		return em.find(c, o);
		}

	public Query createNamedQuery(String name)
		{
		return em.createNamedQuery(name);
		}
	}
