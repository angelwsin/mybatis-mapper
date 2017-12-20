package org.mybatis.generator.codegen.xml.mapper.elements;

import java.util.Iterator;
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

	

	
	public WhereElementGenBuilder(Configuration configuration, Table introspectedTable, MappedStatement mst) {
		super(configuration, introspectedTable, mst);
	}

	@Override
	public void element(XmlElement parentElement) {
		List<IntrospectedColumn> columns = getFilterColums(Const.NO_IF);
		XmlElement answer = null;
		StringBuilder sb = new StringBuilder();
		boolean noifFlag = false;
        if(columns.size()>0){
          noifFlag = true;
          answer = parentElement;
          answer.addElement(new TextElement("where"));
  		  Iterator<IntrospectedColumn> noifIt = columns.iterator();
  		  while(noifIt.hasNext()){
  			  IntrospectedColumn introspectedColumn = noifIt.next();
  			  sb.setLength(0);
  			  sb.append(introspectedColumn.getJavaProperty());
  			  sb.append("="); 
  			  sb.append(MyBatisFormattingUtilities
  	                    .getParameterClause(introspectedColumn));
  			  if(noifIt.hasNext())sb.append(" and ");
  			  answer.addElement(new TextElement(sb.toString()));
  		  }
  		}
        
		columns = getFilterColums(Const.WHERE_COLUMS);
		if(Objects.isNull(columns)||columns.size()<=0)
			return ;
		answer = new XmlElement("where"); 
		
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

		if (!noifFlag) {
			parentElement.addElement(answer);
		}

	}

}
