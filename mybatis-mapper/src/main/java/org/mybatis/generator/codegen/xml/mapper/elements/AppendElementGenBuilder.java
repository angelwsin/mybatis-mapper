package org.mybatis.generator.codegen.xml.mapper.elements;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.Table;

public class AppendElementGenBuilder extends AbstractXmlElementGenBuilder{

    protected String text;

	public AppendElementGenBuilder(Configuration configuration, Table introspectedTable, MappedStatement mst) {
		super(configuration, introspectedTable, mst);
	}

	@Override
	public void element(XmlElement parentElement) {
		    for(String t :text.split("\n")){
		    	if(t.matches("\\s*\n*\\s*")) continue ;
		    	parentElement.addElement(new TextElement(t));
		    }	
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}
