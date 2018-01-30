package org.mybatis.generator.codegen.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.codegen.Table;
import org.mybatis.generator.codegen.xml.mapper.elements.AbstractGenBuilder;

public abstract class AbstractJavaMapperMethodGenBuilder extends AbstractGenBuilder{
	
    
	
	

	public AbstractJavaMapperMethodGenBuilder(Configuration configuration, MappedStatement mst,
			Table introspectedTable) {
		super(configuration, mst, introspectedTable);
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
	
	protected void paramterTypes(Set<FullyQualifiedJavaType> importedTypes,Method method){
		List<ParameterMapping> params = mst.getParameterMap().getParameterMappings();
		if(params!=null&&params.size()>0){
			importedTypes.add(new FullyQualifiedJavaType(Param.class.getName()));
			params.forEach(param->{
				FullyQualifiedJavaType type = new FullyQualifiedJavaType(param.getJavaType().getSimpleName());
				method.addParameter(new Parameter(type, param.getProperty(),String.format("@Param(\"%s\")", param.getProperty())));
			});
		}else{
			if(Object.class.getName().equals(mst.getParameterMap().getType().getName())){
				FullyQualifiedJavaType param = new FullyQualifiedJavaType(introspectedTable.getBeanNamespace());
				method.addParameter(new Parameter(param, introspectedTable.paramName()));
			}else if(Map.class.isAssignableFrom(mst.getParameterMap().getType())){
				importedTypes.add(FullyQualifiedJavaType.getNewMapInstance());
				FullyQualifiedJavaType param = new FullyQualifiedJavaType(Map.class.getSimpleName());
				param.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
				param.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
				method.addParameter(new Parameter(param, introspectedTable.paramName()));
			}
			
		}
	}

}
