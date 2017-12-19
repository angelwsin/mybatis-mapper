package org.mybatis.generator.api.dom;

import org.mybatis.generator.api.dom.xml.Document;

public class DefaultXmlFormatter implements XmlFormatter {

	@Override
	public String getFormattedContent(Document document) {
        return document.getFormattedContent();
    }

}
