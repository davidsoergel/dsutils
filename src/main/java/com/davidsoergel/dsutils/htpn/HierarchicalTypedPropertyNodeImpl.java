/*
 * Copyright (c) 2001-2008 David Soergel
 * 418 Richmond St., El Cerrito, CA  94530
 * dev@davidsoergel.com
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the names of any contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.davidsoergel.dsutils.htpn;

import com.davidsoergel.dsutils.DSClassUtils;
import com.davidsoergel.dsutils.GenericFactory;
import com.davidsoergel.dsutils.Incrementable;
import com.davidsoergel.dsutils.PluginManager;
import com.davidsoergel.dsutils.TypeUtils;
import com.davidsoergel.dsutils.collections.OrderedPair;
import com.davidsoergel.dsutils.stringmapper.StringMapperException;
import com.davidsoergel.dsutils.stringmapper.TypedValueStringMapper;
import com.davidsoergel.dsutils.tree.AbstractHierarchyNode;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id: HierarchicalTypedPropertyNodeImpl.java 255 2009-08-01 22:53:03Z soergel $
 */

public class HierarchicalTypedPropertyNodeImpl<S extends Comparable, T>
		extends AbstractHierarchyNode<OrderedPair<S, T>, HierarchicalTypedPropertyNode<S, T>>
//extends HierarchicalStringObjectMap//extends BasicTypedProperties
		implements HierarchicalTypedPropertyNode<S, T>
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(HierarchicalTypedPropertyNodeImpl.class);

	//protected S key;
	//protected T value;

	/**
	 * the type of value that is expected in this node.  This type must be a equal to or a subclass of the generic type
	 * required for values.  The actual runtime type of the value may be a subtype of this.  Also, the value may be null,
	 * but we still want to know what type of value is expected, so we can't just use value.getClass() instead.
	 */
	protected Type type;

	// the value may be a primitive value (or of a standard wrapped type, or array, etc.), or in the case of a plugin, a Class.
	// Note we don't put GenericFactories here, just the Class that the factory produces.

	// In the event that the Type shows this is supposed to be a plugin, but the appropriate Class does not exist,
	// then the value may instead contain the String giving the fully qualified class name.

	// Types and Values here should not refer to wrappers inheriting from ParameterSetValueOrKey, so they can't represent ranges.

	protected T defaultValue;

	// the child map is null if the type is not a Factory or plugin type.

	protected Map<S, HierarchicalTypedPropertyNode<S, T>> childrenByName =
			new TreeMap<S, HierarchicalTypedPropertyNode<S, T>>();
	private String helpmessage;
	private String defaultValueString;

	//private Set<String> contextNames;
	//private boolean trackContextName;

	//private boolean namesSubConsumer;
	private boolean isNullable = false;

	// allow for locking the value
	protected boolean editable = true;
	private HierarchicalTypedPropertyNode<S, T> parent;

	private boolean obsolete = false;
	private boolean changed = false;

	/**
	 * Test whether this node has had any fields set yet.  Returns true only if the node is completely empty of interesting
	 * contents.
	 *
	 * @return
	 */
	/*	public boolean isNew()
	   {
	   return parent == null && key == null && value == null && type == null && helpmessage == null
			   && defaultValue == null && getChildNodes().isEmpty();
	   }*/

	// --------------------- GETTER / SETTER METHODS ---------------------
	public Map<S, HierarchicalTypedPropertyNode<S, T>> getChildrenByName()
		{
		return childrenByName;
		}

	public T getDefaultValue()
		{
		return defaultValue;
		}

	public String getDefaultValueString()
		{
		return defaultValueString;
		}

	public String getHelpmessage()
		{
		return helpmessage;
		}

	/*	public boolean isNamesSubConsumer()
		 {
		 return namesSubConsumer;
		 }
 */

	public void setHelpmessage(String helpmessage)
		{
		this.helpmessage = helpmessage;
		}

	public S getKey()
		{
		return payload.getKey1();
		}

	public void setKey(S newKey)
		{
		payload = new OrderedPair<S, T>(newKey, getValue());
		}

