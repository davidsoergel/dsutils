package com.davidsoergel.dsutils.stringmapper;

import com.davidsoergel.dsutils.StringMapper;
import com.davidsoergel.dsutils.StringMapperException;
import org.apache.log4j.Logger;

/**
 * @author <a href="mailto:dev@davidsoergel.com">David Soergel</a>
 * @version $Rev$
 */
public class ClassMapper extends StringMapper<Class>
	{
	private static final Logger logger = Logger.getLogger(ClassMapper.class);

	public ClassMapper()
		{
		//super();
		}

	public Class parse(String s) throws StringMapperException
		{
		try
			{
			return Class.forName(s);
			}
		catch (ClassNotFoundException e)
			{
			logger.debug(e);
			e.printStackTrace();
			throw new StringMapperException(e);
			}
		}

	public String render(Class value)
		{
		return value.getCanonicalName();
		}
	}
