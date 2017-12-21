package org.mybatis.generator.codegen.xml.mapper.elements;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.Table;
import org.mybatis.generator.codegen.util.MyBatisFormattingUtilities;
import org.mybatis.mapper.config.Const;

public class NoIfElementGenBuilder extends AbstractXmlElementGenBuilder{
	
	protected String[] noif;

	public NoIfElementGenBuilder(Configuration configuration, Table introspectedTable, MappedStatement mst) {
		super(configuration, introspectedTable, mst);
	}

	@Override
	public void element(XmlElement parentElement) {
		// Const.NO_IF
		List<IntrospectedColumn> columns = getFilterColums(noif);
		StringBuilder sb = new StringBuilder();
		Context.get().put(getId()+Const.NO_IF, false);
		if (columns.size() > 0) {
			Context.get().put(getId()+Const.NO_IF, true);
			parentElement.addElement(new TextElement("where"));
			Iterator<IntrospectedColumn> noifIt = columns.iterator();
			while (noifIt.hasNext()) {
				IntrospectedColumn introspectedColumn = noifIt.next();
				sb.setLength(0);
				sb.append(introspectedColumn.getJavaProperty());
				sb.append("=");
				sb.append(MyBatisFormattingUtilities.getParameterClause(introspectedColumn));
				if (noifIt.hasNext())
					sb.append(" and ");
				parentElement.addElement(new TextElement(sb.toString()));
			}
		}

	}
	
	public void setNoif(String[] noif) {
		this.noif = noif;
	}

}
