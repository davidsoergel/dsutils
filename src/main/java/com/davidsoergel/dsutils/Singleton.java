/*
 * Ashatech Ibex - A framework for rapid development of dynamic web applications
 *
 * Copyright (C) 2002 Asha Technologies
 * 1215 2nd Avenue, San Francisco, CA  94122
 * info@ashatech.com
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

/* $Id: Singleton.java,v 1.1.1.1 2003/03/19 22:54:32 david Exp $ */

package com.davidsoergel.dsutils;

import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Use Singleton.instance(String) to access a given
 * instance by name.
 */
public abstract class Singleton {
    private static Logger logger = Logger.getLogger(Singleton.class);

    static private HashMap _registry = new HashMap();

    /**
     * The constructor could be made private
     * to prevent others from instantiating this class.
     * But this would also make it impossible to
     * create instances of Singleton subclasses.
     */
    protected Singleton() {
        logger.debug(
                "Singleton registered: "
                + this.getClass().getName());
        _registry.put(this.getClass().getName().toLowerCase(), this);

        // _registry.put(this.getClass().getName(), this);

    }

    /**
     * @return The unique instance of the specified class.
     */
    static public Singleton instance(String byname)
            throws ClassNotFoundException {

        // byname = byname.toLowerCase();

        logger.debug(
                "Singleton.instance(\"" + byname + "\")");

        Singleton result = (Singleton) (_registry.get(byname.toLowerCase()));

        // make sure the class has been loaded, since it wouldn't yet be in Singleton's registry otherwise

        if (result == null) {
            try {

                // ** this doesn't work due to the lowercasing issue!!

                Class.forName(byname).newInstance();

                result = (Singleton) (_registry.get(byname.toLowerCase()));
            } catch (Exception e) {
                throw new ClassNotFoundException("Couldn't find class "
                        + byname
                        + ".  Check to make sure it was preloaded.");
            }
        }

        return result;
    }

    /**
     * Method declaration
     *
     *
     * @return
     *
     * @see
     */
    public String toString() {
        return this.getClass().getName();
    }

}

