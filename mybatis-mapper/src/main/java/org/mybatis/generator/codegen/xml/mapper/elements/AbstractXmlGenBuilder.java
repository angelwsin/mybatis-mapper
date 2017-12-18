package org.mybatis.generator.codegen.xml.mapper.elements;

import java.util.List;

import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.dom.xml.Document;

public abstract class AbstractXmlGenBuilder extends AbstractGenBuilder{

	protected List<String>      ids;
	
	public AbstractXmlGenBuilder(Configuration configuration) {
		super(configuration);
	}
	
	public abstract Document getDocument();


	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	
	
	

}
