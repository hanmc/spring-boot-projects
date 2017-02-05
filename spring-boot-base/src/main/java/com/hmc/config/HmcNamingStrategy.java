package com.hmc.config;

import org.hibernate.cfg.DefaultNamingStrategy;
import org.hibernate.internal.util.StringHelper;

/**
 * 数据库命名策略<p>
 * @author hanmc
 *
 */
public class HmcNamingStrategy extends DefaultNamingStrategy{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8723190589714976016L;

	@Override
	public String classToTableName(String className) {
		return "hmc_" + StringHelper.unqualify(className);
	}
	
	@Override
	public String propertyToColumnName(String propertyName) {
		return "hmc_" + StringHelper.unqualify(propertyName);
	}
}