/*	public HierarchicalTypedPropertyNode<S, T> getParent()
		{
		return parent;
		}*/

	public void setParent(HierarchicalTypedPropertyNode<S, T> newParent)
		{
		parent = newParent;
		}

	public Type getType()
		{
		return type;
		}

	public void setType(Type type)
		{
		this.type = type;
		}

	public T getValue()
		{
		return payload.getKey2();
		}

	public boolean isEditable()
		{
		return editable;
		}

	public void setEditable(boolean editable)
		{
		this.editable = editable;
		}

	public void setObsolete(boolean obsolete)
		{
		this.obsolete = obsolete;
		// ** obsoleteChildren();  ?
		}


	public void setChanged(boolean changed)
		{
		this.changed = changed;
		for (HierarchicalTypedPropertyNode<S, T> child : childrenByName.values())
			{
			child.setChanged(changed);
			}
		}

	// ------------------------ CANONICAL METHODS ------------------------

	/*	public boolean equals(Object obj)
		 {
		 return super.equals(obj);//To change body of overridden methods use File | Settings | File Templates.
		 }
 */

	public String toString()
		{
		return "HierarchicalTypedPropertyNode -> keyPath = " + keyPath() + ", type = " + type + ", value = "
		       + getValue();
		//return name;
		}

	// ------------------------ INTERFACE METHODS ------------------------


	// --------------------- Interface Comparable ---------------------

	public int compareTo(Object o)
		{
		S key = getKey();
		if (o == null)
			{
			return -1;
			}
		else if (this.equals(o))
			{
			return 0;
			}
		else if (key == null)
				{
				return -1;
				}
			else
				{
				return key.compareTo(((HierarchicalTypedPropertyNode<S, T>) o).getKey());
				}
		}

	// --------------------- Interface HierarchicalTypedPropertyNode ---------------------

	/**
	 * Generally not necessary to call expicitly since the child's constructor calls it already
	 *
	 * @param n
	 */
	public void addChild(@NotNull HierarchicalTypedPropertyNode<S, T> n)
		{
		childrenByName.put(n.getKey(), n);
		}

	public void addChild(S childKey, T childValue)
		{

		getOrCreateChild(childKey, childValue);
		}

	public void addChild(List<S> childKeyPath, T childValue)
		{
		try
			{
			getOrCreateDescendant(childKeyPath).setValue(childValue);
			}
		catch (HierarchicalPropertyNodeException e)
			{
			logger.error("Error", e);
			throw new Error(e);
			}
		}

	public void addChild(List<S> childKeyPath, S leafKey, T childValue)
		{

		getOrCreateDescendant(childKeyPath).getOrCreateChild(leafKey, childValue);
		}

	public void collectDescendants(List<S> path, Map<List<S>, HierarchicalTypedPropertyNode<S, T>> result)
		{
		List<S> keyPath = new LinkedList<S>(path);
		keyPath.add(getKey());
		result.put(keyPath, this);
		for (HierarchicalTypedPropertyNode<S, T> child : childrenByName.values())
			{
			child.collectDescendants(keyPath, result);
			}
		}

	/**
	 * Returns a map of key paths to nodes, containing this node and all its descendants.  All key paths start with the key
	 * of this node.
	 *
	 * @return
	 */
	public Map<List<S>, HierarchicalTypedPropertyNode<S, T>> getAllDescendants()
		{
		Map<List<S>, HierarchicalTypedPropertyNode<S, T>> result =
				new HashMap<List<S>, HierarchicalTypedPropertyNode<S, T>>();

		collectDescendants(new LinkedList<S>(), result);

		return result;
		}

	public
	@NotNull
	Collection<? extends HierarchicalTypedPropertyNode<S, T>> getChildNodes()
		{
		return childrenByName.values();
		}

	public Object[] getEnumOptions()
		{
		//	Class c = theField.getType();

		try
			{
			return (((Class) type).getEnumConstants());
			}
		catch (ClassCastException e)
			{
			throw new Error("Can't get enum options for a field of generic type.");
			}
		}

	public T getInheritedValue(S forKey)
		{
		T result = getChildValue(forKey);

		if ((result == null || result.equals(PropertyConsumerFlags.INHERITED)) && parent != null)
			{
			result = parent.getInheritedValue(forKey);
			}
		return result;
		}

	public HierarchicalTypedPropertyNode<S, T> getInheritedNode(S forKey)
		{
		HierarchicalTypedPropertyNode<S, T> result = getChild(forKey);

		if ((result == null || result.getValue().equals(PropertyConsumerFlags.INHERITED)) && parent != null)
			{
			result = parent.getInheritedNode(forKey);
			}
		return result;
		}

	/**
	 * Finds the descendant of this node named by the child keys in the given list in order, creating nodes if necessary.
	 *
	 * @param keyPath A List of child keys.  Presently this method will destroy the list; maybe we should copy it first.
	 * @return
	 */
	public HierarchicalTypedPropertyNode<S, T> getOrCreateDescendant(List<S> keyPath)
		{
		HierarchicalTypedPropertyNode<S, T> result;

		if (keyPath.size() == 0)
			{
			result = this;
			}
		else
			{
			S childKey = keyPath.remove(0);
			assert childKey != null;
			HierarchicalTypedPropertyNode<S, T> child = getOrCreateChild(childKey, null);

			result = child.getOrCreateDescendant(keyPath);
			}
		return result;
		}

	public SortedSet<Class> getPluginOptions(Incrementable incrementor)
		{
		//try
		T value = getValue();

		if (!(value == null || value instanceof GenericFactory || value instanceof Class))
			{
			logger.warn("Can't get plugin options for a value " + value + " of type " + value.getClass());
			}
		//Class c = theField.getType();
		//Type genericType = theField.getGenericType();

		java.util.SortedSet<Class> result = new TreeSet<Class>(new Comparator<Class>()
		{
		public int compare(Class o1, Class o2)
			{
			if (o1 == null)
				{
				return -1;
				}
			else if (o2 == null)
				{
				return 1;
				}
			else
				{
				return o1.getSimpleName().compareTo(o2.getSimpleName());
				}
			}
		});
		if (isNullable)
			{
			result.add(null);
			}
		try
			{
			PluginManager.registerPluginsFromDefaultPackages(type, incrementor);
			}
		catch (IOException e)
			{
			logger.error("Error", e);
			throw new Error(e);
			}

		result.addAll(PluginManager.getPlugins(type));
		return result;
		//			}
		//		catch (PluginException e)
		//			{
		//			return null;
		//			}
		}

	/**
	 * For simple values, we can just copy the value here.  But for plugins, we need to copy the whole subtree.
	 */
	public void inheritValueIfNeeded()
		{
		try
			{
			T value = getValue();
			if (value == PropertyConsumerFlags.INHERITED)
				{
				setValueForce(getParent().getInheritedValue(getKey()));

				// copy the plugin _definition_, not the plugin instance itself
				// if the plugin is a singleton, it will be used that way

				if (isClassBoundPlugin())
					{
					logger.warn("Plugin definition inherited: " + getKey() + "=" + getValue()
					            + " (singleton only if get/setInjectedInstance exists, else newly instantiated)");

					copyChildrenFrom(getParent().getInheritedNode(getKey()));
					}
				}
			}
		catch (HierarchicalPropertyNodeException e)
			{
			logger.error("Error", e);
			throw new Error(e);
			}
		}

	/*public Map<S, HierarchicalTypedPropertyNode<S, T>> getChildrenByName()
		{
		return childrenByName;
		}*/

	public void copyChildrenFrom(HierarchicalTypedPropertyNode<S, T> inheritedNode)
			throws HierarchicalPropertyNodeException
		{
		clearChildren();
		for (HierarchicalTypedPropertyNode<S, T> originalChild : inheritedNode.getChildren())
			{
			HierarchicalTypedPropertyNode<S, T> childCopy =
					getOrCreateChild(originalChild.getKey(), originalChild.getValue());
			childCopy.copyChildrenFrom(originalChild);
			}

		/*	Map<S, HierarchicalTypedPropertyNode<S, T>> originalChildren = inheritedNode.getChildrenByName();
	   for (S key : originalChildren.keySet())
		   {
		   HierarchicalTypedPropertyNode<S, T> childCopy = getOrCreateChild(key);
		   HierarchicalTypedPropertyNode<S, T> originalChild = originalChildren.get(key);
		   childCopy.setValue(originalChild.getValue());
		   childCopy.copyChildrenFrom(originalChild);
		   }*/
		}

	public boolean isClassBoundPlugin()
		{
		return //value instanceof GenericFactory ||
				getValue() instanceof Class;
		}

