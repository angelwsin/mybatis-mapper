package org.mybatis.generator.codegen.xml.mapper.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.Table;
import org.mybatis.mapper.config.Const;

public abstract class AbstractXmlElementGenBuilder extends AbstractGenBuilder{

	protected  List<AbstractXmlElementGenBuilder> buildes = new ArrayList<>();
	
	protected String[] colums;
	

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
	public String[] getKeys(){
		BoundSql sql = mst.getBoundSql(null);
		String kys = sql.getSql();
		String[] keys = kys.split("\\$");
		return keys;
	}
	
	public List<IntrospectedColumn> getFilterColums(String[] key){
		List<IntrospectedColumn> applyColum = new ArrayList<>();
		if(Objects.isNull(key)) return applyColum;
		List<String> filters = Arrays.asList(key);
		List<IntrospectedColumn> columns = introspectedTable.getColumns();
		if(Objects.nonNull(filters)&&filters.size()==1&&"all".equalsIgnoreCase(filters.get(0))){
			return columns;
		}
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
	
	
	public void setColums(String[] colums) {
		this.colums = colums;
	}
	
	public void init() {
		String[] keys = getKeys();
		for(String key : keys){
			int index = key.trim().indexOf(':');
			switch (key.trim().substring(0, index)) {
			case Const.APPEND:
				AppendElementGenBuilder app = new AppendElementGenBuilder(configuration, introspectedTable, mst);
				app.setText(key.trim().substring(index+1));
				buildes.add(app);
				break;
            case Const.COLUMS:
				setColums(key.trim().substring(index+1).split(","));
				addColumnsElementGenBuilder(colums);
				break;
            case Const.NO_IF:
            	NoIfElementGenBuilder noif = new NoIfElementGenBuilder(configuration, introspectedTable, mst);
            	noif.setNoif(key.trim().substring(index+1).split(","));
				buildes.add(noif);
            	break;
            case Const.WHERE_COLUMS:
            	WhereElementGenBuilder wheres = new WhereElementGenBuilder(configuration, introspectedTable, mst);
            	wheres.setWheres(key.trim().substring(index+1).split(","));
				buildes.add(wheres);
				break;

			default:
				throw new BuilderException(
						"A xml mapper fail .");
			}
		}
		
	}
	
	
	public void addColumnsElementGenBuilder(String[] columns){
		
	}
	

}
