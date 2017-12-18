package org.mybatis.mapper.xml;

import java.util.Map;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

public class SqlGenSourceBuilder extends BaseBuilder {

	public SqlGenSourceBuilder(Configuration configuration) {
		super(configuration);
	}

	public SqlSource parse(String originalSql, Class<?> parameterType, Map<String, Object> additionalParameters) {

		return new StaticSqlSource(configuration, originalSql, null);
	}
	

}