//** All PluginMap functionality needs revisiting
/*	public boolean isPluginMap()
		{
		return TypeUtils.isAssignableFrom(PluginMap.class, type);
		}*/

	public boolean isNullable()
		{
		return isNullable;
		}

	public boolean isObsolete()
		{
		return obsolete || (parent != null && parent.isObsolete());
		}

	public boolean isChanged()
		{
		return changed;
		}


	public List<S> keyPath()
		{
		List<S> result;
		if (parent == null)
			{
			result = new LinkedList<S>();
			}
		else
			{
			result = parent.keyPath();
			}
		result.add(getKey());
		return result;
		}

	public void removeChild(S childKey)
		{
		childrenByName.remove(childKey);
		}

	public void removeObsoletes()
		{
		HashSet<? extends HierarchicalTypedPropertyNode<S, T>> set =
				new HashSet<HierarchicalTypedPropertyNode<S, T>>(getChildNodes());
		for (HierarchicalTypedPropertyNode<S, T> n : set)
			{
			if (n.isObsolete())
				{
				removeChild(n.getKey());
				}
			else
				{
				n.removeObsoletes();
				}
			}
		}

	/**
	 * Set the value of this node.  If this node expects a plugin and the value is a Class (or a String naming a Class),
	 * then create the children array if necessary and populate it with defaults.  Act similarly for a PluginMap.
	 * <p/>
	 * If the children array already exists, and the value was previously null or the same class, then we merge the
	 * defaults non-destructively.
	 *
	 * @param value
	 * @throws HierarchicalPropertyNodeException
	 *
	 */
	public void setValue(T value) throws HierarchicalPropertyNodeException
		{
		if (!editable)
			{
			throw new HierarchicalPropertyNodeException("Node is locked: " + this);
			}
		setValueForce(value);
		}

	private void setValueForce(T value) throws HierarchicalPropertyNodeException
		{
		boolean destructive = true;
		T currentValue = getValue();
		if (currentValue == null || currentValue == value)
			{
			destructive = false;
			}

		// do any required string parsing, including class names
		/*	if (value instanceof String)
		   {
		   value = parseValueString((String) value);
		   }*/
		//this.value = value;

		payload = new OrderedPair<S, T>(getKey(), value);

		// REVIEW possible hack re setting PluginMap values on an HTPN


//** All PluginMap functionality needs revisiting
		/*
		if (isPluginMap() && value != null)// value instanceof PluginMap
			{
			throw new HierarchicalPropertyNodeException(
					"Can't set a plugin value on a regular HTPN; need to use the StringNamed version");
			}
		else */
		if (isClassBoundPlugin() && value != null)
			{

			throw new HierarchicalPropertyNodeException(
					"Can't set a plugin value on a regular HTPN; need to use the StringNamed version");
			}
		else
			{
			clearChildren();
			}

		// if the value is a String giving a class name that is not currently loadable, we may yet add children later
		}

	// -------------------------- OTHER METHODS --------------------------

	public void clearChildren()
		{
		childrenByName = new TreeMap<S, HierarchicalTypedPropertyNode<S, T>>();
		}

	public T getChildValue(S childKey)
		{
		HierarchicalTypedPropertyNode<S, T> child = getChild(childKey);
		if (child == null)
			{
			return null;
			}
		else
			{
			return child.getPayload().getKey2();
			}
		}

	public HierarchicalTypedPropertyNode<S, T> getChild(S childKey)
		{
		return childrenByName.get(childKey);
		}

	public HierarchicalTypedPropertyNode<S, T> getOrCreateChild(S childKey, T childValue)
		{
		HierarchicalTypedPropertyNode<S, T> child = getChild(childKey);
		if (child == null)
			{
			try
				{
				child = newChild().init(this, childKey, childValue, null, null, null, true);
				/*
				if (keys.size() == 0)
					{
					// this is the leaf node, so we give it the requested type
					child = new HierarchicalTypedPropertyNodeImpl<S, T>()
							.init(this, childKey, type, null, null, true);
					}
				else
					{
					// this is an intermediate node, so we give it type Class
					child = new HierarchicalTypedPropertyNodeImpl<S, T>()
							.init(this, childKey, Class.class, null, null, true);
					}*/

				// if the node didn't already exist, then the referenced field is no longer in the class that was parsed
				child.setObsolete(true);
				}
			catch (HierarchicalPropertyNodeException e)
				{
				// this should never happen since we're passing a default value of null
				logger.error("Error", e);
				throw new Error(e);
				}
			//addChild(child); // implicit in child creation
			}
		return child;
		}

	//private String programName;

	public HierarchicalTypedPropertyNodeImpl<S, T> init(HierarchicalTypedPropertyNode<S, T> parent, S key, T value,
	                                                    Type type, String helpmessage, String defaultValueString,
	                                                    //Set<String> contextNames,
	                                                    // boolean trackContextName,
	                                                    //boolean namesSubConsumer,
	                                                    boolean isNullable) throws HierarchicalPropertyNodeException
		{
		init(parent, key, value, type, helpmessage);
		this.defaultValueString = defaultValueString;
		if (type == null)
			{
			// this is a root node of unknown ROOTCONTEXT
			// or it's an obsolete entry from the database
			this.isNullable = true;
			}
		else
			{
			this.isNullable = true;// temporary
			setDefaultValueString(defaultValueString);
			setNullable(isNullable);// also sets the value from the default, if needed
			}
		return this;
		}

	public void setDefaultValueString(String defaultValueString) throws HierarchicalPropertyNodeException
		{
		try
			{
			// the default value should be the actual injectable value, not the string representation
			defaultValue = parseValueString(defaultValueString);
			}
		catch (Throwable e)
			{
			logger.error("Error", e);

			// too bad, just leave it alone
			}

		// some sanity checks
		if (defaultValue == null)
			{
			if (!isNullable)
				{
				logger.error("Node is not nullable and has no default value.");
				//throw new HierarchicalPropertyNodeException("Node is not nullable and has no default value.");
				}
			}
		/*	else if (TypeUtils.isAssignableFrom(PluginDoubleMap.class, type))
		   {
		   if (!defaultValue.getClass().equals(PluginDoubleMap.class))
			   {
			   throw new HierarchicalPropertyNodeException(
					   "Node is a PluginMap, but the default value is not a PluginMap");
			   }
		   }*/

//** All PluginMap functionality needs revisiting
		/*
	   else if (defaultValue.getClass().equals(PluginMap.class))
		   {
		   // if the default value is a PluginMap, then we can leave it alone
		   }*/
		else if (defaultValue.getClass().equals(Class.class))
			{
			// if the default value is a Class, then this node represents a plugin, so we can leave the value alone
			}
		else if (!(type instanceof Class))// && ! this.defaultValue.getClass().equals(Class.class) implicit
				{
				// type must be ParameterizedType
				throw new HierarchicalPropertyNodeException(
						"Node has generic type, but the default value is not a plugin class");
				}
			else if (type instanceof Class && ((Class) type).isPrimitive() && DSClassUtils
						.isAssignable(defaultValue.getClass(), DSClassUtils.primitiveToWrapper((Class) type)))
					{
					// non-generic case; allows for primitives
					}
				else if (!TypeUtils.isAssignableFrom(type, defaultValue.getClass()))
						{
						// does not allow primitives, but does deal with generics, though that doesn't apply here anyway... hmmm.
						throw new HierarchicalPropertyNodeException(
								"Can't assign a default value of type " + defaultValue.getClass()
								+ " to a node requiring " + type);
						}
		}

	/**
	 * Sets the nullable status of this node, and sets the value to the defaultValue if necessary.  If a node is nullable,
	 * and is null, then leave it that way; don't force the default value
	 *
	 * @param nullable
	 * @throws HierarchicalPropertyNodeException
	 *
	 */
	public void setNullable(boolean nullable) throws HierarchicalPropertyNodeException
		{
		isNullable = nullable;
		if (!nullable)
			{
			if (defaultValue == null)
				{
				throw new HierarchicalPropertyNodeException("Node is not nullable and has no default value: " + this);
				}
			if (getValue() == null)
				{
				setValue(defaultValue);
				}
			}
		}

	public HierarchicalTypedPropertyNodeImpl<S, T> init(HierarchicalTypedPropertyNode<S, T> parent, S key, T value,
	                                                    Type type, String helpmessage)
			throws HierarchicalPropertyNodeException
		{
		payload = new OrderedPair<S, T>(key, value);
		this.type = type;
		this.helpmessage = helpmessage;

		//this.contextNames = contextNames;
		//	this.trackContextName = trackContextName;
		//this.namesSubConsumer = namesSubConsumer;
		//name = theField.getDeclaringClass().getCanonicalName() + "." + theField.getName();
		this.parent = parent;

		if (parent != null)
			{
			parent.addChild(this);
			}

		// note that the parent uses the name of this node as a key in its child map.
		// at the moment there isn't a setName() function, but if there were, it would have to update the parent as well.


		return this;
		}

