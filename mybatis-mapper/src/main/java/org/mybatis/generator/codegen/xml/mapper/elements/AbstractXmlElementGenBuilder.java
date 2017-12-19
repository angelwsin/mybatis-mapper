package org.mybatis.generator.codegen.xml.mapper.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.Table;

public abstract class AbstractXmlElementGenBuilder extends AbstractGenBuilder{
	
	protected  String id;
	protected  List<AbstractXmlElementGenBuilder> buildes = new ArrayList<>();
	

	public AbstractXmlElementGenBuilder(Configuration configuration,String id,Table introspectedTable) {
		super(configuration,introspectedTable);
		this.id  = id;
	}
	
	
	public abstract void element(XmlElement parentElement);
	
	
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
	
	public List<IntrospectedColumn> getFilterColums(String key){
		List<IntrospectedColumn> columns = introspectedTable.getColumns();
		List<IntrospectedColumn> applyColum = new ArrayList<>();
		List<String> filters = getFilterByKey(key);
		if(filters.size()>0){
			columns.forEach(colum->{
				if(filters.contains(colum.getActualColumnName())){
					applyColum.add(colum);
				}
			});
		}
		return applyColum;
	}


	
	
	
	public String getId(){
		String[] ids = id.split("\\.");
		return ids[ids.length-1];
	}
	
	
	
	

}
