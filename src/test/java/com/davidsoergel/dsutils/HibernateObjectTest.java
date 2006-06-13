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
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import org.testng.annotations.Test;
import org.testng.annotations.Configuration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

/**
 * HibernateObject Tester.
 *
 * Ideally multiple instances of this class could test different databases (e.g.
 * hSQLdb and MySQL in the same test run.  That should be doable both via
 * HibernateObjectTestFactory and through parameters in testng.xml.
 * However, only the latter approach seems to work, and only with parallel=false.
 *
 * @author lorax
 * @version $Revision$, $Date$
 * @since 1.0
 */
public class HibernateObjectTest
	{

	private static Logger logger = Logger.getLogger(HibernateObjectTest.class);

	//private EntityManagerFactory emf; // context;
	//public HibernateObjectTest(EntityManagerFactory f) { emf = f; }

	//private String context;

	public HibernateObjectTest()
		{
		/*
		context = c;
		if (context.equals("Default test name"))
			{
			context = "mysqltest";
			}
		if (context == null || context.equals(""))
			{
			assert false;
			}
			*/
		}

	private HibernateDB DB;

/*	@Configuration(beforeTestClass = true)
	public void setUp() //throws SQLException
		{

		}*/

   @Test
		   (parameters = { "persistenceUnitName" })
	public void openDatabase(String persistenceUnitName) throws SQLException
	   {
	   //String persistenceUnitName="mysqltest";
	   //HibernateDB.init(emf);
	   HibernateDB.init(persistenceUnitName);
	   DB = HibernateDB.getDb();  // assume all tests in the same thread!

	   assert tableExists("testentity");
	   }

	@Configuration(afterTestClass = true)
	public void cleanup() throws SQLException
	{
		dropAllTables();
	}

	@Test(dependsOnMethods="openDatabase")
	public void tableMatchesEntityAnnotations() throws SQLException {
	assert tableExists("TestEntity");  // Note test is case-insensitive
		// // apparently it gets solid-capitalized in HSQL, but not MySQL
		String[] columns = {"ID",
		                    "MODIFIEDDATE",
		                    "NAME"};
		assert tableHasColumnsExactly("TestEntity", new HashSet(Arrays.asList(columns)));
	}

	/*@Test(dependsOnMethods="openDatabase")
	public void syncAndPersistCreatesTable() throws SQLException
		{
		DB.sync();
		dropAllTables();
		DB.sync();

		//DB.sync();
		assert !tableExists("testentity");
		DB.sync();
		//assert tableExists("testentity");

		TestEntity t = new TestEntity();
		DB.beginTaxn();
		t.persist();
		DB.commit();

		assert tableExists("testentity");
		String[] columns = {"id",
		                    "modifieddate",
		                    "name"};
		assert tableHasColumnsExactly("testentity", Arrays.asList(columns));
		}
*/
	private boolean tableExists(String name) throws SQLException
		{

		Session s = ((HibernateEntityManager) DB.getEntityManager()).getSession();
		DatabaseMetaData dbmt = null;

		dbmt = s.connection().getMetaData();
		String[] names = {"TABLE"};
		ResultSet tableNames = dbmt.getTables(null, "%", "%", names);
		while (tableNames.next())
			{
			String tn = tableNames.getString("TABLE_NAME");
			logger.info("Found table: " + tn);
			if (tn.toUpperCase().equals(name.toUpperCase()))
				{
				//s.close();
				return true;
				}
			}
		//s.close();
		return false;
		}

	private boolean tableHasColumnsExactly(String tableName, Set<String> columnNames) throws SQLException
		{

		Session s = ((HibernateEntityManager) DB.getEntityManager()).getSession();
		DatabaseMetaData dbmt = null;
		Set<String> columnNamesUC = new HashSet<String>();
		for(String c : columnNames)
			{
			columnNamesUC.add(c.toUpperCase());
			}

		dbmt = s.connection().getMetaData();
		ResultSet columns = dbmt.getColumns(null, "%", tableName, "%");
		while (columns.next())
			{
			String foundColumn = columns.getString("COLUMN_NAME");
			logger.info("Found column: " + foundColumn);
			if (!columnNames.remove(foundColumn.toUpperCase()))
				{
				//s.close();
				return false;
				}
			}
		//s.close();
		return columnNames.size() == 0;

		}


	private void dropAllTables() throws SQLException
		{

		Session s = ((HibernateEntityManager) DB.getEntityManager()).getSession();
		DatabaseMetaData dbmt = null;
		Connection c = s.connection();
		dbmt = c.getMetaData();
		ResultSet tableNames = dbmt.getTables(null, "%", "%", null);
		while (tableNames.next())
			{
			String name = tableNames.getString("TABLE_NAME");
			Statement st;
			st = c.createStatement();
			try
				{
				st.execute("drop table " + name);
				}
			catch(SQLException e) {}
			finally
				{
				//st.close();
				}
			}
		//c.close();
		//s.close();
		}


	@Test(dependsOnMethods="openDatabase")
	public void idIsNullBeforePersistOutsideTransaction()
		{
		DB.sync();
		TestEntity t = new TestEntity();
		assert t.getId() == null;
		}

	@Test(dependsOnMethods="openDatabase")
	public void idIsNullAfterPersistOutsideTransaction()
		{
		DB.sync();
		TestEntity t = new TestEntity();
		t.persist();

		assert t.getId() == null;
		}

	@Test(dependsOnMethods="openDatabase")
	public void idIsNullBeforePersistInTransaction()
		{
		DB.sync();
		try
			{
			DB.beginTaxn();
			TestEntity t = new TestEntity();
			assert t.getId() == null;
			t.persist();
			}
		finally
			{
			DB.commit();
			}
		}

	@Test(dependsOnMethods="openDatabase")
	public void idIsNotNullAfterPersistInTransaction()
		{
		DB.sync();
		try
			{
			DB.beginTaxn();
			TestEntity t = new TestEntity();
			t.persist();
			assert t.getId() != null;
			}
		finally
			{
			DB.commit();
			}
		}


	@Test(dependsOnMethods="openDatabase")
	public void idIsNotNullAfterPersistAndCommit()
		{
		DB.sync();
		TestEntity t = new TestEntity();
		DB.beginTaxn();
		t.persist();
		DB.commit();

		assert t.getId() != null;
		}


	@Test(dependsOnMethods="openDatabase")
	public void persistOutsideTransactionOccursOnNextCommit()
		{
		DB.sync();
		assert !DB.inTransaction();
		TestEntity t = new TestEntity();
		t.persist();
		DB.beginTaxn();
		DB.commit();

		assert t.getId() != null;

		}

	@Test(dependsOnMethods="openDatabase")
	public void modificationsOutsideTransactionAreStoredOnNextCommit()
		{
		modificationsSavedTester(true, true);
		}

	@Test(dependsOnMethods="openDatabase")
	public void modificationsOutsideTransactionAreStoredOnSync()

		{
		modificationsSavedTester(false, false);
		}

	@Test(dependsOnMethods="openDatabase")
	public void modificationsInOpenTransactionAreStoredOnSync()
		{
		modificationsSavedTester(true, false);
		}

	private void modificationsSavedTester(boolean beginTaxn, boolean commitTaxn)
		{
		DB.sync();

		DB.beginTaxn();
		TestEntity t = new TestEntity("test1");
		t.persist();
		DB.commit();

		t.setName("test2");

		if (beginTaxn)
			{
			DB.beginTaxn();
			}
		if (commitTaxn)
			{
			DB.commit();
			}

		assert t.getName().equals("test2");
		Long id = t.getId();
		t = null;  // just pointing out that the old entity is gone, and inviting GC

		DB.sync();

		t = DB.find(TestEntity.class, id);
		assert t.getName().equals("test2");
		}


	@Test(dependsOnMethods="openDatabase")
	public void idIsSameAfterResaveAndCommit()
		{
		DB.sync();
		TestEntity t = new TestEntity("idIsSameAfterResave");
		DB.beginTaxn();
		t.persist();
		Long oldId = t.getId();
		DB.commit();

		DB.beginTaxn();
		t.setName("idIsSameAfterResave updated");
		DB.commit();


		assert t.getId() == oldId;

		}

	@Test(dependsOnMethods="openDatabase")
	public void idIsNullAfterRemoveAndCommit()
		{
		DB.sync();
		TestEntity t = new TestEntity("idIsSameAfterRemove");
		DB.beginTaxn();
		t.persist();
		Long oldId = t.getId();
		DB.commit();

		DB.beginTaxn();
		t.remove();
		DB.commit();


		assert t.getId() == null;
		}


	@Test(dependsOnMethods="openDatabase")
	public void idIsNullAfterRemoveBeforeCommit()
		{
		DB.sync();

		TestEntity t = new TestEntity("idIsSameAfterRemove");
		DB.beginTaxn();
		t.persist();
		Long oldId = t.getId();
		DB.commit();

		DB.beginTaxn();
		t.remove();

		try
			{
			assert t.getId() == null;
			}
		finally
			{
			DB.commit();
			}
		}

	@Test(dependsOnMethods="openDatabase")
	public void idIsNullAfterRemoveOutsideTransaction()
		{

		DB.sync();
		TestEntity t = new TestEntity("idIsSameAfterRemove");
		DB.beginTaxn();
		t.persist();
		Long oldId = t.getId();
		DB.commit();

		t.remove();

		assert t.getId() == null;
		}


	@Test(dependsOnMethods="openDatabase")
	public void modifieddateIsNullBeforeSave()
		{
		DB.sync();
		TestEntity t = new TestEntity();
		assert t.getModifieddate() == null;
		}

	@Test(dependsOnMethods="openDatabase")
	public void modifieddateIsNotNullAfterPersistOutsideTransaction()
		{
		DB.sync();
		TestEntity t = new TestEntity();
		t.persist();
		assert t.getModifieddate() != null;
		}


	@Test(dependsOnMethods="openDatabase")
	public void modifieddateIsNotNullAfterPersistAndCommit()
		{
		DB.sync();
		TestEntity t = new TestEntity();
		DB.beginTaxn();
		t.persist();
		DB.commit();
		assert t.getModifieddate() != null;
		}


	@Test(dependsOnMethods="openDatabase")
	public void modifieddateIsNotDifferentAfterUpdateOutsideTransaction() throws InterruptedException
		{
		DB.sync();
		TestEntity t = new TestEntity();
		t.persist();
		Date d = t.getModifieddate();
		Thread.sleep(1000);
		t.setName("bogus");
		assert t.getModifieddate() == d;
		}


	@Test(dependsOnMethods="openDatabase")
	public void modifieddateIsDifferentAfterUpdateAndCommit() throws InterruptedException
		{
		DB.sync();
		TestEntity t = new TestEntity();
		DB.beginTaxn();
		t.persist();
		DB.commit();
		Date d = t.getModifieddate();

		DB.beginTaxn();
		Thread.sleep(1000);
		t.setName("bogus");
		DB.commit();

		assert t.getModifieddate() != d;
		}


	@Test(dependsOnMethods="openDatabase")
	public void primaryKeyDoesNotAutoIncrementOutsideTransaction()
		{
		DB.sync();
		TestEntity t1 = new TestEntity();
		t1.persist();
		TestEntity t2 = new TestEntity();
		t2.persist();
		assert t2.getId() == t1.getId();
		}

	@Test(dependsOnMethods="openDatabase")
	public void primaryKeyAutoIncrementTakesEffectOnCommit()
		{
		DB.sync();
		TestEntity t1 = new TestEntity();
		t1.persist();
		TestEntity t2 = new TestEntity();
		t2.persist();

		DB.beginTaxn();
		DB.commit();

		assert t2.getId() == t1.getId() + 1;
		}


	@Test(dependsOnMethods="openDatabase")
	public void primaryKeyAutoIncrementsWithinTransaction()
		{
		DB.sync();
		DB.beginTaxn();
		try
			{
			TestEntity t1 = new TestEntity();
			t1.persist();
			TestEntity t2 = new TestEntity();
			t2.persist();
			assert t2.getId() == t1.getId() + 1;
			}
		finally
			{
			DB.commit();
			}
		}


	@Test(dependsOnMethods="openDatabase")
	public void primaryKeyAutoIncrementsBetweenTransactions()
		{
		DB.sync();
		DB.beginTaxn();
		try
			{
			TestEntity t1 = new TestEntity();
			t1.persist();

			DB.commit();
			DB.beginTaxn();
			TestEntity t2 = new TestEntity();
			t2.persist();
			assert t2.getId() == t1.getId() + 1;
			}
		finally
			{
			DB.commit();
			}
		}

	// I'm no longer so worried about entities from different entity managers
	// since I'm using one entity manager per thread (for now anyway...)
	/*
	@Test(dependsOnMethods="openDatabase")
	public void saveMergesExistingEntity()
		{
		}

	@Test(dependsOnMethods="openDatabase")
	public void savePersistsNewEntity()
		{
		}

	@Test(dependsOnMethods="openDatabase")
	public void savedEntitiesAreInDatabase()
		{
		}

	@Test(dependsOnMethods="openDatabase")
	public void sameEntityFromTwoEntityManagersAreDifferent()
		{
		}

	@Test(dependsOnMethods="openDatabase")
	public void sameEntityFromTwoEntityManagersAreSameAfterMerge()
		{
		}

	@Test(dependsOnMethods="openDatabase")
	public void twoDetachedEntitiesLastMergeWins()
		{
		}
*/
	}