/*
	static
		{
		TypedValueStringMapper.register(PluginMap.class, new PluginMapStringMapper());
		}
*/

	protected T parseValueString(String valueString) throws HierarchicalPropertyNodeException
		{
		T result;
		//	if (type.equals(String.class))
		//		{
		//		result = (T) valueString;
		//		}
		//	else
		//		{
		if (valueString == null || valueString.equals("") || valueString.equals("null"))
			{
			return null;
			}
		try
			{
			result = (T) TypedValueStringMapper.parse(type, valueString);
			}
		catch (StringMapperException e)
			{
			logger.error("Unparseable value for property " + getKey() + ": " + valueString, e);
			throw new HierarchicalPropertyNodeException(e);
			}
		//		}
		return result;
		}

	/*public void sanityCheck()
		{
		}*/

	@NotNull
	public Collection<HierarchicalTypedPropertyNode<S, T>> getChildren()
		{
		return childrenByName.values();
		}

	public HierarchicalTypedPropertyNodeImpl<S, T> newChild()
		{
		HierarchicalTypedPropertyNodeImpl<S, T> result = new HierarchicalTypedPropertyNodeImpl<S, T>();
		//children.add(result);  // setParent calls registerChild
		result.setParent(this);
		return result;
		}

	public HierarchicalTypedPropertyNode<S, T> newChild(final OrderedPair<S, T> payload)
		{
		HierarchicalTypedPropertyNodeImpl<S, T> result = new HierarchicalTypedPropertyNodeImpl<S, T>();
		//children.add(result);  // setParent calls registerChild
		result.setParent(this);
		result.setPayload(payload);
		return result;
		}


	public void registerChild(final HierarchicalTypedPropertyNode<S, T> a)
		{
		childrenByName.put(a.getKey(), a);
		}

	public void unregisterChild(final HierarchicalTypedPropertyNode<S, T> a)
		{
		final S key = a.getKey();
		if (childrenByName.get(key).equals(a))
			{
			childrenByName.remove(key);
			}
		}
	}
