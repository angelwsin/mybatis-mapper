package org.mybatis.generator.codegen.xml.mapper.elements;

import java.util.List;
import java.util.Objects;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.Table;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.util.MyBatisFormattingUtilities;
import org.mybatis.mapper.config.Const;

public class WhereElementGenBuilder extends AbstractXmlElementGenBuilder{
	 
	 protected String[] wheres;

	
	public WhereElementGenBuilder(Configuration configuration, Table introspectedTable, MappedStatement mst) {
		super(configuration, introspectedTable, mst);
	}

	@Override
	public void element(XmlElement parentElement) {
		Object obj = Context.get().get(getId()+Const.NO_IF);
		boolean noifFlag = obj==null?false:(boolean) obj; 
        //Const.WHERE_COLUMS
		List<IntrospectedColumn> columns = getFilterColums(wheres);
		if(Objects.isNull(columns)||columns.size()<=0)
			return ;
		XmlElement answer = parentElement;
		if (!noifFlag) {
		    answer = new XmlElement("where"); 
			parentElement.addElement(answer);
		}
		
		StringBuilder sb = new StringBuilder();
		int sum = 0;
		for (IntrospectedColumn introspectedColumn : columns) {
			sum++;
			XmlElement ifElement = new XmlElement("if"); //$NON-NLS-1$
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null"); //$NON-NLS-1$
            ifElement.addAttribute(new Attribute(
                    "test", sb.toString())); //$NON-NLS-1$

            sb.setLength(0);
            if(noifFlag&&sum==1)
            	sb.append(" and ");
            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatisFormattingUtilities
                    .getParameterClause(introspectedColumn));
            if(sum!=columns.size())sb.append(" and ");
            ifElement.addElement(new TextElement(sb.toString()));
            answer.addElement(ifElement);
           
		}

	}

	

	public void setWheres(String[] wheres) {
		this.wheres = wheres;
	}
	
	

}
