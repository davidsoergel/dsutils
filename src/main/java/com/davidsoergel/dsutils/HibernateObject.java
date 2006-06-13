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
import org.hibernate.annotations.Proxy;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.PreUpdate;
import javax.persistence.PrePersist;
import java.util.Date;

/**
 * @author lorax
 * @version 1.0
 */
@MappedSuperclass
@Proxy(lazy=false)
public abstract class HibernateObject
	{
// ------------------------------ FIELDS ------------------------------

	private static Logger logger = Logger.getLogger(HibernateObject.class);

	private static final Long NOT_PERSISTED = null;

	private Long id = null;
	private Date modifieddate = null;

// -------------------------- OTHER METHODS --------------------------


	@Temporal(TemporalType.TIMESTAMP)
	public Date getModifieddate() { return modifieddate ; }
	public void setModifieddate(Date d) { modifieddate = d; }

    @PreUpdate
    @PrePersist
	public void updated() { modifieddate = new Date(); }

	// does it work to inherit the id field?
	@Id @GeneratedValue
	public Long getId() { return id; }

	public void setId(Long id)
		{
		this.id = id;
		}


	public void refresh() { HibernateDB.getDb().getEntityManager().refresh(this); }
	public void persist() { HibernateDB.getDb().getEntityManager().persist(this); }
	//public <? extends HibernateObject> merge() { return HibernateDB.getDb().getEntityManager().merge(this); }  // generify?
	public void remove() { HibernateDB.getDb().getEntityManager().remove(this); id = null; }

	// assume for now we'll never have to merge, since all operations are within a
	// thread with a single live EntityManager

/*
	public void atomicPersist() {
		EntityManager em = HibernateDB.getDb().openEntityManager();
			setModifieddate(new Date());
			EntityTransaction taxn = em.getTransaction();
			taxn.begin();
				em.persist(this);

			taxn.commit();
			em.flush();
	}

	public <T> atomicMerge(<T> tmp) {
		EntityManager em = HibernateDB.getDb().openEntityManager();
			setModifieddate(new Date());
			EntityTransaction taxn = em.getTransaction();
			taxn.begin();
				em.merge(this);

			taxn.commit();
			em.flush();
	}
*/

	// ** Inefficient but safe...?
	//Maybe this whole automatic dealing with detached entities was misguided
	// what happens if we just ditch it and write real EJB3 code in the app?
	/*
	public void save()
		{
		EntityTransaction taxn = null;
		EntityManager em = null;
		try
			{
			em = HibernateDB.getDb().openEntityManager();
			setModifieddate(new Date());
			taxn = em.getTransaction();
			taxn.begin();
			//session.lock(this, );
			if(getId() == NOT_PERSISTED)
				{
				em.persist(this);
				}
			else {
			em.merge(this);
				em.refresh(this);}
			//session.saveOrUpdate(this);
			taxn.commit();
			em.flush();
			}
		catch (HibernateException e)
			{
			logger.debug(e);
			try
				{
				taxn.rollback();
				}
			catch (Exception f)
				{
				logger.debug(f);
				}
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
		}*/

	// are these useful?  Are thy correct?  Will they screw up Hibernate?
/*
	public boolean equals(HibernateObject o)
		{
		if(!(o.getClass().equals(this.getClass()))) return false;  // messed up with inheritance
		return getId() == o.getId();
		}

	public int hashCode()
		{
		return getId().intValue();
		} */
	}
