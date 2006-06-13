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

/**
 * @author lorax
 * @version 1.0
 */
public class HibernateObjectTestFactory
	{
	private static Logger logger = Logger.getLogger(HibernateObjectTestFactory.class);
/*
	static Configuration memorytest = new Configuration().
            setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
            setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver").
            setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:test").
            setProperty("hibernate.connection.username", "sa").
            setProperty("hibernate.connection.password", "").
            setProperty("hibernate.connection.pool_size", "1").
            setProperty("hibernate.connection.autocommit", "false").
            setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider").
            setProperty("hibernate.hbm2ddl.auto", "create-drop").
            setProperty("hibernate.show_sql", "true").
            addClass(TestEntity.class);


	static Configuration mysqltest = new Configuration().
            setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect").
            setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver").
            setProperty("hibernate.connection.url", "jdbc:mysql://localhost/test").
            setProperty("hibernate.connection.username", "").
            setProperty("hibernate.connection.password", "").
            setProperty("hibernate.connection.pool_size", "1").
            setProperty("hibernate.connection.autocommit", "false").
            setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider").
            setProperty("hibernate.hbm2ddl.auto", "create-drop").
            setProperty("hibernate.show_sql", "true").
            addClass(TestEntity.class);



	@Factory
	public Object[] createInstances()
		{
		Object[] result = new Object[2];
		result[0] = memorytest.buildSessionFactory();
		result[1] = mysqltest.buildSessionFactory();
		return result;
		}
*/

	//@Factory
	public Object[] createInstances()
		{
		Object[] result = new Object[2];
	//	result[0] = new HibernateObjectTest("memorytest");
	//	result[1] = new HibernateObjectTest("mysqltest");
		return result;
		}

	//@Test
	//public void bogus() { assert true; }
	}
