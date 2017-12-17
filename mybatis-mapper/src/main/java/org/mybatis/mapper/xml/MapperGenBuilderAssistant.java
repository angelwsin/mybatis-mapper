package org.mybatis.mapper.xml;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.db.DatabaseGenIntrospector;
import org.mybatis.mapper.config.Const;
import org.mybatis.mapper.config.GenConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapperGenBuilderAssistant extends MapperBuilderAssistant{
	
	private static Logger logger = LoggerFactory.getLogger(MapperGenBuilderAssistant.class);
	
	private String currentTableName;
	
	private String currentCatalog;
	
	private String currentSchema;
	
	

	public MapperGenBuilderAssistant(Configuration configuration, String resource) {
		super(configuration, resource);
		
	}

	public String getCurrentTableName() {
		return currentTableName;
	}

	public void setCurrentTableName(String currentTableName) {
		this.currentTableName = currentTableName;
	}
	
	

	public String getCurrentCatalog() {
		return currentCatalog;
	}

	public void setCurrentCatalog(String currentCatalog) {
		this.currentCatalog = currentCatalog;
	}

	public String getCurrentSchema() {
		return currentSchema;
	}

	public void setCurrentSchema(String currentSchema) {
		this.currentSchema = currentSchema;
	}

	@Override
	public MappedStatement addMappedStatement(String id, SqlSource sqlSource, StatementType statementType,
			SqlCommandType sqlCommandType, Integer fetchSize, Integer timeout, String parameterMap,
			Class<?> parameterType, String resultMap, Class<?> resultType, ResultSetType resultSetType,
			boolean flushCache, boolean useCache, boolean resultOrdered, KeyGenerator keyGenerator, String keyProperty,
			String keyColumn, String databaseId, LanguageDriver lang) {
		System.out.println(getCurrentTableName());
		BoundSql boundSql = sqlSource.getBoundSql(null);
		System.out.println(boundSql.getAdditionalParameter(Const.COLUMS));
		return null;
	}
	
	@Override
	public MappedStatement addMappedStatement(String id, SqlSource sqlSource, StatementType statementType,
			SqlCommandType sqlCommandType, Integer fetchSize, Integer timeout, String parameterMap,
			Class<?> parameterType, String resultMap, Class<?> resultType, ResultSetType resultSetType,
			boolean flushCache, boolean useCache, boolean resultOrdered, KeyGenerator keyGenerator, String keyProperty,
			String keyColumn, String databaseId, LanguageDriver lang, String resultSets) {
		Configuration conf = getConfiguration();
		DataSource dataSource = conf.getEnvironment().getDataSource();
		try (Connection con = dataSource.getConnection();){
			
			DatabaseGenIntrospector dataIntrosp = new DatabaseGenIntrospector(con.getMetaData(),getContext() );
			dataIntrosp.getColumns(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*System.out.println(getCurrentTableName());
		BoundSql boundSql = sqlSource.getBoundSql(null);
		System.out.println(boundSql.getAdditionalParameter(Const.COLUMS));*/
		return null;
	}
	
	
	private Context getContext(){
		return  ((GenConfiguration)getConfiguration()).getContext();
	}
	
	
	
	
}
