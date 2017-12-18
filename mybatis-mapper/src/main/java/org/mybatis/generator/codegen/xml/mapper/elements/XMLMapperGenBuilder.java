package org.mybatis.generator.codegen.xml.mapper.elements;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.codegen.xml.mapper.elements.AbstractXmlGenBuilder;
import org.mybatis.generator.codegen.xml.mapper.elements.SelectElementGenBuilder;
import org.mybatis.generator.codegen.xml.mapper.elements.WhereElementGenBuilder;

public class XMLMapperGenBuilder extends AbstractXmlGenBuilder{

	public XMLMapperGenBuilder(Configuration configuration) {
		super(configuration);
	}

	@Override
	public Document getDocument() {
		 Document document = new Document(
	                XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
	                XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
	        document.setRootElement(getSqlMapElement());


	        return document;
	}
	
	protected XmlElement getSqlMapElement() {
        //FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
       
        XmlElement answer = new XmlElement("mapper"); //$NON-NLS-1$
        String namespace = introspectedTable.getMyBatis3SqlMapNamespace();
        answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
                namespace));
        
        for(String id : ids){
        	MappedStatement mst = 	getConfiguration().getMappedStatement(id);
        	switch (mst.getSqlCommandType()) {
			case SELECT:{
				select(getConfiguration(), id,answer);
			   }
			   break;

			default:
				break;
			}
        	
        }

        return answer;
    }
	
	
	protected void  select(Configuration configuration, String id,XmlElement parentElement){
		SelectElementGenBuilder selectElementGenBuilder = new SelectElementGenBuilder(configuration, id);
		selectElementGenBuilder.setIntrospectedTable(introspectedTable);
		XmlElement select  = selectElementGenBuilder.element(parentElement);
		WhereElementGenBuilder whereElement = new WhereElementGenBuilder(configuration, id);
		whereElement.setIntrospectedTable(introspectedTable);
		whereElement.element(select);
	}

}
