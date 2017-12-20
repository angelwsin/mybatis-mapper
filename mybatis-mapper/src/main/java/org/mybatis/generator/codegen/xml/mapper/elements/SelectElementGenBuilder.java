package org.mybatis.generator.codegen.xml.mapper.elements;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.Table;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.mapper.config.Const;

public class SelectElementGenBuilder extends AbstractXmlElementGenBuilder{
	
	

	

	
	public SelectElementGenBuilder(Configuration configuration, Table introspectedTable, MappedStatement mst) {
		super(configuration, introspectedTable, mst);
		buildes.add(new WhereElementGenBuilder(configuration,introspectedTable, mst));
		buildes.add(new AppendElementGenBuilder(configuration, introspectedTable, mst));
	}

	@Override
	public void element(XmlElement parentElement) {
		List<IntrospectedColumn> columns = getFilterColums(Const.COLUMS);
		XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", getId())); //$NON-NLS-1$
        ResultMap resultmap = mst.getResultMaps().get(0);
		if(List.class.isAssignableFrom(resultmap.getType())){
			answer.addAttribute(new Attribute("resultType", List.class.getSimpleName()));
		}else if(Map.class.isAssignableFrom(resultmap.getType())){
			answer.addAttribute(new Attribute("resultType", Map.class.getSimpleName()));
		}else if(Object.class.getName().equals(resultmap.getType().getName())){
			answer.addAttribute(new Attribute("resultType", introspectedTable.getBeanNamespace()));
		}
        

        //context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select "); //$NON-NLS-1$
        Iterator<IntrospectedColumn> iter = columns
                .iterator();
        while (iter.hasNext()) {
        	IntrospectedColumn introspectedColumn = iter.next();
            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(introspectedColumn));
            sb.append(" as ").append(introspectedColumn.getJavaProperty(null));
            if (iter.hasNext()) {
                sb.append(", "); //$NON-NLS-1$
            }

            if (sb.length() > 80) {
                answer.addElement(new TextElement(sb.toString()));
                sb.setLength(0);
            }
        }

        if (sb.length() > 0) {
            answer.addElement(new TextElement(sb.toString()));
        }

        sb.setLength(0);
        sb.append("from "); //$NON-NLS-1$
        sb.append(introspectedTable.getRuntimeTableName());
        answer.addElement(new TextElement(sb.toString()));
        parentElement.addElement(answer);
        
        for(AbstractXmlElementGenBuilder b : buildes){
        	b.element(answer);
        }
	}

}
