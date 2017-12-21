package org.mybatis.generator.codegen.xml.mapper.elements;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.Table;

public class DeleteElementGenBuilder extends AbstractXmlElementGenBuilder{

	public DeleteElementGenBuilder(Configuration configuration, Table introspectedTable, MappedStatement mst) {
		super(configuration, introspectedTable, mst);
		init();
	}

	@Override
	public void element(XmlElement parentElement) {
		XmlElement answer = new XmlElement("delete"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", getId())); //$NON-NLS-1$
        
        //context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("delete from "); //$NON-NLS-1$
        sb.append(introspectedTable.getRuntimeTableName());
        answer.addElement(new TextElement(sb.toString()));
        parentElement.addElement(answer);
        
        for(AbstractXmlElementGenBuilder b : buildes){
        	b.element(answer);
        }
		
	}

}
