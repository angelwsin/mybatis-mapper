package org.mybatis.generator.codegen.xml.mapper.elements;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.codegen.Table;

public abstract class AbstractGenBuilder extends BaseBuilder{
	
	protected Table introspectedTable;
	
	protected MappedStatement  mst;
	
    protected static ThreadLocal<Map<String,Object>> Context = new ThreadLocal<Map<String,Object>>(){
    	public Map<String,Object> initialValue() {
    		return new HashMap<>();
    	};
    };
    
	public AbstractGenBuilder(Configuration configuration,MappedStatement  mst,Table introspectedTable) {
		super(configuration);
		this.introspectedTable = introspectedTable;
		this.mst = mst;
	}

	public Table getIntrospectedTable() {
		return introspectedTable;
	}

	public void setIntrospectedTable(Table introspectedTable) {
		this.introspectedTable = introspectedTable;
	}
	
	

}
