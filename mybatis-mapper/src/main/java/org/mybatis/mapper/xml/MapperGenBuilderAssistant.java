package org.mybatis.mapper.xml;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapperGenBuilderAssistant extends MapperBuilderAssistant {

	private static Logger logger = LoggerFactory.getLogger(MapperGenBuilderAssistant.class);

	private String currentTableName;

	private String currentCatalog;

	private String currentSchema;
	
	private List<String> ids = new  ArrayList<>();

	public MapperGenBuilderAssistant(Configuration configuration, String resource) {
		super(configuration, resource);

	}

	public String getCurrentTableName() {
		return currentTableName;
	}

	public void setCurrentTableName(String currentTableName) {
		this.currentTableName = currentTableName;
	}

	public String getCurrentCatalog() {
		return currentCatalog;
	}

	public void setCurrentCatalog(String currentCatalog) {
		this.currentCatalog = currentCatalog;
	}

	public String getCurrentSchema() {
		return currentSchema;
	}

	public void setCurrentSchema(String currentSchema) {
		this.currentSchema = currentSchema;
	}

	


	public void addElement(String id) {
		ids.add(applyCurrentNamespace(id, false));
		

	}

	public List<String> getIds() {
		return ids;
	}
	
	


}
