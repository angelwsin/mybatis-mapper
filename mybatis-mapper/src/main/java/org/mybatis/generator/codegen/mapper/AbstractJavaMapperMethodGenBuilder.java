package org.mybatis.generator.codegen.mapper;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.codegen.Table;
import org.mybatis.generator.codegen.xml.mapper.elements.AbstractGenBuilder;

public abstract class AbstractJavaMapperMethodGenBuilder extends AbstractGenBuilder{
	
    protected MappedStatement  mst;
	
	public AbstractJavaMapperMethodGenBuilder(Configuration configuration,MappedStatement  mst,Table introspectedTable) {
		super(configuration,introspectedTable);
		this.mst =  mst;
	}

	public abstract void addInterfaceElements(Interface interfaze);
	
	public List<String> getFilterByKey(String key){
		BoundSql sql = mst.getBoundSql(null);
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
		String[] ids = mst.getId().split("\\.");
		return ids[ids.length-1];
	}

}
