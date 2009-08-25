package com.davidsoergel.dsutils.tree.htpn;

import com.davidsoergel.dsutils.DSClassUtils;
import com.davidsoergel.dsutils.GenericFactory;
import com.davidsoergel.dsutils.PluginManager;
import com.davidsoergel.dsutils.TypeUtils;
import com.davidsoergel.dsutils.collections.OrderedPair;
import com.davidsoergel.dsutils.increment.Incrementor;
import com.davidsoergel.dsutils.stringmapper.StringMapperException;
import com.davidsoergel.dsutils.stringmapper.TypedValueStringMapper;
import org.apache.commons.lang.NotImplementedException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Id$
 */
public class ExtendedHierarchicalTypedPropertyNodeImpl<K extends Comparable, V>
		extends AbstractHierarchicalTypedPropertyNode<K, V, ExtendedHierarchicalTypedPropertyNodeImpl<K, V>>
		implements ExtendedHierarchicalTypedPropertyNode<K, V, ExtendedHierarchicalTypedPropertyNodeImpl<K, V>>
	{
	private static final Logger logger = Logger.getLogger(ExtendedHierarchicalTypedPropertyNodeImpl.class);

	protected V defaultValue;

	private String helpmessage;
	//private String defaultValueString;

	//private Set<String> contextNames;
	//private boolean trackContextName;

	//private boolean namesSubConsumer;
	private boolean isNullable = false;

	// allow for locking the value
	// protected boolean editable = true;

	//private HierarchicalTypedPropertyNode<S, T> parent;

	private boolean obsolete = false;
	private boolean changed = false;

/*	public ExtendedHierarchicalTypedPropertyNodeImpl<K, V> init(ExtendedHierarchicalTypedPropertyNodeImpl<K, V> parent,
	                                                            K key, V value, Type type, String helpmessage)
			throws HierarchicalPropertyNodeException
		{
		super.init(parent, key, value, type);
		this.helpmessage = helpmessage;
		return this;
		}
*/

	/*	public ExtendedHierarchicalTypedPropertyNodeImpl<K, V> init(ExtendedHierarchicalTypedPropertyNodeImpl<K, V> parent,
																K key, V value, Type type, String helpmessage,
																V defaultValue,
																//Set<String> contextNames,
																// boolean trackContextName,
																//boolean namesSubConsumer,
																boolean isNullable)
			throws HierarchicalPropertyNodeException
		{
		init(parent, key, value, type, helpmessage);
		//	this.defaultValueString = defaultValueString;
	*/

	public void setDefaultAndNullable(V defaultValue, boolean isNullable) throws HierarchicalPropertyNodeException
		{
		if (type == null)
			{
			// this is a root node of unknown ROOTCONTEXT
			// or it's an obsolete entry from the database
			this.isNullable = true;
			}
		else
			{
			//setDefaultValue(defaultValue);
			//setNullable(isNullable);// also sets the value from the default, if needed
			this.defaultValue = defaultValue;
			this.isNullable = isNullable;
			useDefaultValueIfNeeded();
			defaultValueSanityChecks();
			}
		}

	// --------------------- GETTER / SETTER METHODS ---------------------
	public Map<K, ExtendedHierarchicalTypedPropertyNodeImpl<K, V>> getChildrenByName()
		{
		return childrenByName;
		}

	public V getDefaultValue()
		{
		return defaultValue;
		}

/*	public String getDefaultValueString()
		{
		return defaultValueString;
		}*/

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


/*	public boolean isEditable()
		{
		return editable;
		}

	public void setEditable(boolean editable)
		{
		this.editable = editable;
		}
*/

	public void setObsolete(boolean obsolete)
		{
		this.obsolete = obsolete;
		// ** obsoleteChildren();  ?
		}


	public void setChanged(boolean changed)
		{
		this.changed = changed;
		for (ExtendedHierarchicalTypedPropertyNode<K, V, ExtendedHierarchicalTypedPropertyNodeImpl<K, V>> child : childrenByName
				.values())
			{
			child.setChanged(changed);
			}
		}


	public SortedSet<Class> getPluginOptions(Incrementor incrementor)
		{
		//try
		V value = getValue();

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
	public void setValue(V value) //throws HierarchicalPropertyNodeException
		{
		/*	if (!editable)
		   {
		   throw new HierarchicalPropertyNodeException("Node is locked: " + this);
		   }
		   */
		setValueForce(value);
		}

	protected void setValueForce(V value) //throws HierarchicalPropertyNodeException
		{
		payload = new OrderedPair<K, V>(getKey(), value);

		// REVIEW possible hack re setting PluginMap values on an HTPN

		obsoleteChildren();

		//	clearChildren();
		}

	private void obsoleteChildren()
		{
		for (ExtendedHierarchicalTypedPropertyNodeImpl<K, V> child : childrenByName.values())
			{
			child.setObsolete(true);
			child.obsoleteChildren();
			}
		}


	/*	public void setDefaultValue(V defaultValue) throws HierarchicalPropertyNodeException
		 {
		 this.defaultValue = defaultValue;
		 }
 */


	public void defaultValueSanityChecks() throws HierarchicalPropertyNodeException
		{

		// some sanity checks
		if (defaultValue == null && !isNullable)
			{
			logger.error("Node is not nullable and has no default value: " + this);
			throw new HierarchicalPropertyNodeException("Node is not nullable and has no default value: " + this);

			//throw new HierarchicalPropertyNodeException("Node is not nullable and has no default value.");

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
	 * <p/>
	 * //@param nullable
	 *
	 * @throws HierarchicalPropertyNodeException
	 *
	 */
	/*public void setNullable(boolean nullable) throws HierarchicalPropertyNodeException
		{
		isNullable = nullable;
		}
*/
	public void useDefaultValueIfNeeded() throws HierarchicalPropertyNodeException
		{
		if (getValue() == null && !isNullable)
			{
			setValue(defaultValue);
			}
		}

	public ExtendedHierarchicalTypedPropertyNodeImpl<K, V> updateOrCreateChild(K childKey, V childValue)
		{
		ExtendedHierarchicalTypedPropertyNodeImpl<K, V> child = getChild(childKey);
		if (child == null)
			{
			// BAD hack: payload should be final?
			child = newChild(new OrderedPair<K, V>(childKey, childValue));
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

			//addChild(child); // implicit in child creation
			}
		return child;
		}


	/*	public ExtendedHierarchicalTypedPropertyNodeImpl<K, V> newChild()
		 {
		 ExtendedHierarchicalTypedPropertyNodeImpl<K, V> result = new ExtendedHierarchicalTypedPropertyNodeImpl<K, V>();
		 //children.add(result);  // setParent calls registerChild
		 result.setParent(this);
		 return result;
		 }
 */
	public ExtendedHierarchicalTypedPropertyNodeImpl<K, V> newChild(final OrderedPair<K, V> payload)
		{
		ExtendedHierarchicalTypedPropertyNodeImpl<K, V> result = new ExtendedHierarchicalTypedPropertyNodeImpl<K, V>();
		//children.add(result);  // setParent calls registerChild
		result.setPayload(payload);
		result.setParent(this);
		return result;
		}

	public void removeObsoletes()
		{
		HashSet<? extends ExtendedHierarchicalTypedPropertyNodeImpl<K, V>> set =
				new HashSet<ExtendedHierarchicalTypedPropertyNodeImpl<K, V>>(getChildNodes());
		for (ExtendedHierarchicalTypedPropertyNodeImpl<K, V> n : set)
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

	public void appendToStringBuffer(StringBuffer sb, int indentLevel)
		{
		for (int i = 0; i < indentLevel; i++)
			{
			sb.append("\t");
			}
		sb.append(getKey()).append(" = ").append(getValue()).append("\n");
		indentLevel++;

		for (ExtendedHierarchicalTypedPropertyNode child : childrenByName.values())
			{
			child.appendToStringBuffer(sb, indentLevel);
			}
		}

	public int getTotalRuns()
		{
		throw new NotImplementedException();
		}


	public void setValueFromString(String string) throws HierarchicalPropertyNodeException
		{

		try
			{
			setValue((V) TypedValueStringMapper.get(getType()).parse(string));
			//	setValue(SerializableValueOrRangeFactory.newTransientFromString(getType(), string));
			}
		catch (StringMapperException e)
			{
			logger.error("Error", e);
			throw new HierarchicalPropertyNodeException(e);
			}
		//		}
		}

	public V getValueForDescendant(final K[] keyList)
		{
		return getDescendant(keyList).getValue();
		}

	public ExtendedHierarchicalTypedPropertyNodeImpl<K, V> getDescendant(final K[] keyList)
		{
		ExtendedHierarchicalTypedPropertyNodeImpl<K, V> trav = this;

		for (K k : keyList)
			{
			trav = trav.getChild(k);
			}
		return trav;

		// recursive method requires copying/modifying the kryList, yuck
		/*
		if (keyList.isEmpty())
			{
			return null;
			}

		K firstKey = keyList.remove(0);

		if (keyList.isEmpty() && firstKey.equals(getKey()))
			{
			return getValue();
			}
		return getC
		return getPayload().
		*/
		}
	}
