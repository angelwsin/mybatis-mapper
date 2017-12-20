package org.mybatis.generator.codegen.xml.mapper.elements;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.codegen.Table;

public abstract class AbstractGenBuilder extends BaseBuilder{
	
	protected Table introspectedTable;
	protected MappedStatement  mst;
	

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
