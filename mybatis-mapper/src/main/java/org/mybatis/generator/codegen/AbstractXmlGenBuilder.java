package org.mybatis.generator.codegen;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;

public abstract class AbstractXmlGenBuilder extends BaseBuilder{
	
	protected IntrospectedTable introspectedTable;
	
	public AbstractXmlGenBuilder(Configuration configuration) {
		super(configuration);
	}

	public abstract Document getDocument();

}
