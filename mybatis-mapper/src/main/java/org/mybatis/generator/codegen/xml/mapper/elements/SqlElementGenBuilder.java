package org.mybatis.generator.codegen.xml.mapper.elements;

import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.dom.xml.XmlElement;

public class SqlElementGenBuilder extends AbstractXmlElementGenBuilder{


	public SqlElementGenBuilder(Configuration configuration, String id) {
		super(configuration, id);
	}

	@Override
	public XmlElement element(XmlElement parentElement){
		 XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

	        /*answer.addAttribute(new Attribute("id", //$NON-NLS-1$
	                introspectedTable.getBaseColumnListId()));

	        context.getCommentGenerator().addComment(answer);

	        StringBuilder sb = new StringBuilder();
	        Iterator<IntrospectedColumn> iter = introspectedTable
	                .getNonBLOBColumns().iterator();
	        while (iter.hasNext()) {
	            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(iter
	                    .next()));

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

	        if (context.getPlugins().sqlMapBaseColumnListElementGenerated(
	                answer, introspectedTable)) {
	            parentElement.addElement(answer);
	        }*/
		 
		 return null;
		
	}

}
