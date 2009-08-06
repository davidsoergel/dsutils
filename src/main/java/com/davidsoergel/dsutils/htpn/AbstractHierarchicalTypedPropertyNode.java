package com.davidsoergel.dsutils.htpn;

import com.davidsoergel.dsutils.collections.OrderedPair;
import com.davidsoergel.dsutils.tree.AbstractHierarchyNode;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public abstract class AbstractHierarchicalTypedPropertyNode<K extends Comparable, V, H extends HierarchicalTypedPropertyNode<K, V, H>>
		extends AbstractHierarchyNode<OrderedPair<K, V>, H>
//extends HierarchicalStringObjectMap//extends BasicTypedProperties
		implements HierarchicalTypedPropertyNode<K, V, H>
	{
	// ------------------------------ FIELDS ------------------------------

	private static final Logger logger = Logger.getLogger(BasicHierarchicalTypedPropertyNode.class);

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


	// the child map is null if the type is not a Factory or plugin type.

	// note the abstract getChildren() returns childrenByName.values()

	protected Map<K, H> childrenByName = new TreeMap<K, H>();

	public K getKey()
		{
		return payload == null ? null : payload.getKey1();
		}

	public void setKey(K newKey)
		{
		payload = new OrderedPair<K, V>(newKey, getValue());
		}

/*	public HierarchicalTypedPropertyNode<S, T> getParent()
		{
		return parent;
		}*/

/*	public void setParent(H newParent)
		{
		parent = newParent;
		}*/

	public Type getType()
		{
		return type;
		}

	public void setType(Type type)
		{
		this.type = type;
		}

	public V getValue()
		{
		return payload == null ? null : payload.getKey2();
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
		K key = getKey();
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
				return key.compareTo(((HierarchicalTypedPropertyNode<K, V, H>) o).getKey());
				}
		}

	// --------------------- Interface HierarchicalTypedPropertyNode ---------------------

	/**
	 * Generally not necessary to call expicitly since the child's constructor calls it already
	 *
	 * @param n
	 */
	public void addChild(@NotNull H n)
		{
		assert n.getKey() != null;
		childrenByName.put(n.getKey(), n);
		}


	public void addChild(K childKey, V childValue)
		{

		getOrCreateChild(childKey, childValue);
		}

	public void addChild(List<K> childKeyPath, V childValue)
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

	public void addChild(List<K> childKeyPath, K leafKey, V childValue)
		{

		getOrCreateDescendant(childKeyPath).getOrCreateChild(leafKey, childValue);
		}

	public void collectDescendants(List<K> path, Map<List<K>, HierarchicalTypedPropertyNode<K, V, H>> result)
		{
		List<K> keyPath = new LinkedList<K>(path);
		keyPath.add(getKey());
		result.put(keyPath, this);
		for (HierarchicalTypedPropertyNode<K, V, H> child : childrenByName.values())
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
	public Map<List<K>, HierarchicalTypedPropertyNode<K, V, H>> getAllDescendants()
		{
		Map<List<K>, HierarchicalTypedPropertyNode<K, V, H>> result =
				new HashMap<List<K>, HierarchicalTypedPropertyNode<K, V, H>>();

		collectDescendants(new LinkedList<K>(), result);

		return result;
		}

	public
	@NotNull
	Collection<H> getChildNodes()
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

	public V getInheritedValue(K forKey)
		{
		V result = getChildValue(forKey);

		if ((result == null || result.equals(PropertyConsumerFlags.INHERITED)) && parent != null)
			{
			result = parent.getInheritedValue(forKey);
			}
		return result;
		}

	public HierarchicalTypedPropertyNode<K, V, H> getInheritedNode(K forKey)
		{
		HierarchicalTypedPropertyNode<K, V, H> result = getChild(forKey);

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
	public H getOrCreateDescendant(List<K> keyPath)
		{
		H result;

		if (keyPath.size() == 0)
			{
			result = (H) this;
			}
		else
			{
			K childKey = keyPath.remove(0);
			assert childKey != null;
			HierarchicalTypedPropertyNode<K, V, H> child = getOrCreateChild(childKey, null);

			result = child.getOrCreateDescendant(keyPath);
			}
		return result;
		}


	/**
	 * For simple values, we can just copy the value here.  But for plugins, we need to copy the whole subtree.
	 */
	public void inheritValueIfNeeded()
		{
		try
			{
			V value = getValue();
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

	public void copyChildrenFrom(HierarchicalTypedPropertyNode<K, V, H> inheritedNode)
			throws HierarchicalPropertyNodeException
		{
		clearChildren();
		for (HierarchicalTypedPropertyNode<K, V, H> originalChild : inheritedNode.getChildren())
			{
			HierarchicalTypedPropertyNode<K, V, H> childCopy =
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

	public List<K> keyPath()
		{
		List<K> result;
		if (parent == null)
			{
			result = new LinkedList<K>();
			}
		else
			{
			result = parent.keyPath();
			}
		result.add(getKey());
		return result;
		}

	public void removeChild(K childKey)
		{
		childrenByName.remove(childKey);
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
	public void setValue(V value) throws HierarchicalPropertyNodeException
		{

		setValueForce(value);
		}

	protected void setValueForce(V value) throws HierarchicalPropertyNodeException
		{
		//WTF
		//boolean destructive = true;
		//V currentValue = getValue();
		//if (currentValue == null || currentValue == value)
		//	{
		//	destructive = false;
		//	}

		// do any required string parsing, including class names
		/*	if (value instanceof String)
		   {
		   value = parseValueString((String) value);
		   }*/
		//this.value = value;

		payload = new OrderedPair<K, V>(getKey(), value);

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
		childrenByName = new TreeMap<K, H>();
		}

	public V getChildValue(K childKey)
		{
		HierarchicalTypedPropertyNode<K, V, H> child = getChild(childKey);
		if (child == null)
			{
			return null;
			}
		else
			{
			return child.getPayload().getKey2();
			}
		}

	public H getChild(K childKey)
		{
		return childrenByName.get(childKey);
		}


	//private String programName;


/*	public H init(H parent, K key, V value, Type type) //throws HierarchicalPropertyNodeException
		{
		payload = new OrderedPair<K, V>(key, value);
		this.type = type;

		//this.contextNames = contextNames;
		//	this.trackContextName = trackContextName;
		//this.namesSubConsumer = namesSubConsumer;
		//name = theField.getDeclaringClass().getCanonicalName() + "." + theField.getName();
	//		this.parent = parent;
//
//	   if (parent != null)
//		   {
//		   parent.addChild(this);
//		   }
		setParent(parent);

		// note that the parent uses the name of this node as a key in its child map.
		// at the moment there isn't a setName() function, but if there were, it would have to update the parent as well.


		return (H) this;
		}
*/

/*
	static
		{
		TypedValueStringMapper.register(PluginMap.class, new PluginMapStringMapper());
		}
*/

	/*public void sanityCheck()
		{
		}*/

	@NotNull
	public Collection<H> getChildren()
		{
		return childrenByName.values();
		}


	public void registerChild(final H a)
		{
		assert a.getKey() != null;
		childrenByName.put(a.getKey(), a);
		}

	public void unregisterChild(final H a)
		{
		final K key = a.getKey();
		if (childrenByName.get(key).equals(a))
			{
			childrenByName.remove(key);
			}
		}

	public H getOrCreateChild(K childKey, V childValue)
		{
		H child = getChild(childKey);
		if (child == null)
			{
			// BAD hack payload should be final?
			child = newChild(new OrderedPair<K, V>(childKey, childValue));
			child.setType(childValue.getClass());
			//child.init((H) this, childKey, childValue, childValue.getClass());


			//		try
			//			{

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
			//child.setObsolete(true);
			//			}
			//		catch (HierarchicalPropertyNodeException e)
			//			{
			// this should never happen since we're passing a default value of null
			//			logger.error("Error", e);
			//			throw new Error(e);
			//			}
			//addChild(child); // implicit in child creation
			}
		return child;
		}

	public void copyFrom(final HierarchicalTypedPropertyNode<K, V, ?> node) throws HierarchicalPropertyNodeException
		{
		//setKey(node.getKey());
		//setValue(node.getValue());
		setType(node.getType());

		for (HierarchicalTypedPropertyNode<K, V, ?> child : node.getChildNodes()) //getChildren())
			{
			newChild(node.getPayload()).copyFrom(child);
			}
		}
	}

