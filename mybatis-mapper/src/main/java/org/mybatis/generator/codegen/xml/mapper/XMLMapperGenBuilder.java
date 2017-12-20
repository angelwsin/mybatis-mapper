package org.mybatis.generator.codegen.xml.mapper;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.Table;
import org.mybatis.generator.codegen.XmlConstants;
import org.mybatis.generator.codegen.xml.mapper.elements.AbstractXmlGenBuilder;
import org.mybatis.generator.codegen.xml.mapper.elements.SelectElementGenBuilder;
import org.mybatis.generator.codegen.xml.mapper.elements.UpdateElementGenBuilder;

public class XMLMapperGenBuilder extends AbstractXmlGenBuilder{


	public XMLMapperGenBuilder(Configuration configuration, Table introspectedTable) {
		super(configuration, introspectedTable, null);
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
        String namespace = introspectedTable.getMyBatisSqlMapNamespace();
        answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
                namespace));
        
        for(String id : ids){
        	MappedStatement mst = 	getConfiguration().getMappedStatement(id);
        	switch (mst.getSqlCommandType()) {
			case SELECT:{
				select(getConfiguration(), mst,answer);
			   }
			   break;
			case UPDATE:{
				update(getConfiguration(), mst,answer);
			   }
			   break;

			default:
				break;
			}
        	
        }

        return answer;
    }
	
	
	private void update(Configuration configuration, MappedStatement mst, XmlElement parentElement) {
		UpdateElementGenBuilder update = new UpdateElementGenBuilder(configuration, introspectedTable, mst);
		update.element(parentElement);
		
	}

	protected void  select(Configuration configuration, MappedStatement mst,XmlElement parentElement){
		SelectElementGenBuilder selectElementGenBuilder = new SelectElementGenBuilder(configuration,introspectedTable,mst);
		selectElementGenBuilder.element(parentElement);
		
	}

}
