package org.mybatis.generator.codegen.xml.mapper.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.Table;

public abstract class AbstractXmlElementGenBuilder extends AbstractGenBuilder{

	protected  List<AbstractXmlElementGenBuilder> buildes = new ArrayList<>();
	

	public AbstractXmlElementGenBuilder(Configuration configuration,Table introspectedTable,MappedStatement  mst) {
		super(configuration,mst,introspectedTable);
	}
	
	
	public abstract void element(XmlElement parentElement);
	
	
	public List<String> getFilterByKey(String key){
		BoundSql sql = mst.getBoundSql(null);
		String kys = sql.getSql();
		String[] keys = kys.split("\\$");
		String ky = ",";
		for(String k :keys){
			if(k.trim().startsWith(key)){
				ky = k.trim().substring(key.length()+1);
				break;
			}
		}
		
		return Arrays.asList(ky.split(","));
	}
	
	public List<IntrospectedColumn> getFilterColums(String key){
		List<String> filters = getFilterByKey(key);
		List<IntrospectedColumn> columns = introspectedTable.getColumns();
		if(Objects.nonNull(filters)&&filters.size()==1&&"all".equalsIgnoreCase(filters.get(0))){
			return columns;
		}
		List<IntrospectedColumn> applyColum = new ArrayList<>();
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
		String[] ids = mst.getId().split("\\.");
		return ids[ids.length-1];
	}
	
	
	
	

}
