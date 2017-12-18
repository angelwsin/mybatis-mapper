package org.mybatis.generator.codegen.xml.mapper.elements;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.util.MyBatisFormattingUtilities;
import org.mybatis.mapper.config.Const;

public class WhereElementGenBuilder extends AbstractXmlElementGenBuilder{

	

	public WhereElementGenBuilder(Configuration configuration, String id) {
		super(configuration, id);
	}

	@Override
	public XmlElement element(XmlElement parentElement) {
		List<IntrospectedColumn> columns = introspectedTable.getColumns();
		List<IntrospectedColumn> applyColum = new ArrayList<>();
		List<String> filters = getFilterByKey(Const.WHERE_COLUMS);
		if(filters.size()>0){
			columns.forEach(colum->{
				if(filters.contains(colum.getActualColumnName())){
					applyColum.add(colum);
				}
			});
			columns = applyColum;
		}
		
		XmlElement answer = new XmlElement("where"); 
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
            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatisFormattingUtilities
                    .getParameterClause(introspectedColumn));
            if(sum!=columns.size())sb.append("and");
            ifElement.addElement(new TextElement(sb.toString()));
            answer.addElement(ifElement);
           
        }
		return answer;
	}

}
