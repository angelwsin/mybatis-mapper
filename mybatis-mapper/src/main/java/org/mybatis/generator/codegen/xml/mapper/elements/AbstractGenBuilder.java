package org.mybatis.generator.codegen.xml.mapper.elements;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.codegen.Table;

public abstract class AbstractGenBuilder extends BaseBuilder{
	
	protected Table introspectedTable;

	public AbstractGenBuilder(Configuration configuration) {
		super(configuration);
	}

	public Table getIntrospectedTable() {
		return introspectedTable;
	}

	public void setIntrospectedTable(Table introspectedTable) {
		this.introspectedTable = introspectedTable;
	}
	
	

}
