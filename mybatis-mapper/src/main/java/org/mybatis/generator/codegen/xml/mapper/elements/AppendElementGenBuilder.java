package org.mybatis.generator.codegen.xml.mapper.elements;

import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.Table;
import org.mybatis.mapper.config.Const;

public class AppendElementGenBuilder extends AbstractXmlElementGenBuilder{


	public AppendElementGenBuilder(Configuration configuration, Table introspectedTable, MappedStatement mst) {
		super(configuration, introspectedTable, mst);
	}

	@Override
	public void element(XmlElement parentElement) {
		List<String> append = getFilterByKey(Const.APPEND);
		for(String a : append){
			parentElement.addElement(new TextElement(a.trim()));	
		}
		
	}

}
