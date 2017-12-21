package org.mybatis.generator.codegen.xml.mapper.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.Table;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.util.MyBatisFormattingUtilities;

public class InsertElementGenBuilder extends AbstractXmlElementGenBuilder{
	
	

	public InsertElementGenBuilder(Configuration configuration, Table introspectedTable, MappedStatement mst) {
		super(configuration, introspectedTable, mst);
		init();
	}

	@Override
	public void element(XmlElement parentElement) {
		//Const.COLUMS 
		List<IntrospectedColumn> columns = getFilterColums(colums);
		XmlElement answer = new XmlElement("insert"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", getId())); //$NON-NLS-1$
        
        

        //context.getCommentGenerator().addComment(answer);
        IntrospectedColumn introspectedColumn = introspectedTable
                .getGeneratedKey();
        if(Objects.nonNull(introspectedColumn)){
        	answer.addAttribute(new Attribute(
                    "useGeneratedKeys", "true")); //$NON-NLS-1$ //$NON-NLS-2$
            answer.addAttribute(new Attribute(
                    "keyProperty", introspectedColumn.getJavaProperty())); //$NON-NLS-1$
            answer.addAttribute(new Attribute(
                    "keyColumn", introspectedColumn.getActualColumnName())); //$NON-NLS-1$	
        }

        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();

        insertClause.append("insert into "); //$NON-NLS-1$
        insertClause.append(introspectedTable.getRuntimeTableName());
        insertClause.append(" ("); //$NON-NLS-1$

        valuesClause.append("values ("); //$NON-NLS-1$

        List<String> valuesClauses = new ArrayList<String>();
        for (int i = 0; i < columns.size(); i++) {
              introspectedColumn = columns.get(i);
            
            if(introspectedColumn.isAutoIncrement())
                continue;
            insertClause.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            valuesClause.append(MyBatisFormattingUtilities
                    .getParameterClause(introspectedColumn));
            if (i + 1 < columns.size()) {
                insertClause.append(", "); //$NON-NLS-1$
                valuesClause.append(", "); //$NON-NLS-1$
            }

            if (valuesClause.length() > 80) {
                answer.addElement(new TextElement(insertClause.toString()));
                insertClause.setLength(0);
                OutputUtilities.xmlIndent(insertClause, 1);

                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                OutputUtilities.xmlIndent(valuesClause, 1);
            }
        }

        insertClause.append(')');
        answer.addElement(new TextElement(insertClause.toString()));

        valuesClause.append(')');
        valuesClauses.add(valuesClause.toString());

        for (String clause : valuesClauses) {
            answer.addElement(new TextElement(clause));
        }

        parentElement.addElement(answer);
		
	}

	
	
	

}
