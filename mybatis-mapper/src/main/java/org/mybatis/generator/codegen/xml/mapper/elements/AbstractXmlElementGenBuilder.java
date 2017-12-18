package org.mybatis.generator.codegen.xml.mapper.elements;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.dom.xml.XmlElement;

public abstract class AbstractXmlElementGenBuilder extends AbstractGenBuilder{
	
	protected  String id;

	public AbstractXmlElementGenBuilder(Configuration configuration,String id) {
		super(configuration);
		this.id  = id;
	}
	
	
	public abstract XmlElement element(XmlElement parentElement);
	
	
	public List<String> getFilterByKey(String key){
		MappedStatement sta = getConfiguration().getMappedStatement(id);
		BoundSql sql = sta.getBoundSql(null);
		String kys = sql.getSql();
		String[] keys = kys.split(" ");
		String ky = ",";
		for(String k :keys){
			if(k.trim().startsWith(key)){
				ky = k.substring(key.length()+1);
				break;
			}
		}
		
		return Arrays.asList(ky.split(","));
	}


	
	
	
	public String getId(){
		String[] ids = id.split("\\.");
		return ids[ids.length-1];
	}
	
	
	
	

}
