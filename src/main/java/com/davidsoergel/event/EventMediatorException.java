package com.davidsoergel.event;//import java.util.*;import java.io.*;public class EventMediatorException extends Exception	{// ------------------------------ FIELDS ------------------------------    Throwable parent;// --------------------------- CONSTRUCTORS ---------------------------    /**     * Constructor declaration     *     *     */    public EventMediatorException() {        super();    }	/**	 *	 * @param e the cause	 */	public EventMediatorException(Throwable e) {        super();        parent = e;    }    /**     * Constructor declaration     *     *     * @param s     *     */    public EventMediatorException(String s) {        super(s);    }    /**     * Constructor declaration     *     *     * @param e     * @param s     *     */    public EventMediatorException(Throwable e, String s) {        super(s);        parent = e;    }// -------------------------- OTHER METHODS --------------------------	public void printStackTrace() {        if (parent != null) {            parent.printStackTrace();        }        super.printStackTrace();    }    /**     * Method declaration     *     *     * @param pw     *     */    public void printStackTrace(PrintWriter pw) {        if (parent != null) {            parent.printStackTrace(pw);        }        super.printStackTrace(pw);    }    /**     * Method declaration     *     *     * @param s     *     */    public void printStackTrace(PrintStream s) {        if (parent != null) {            parent.printStackTrace(s);        }        super.printStackTrace(s);    }	}