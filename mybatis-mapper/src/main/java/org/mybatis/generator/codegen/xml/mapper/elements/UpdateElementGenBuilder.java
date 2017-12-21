package org.mybatis.generator.codegen.xml.mapper.elements;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.Table;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.util.MyBatisFormattingUtilities;

public class UpdateElementGenBuilder extends AbstractXmlElementGenBuilder{

	public UpdateElementGenBuilder(Configuration configuration, Table introspectedTable, MappedStatement mst) {
		super(configuration, introspectedTable, mst);
		init();
	}

	@Override
	public void element(XmlElement parentElement) {
		//Const.COLUMS
		List<IntrospectedColumn> columns = getFilterColums(colums);
		XmlElement answer = new XmlElement("update"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", getId())); //$NON-NLS-1$
        
        //context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("update ").append(introspectedTable.getRuntimeTableName()).append(" set "); //$NON-NLS-1$
        Iterator<IntrospectedColumn> iter = columns
                .iterator();
        while (iter.hasNext()) {
        	IntrospectedColumn introspectedColumn = iter.next();
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(introspectedColumn));
            sb.append(" = ").append(MyBatisFormattingUtilities
	                    .getParameterClause(introspectedColumn));
            if (iter.hasNext()) {
                sb.append(", "); //$NON-NLS-1$
            }

            if (sb.length() > 80) {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }

        answer.addElement(new TextElement(sb.toString()));
        parentElement.addElement(answer);
        
        for(AbstractXmlElementGenBuilder b : buildes){
        	b.element(answer);
        }
		
	}
	
	

}
