package org.mybatis.mapper.config;

import javax.sql.DataSource;

final public class Environment {
	
	private final DataSource dataSource;
	
	
	public Environment(DataSource dataSource) {
		this.dataSource  = dataSource;
	}

	public DataSource getDataSource() {
		return dataSource;
	}
	
	
	

}
