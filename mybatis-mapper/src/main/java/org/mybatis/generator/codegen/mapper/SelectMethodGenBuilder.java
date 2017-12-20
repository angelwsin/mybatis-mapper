package org.mybatis.generator.codegen.mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.Table;

public class SelectMethodGenBuilder extends AbstractJavaMapperMethodGenBuilder {

	

	public SelectMethodGenBuilder(Configuration configuration, MappedStatement mst,Table introspectedTable) {
		super(configuration, mst,introspectedTable);
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
		Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		
		

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        importedTypes(importedTypes,method);
        method.setName(getId());



        //addMapperAnnotations(interfaze, method);

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
		
	}
	
	private void importedTypes(Set<FullyQualifiedJavaType> importedTypes,Method method){
		ResultMap resultmap = mst.getResultMaps().get(0);
		if(List.class.isAssignableFrom(resultmap.getType())){
			importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
			FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(List.class.getSimpleName());
			importedTypes.add(returnType);
			FullyQualifiedJavaType beanPackage = new FullyQualifiedJavaType(introspectedTable.getBeanNamespace());
			importedTypes.add(beanPackage);
			FullyQualifiedJavaType listType = new FullyQualifiedJavaType(introspectedTable.getBeanName());
			returnType.addTypeArgument(listType);
			method.setReturnType(returnType);
			paramterTypes(importedTypes,method);
		}else if(Map.class.isAssignableFrom(resultmap.getType())){
			importedTypes.add(FullyQualifiedJavaType.getNewMapInstance());
			FullyQualifiedJavaType result = new FullyQualifiedJavaType(Map.class.getSimpleName());
			result.addTypeArgument(FullyQualifiedJavaType.getStringInstance());
			result.addTypeArgument(FullyQualifiedJavaType.getObjectInstance());
			paramterTypes(importedTypes,method);
		}else if(Object.class.getName().equals(resultmap.getType().getName())){
			FullyQualifiedJavaType listType = new FullyQualifiedJavaType(introspectedTable.getBeanNamespace());
			method.setReturnType(listType);
			paramterTypes(importedTypes,method);
		}else{
			FullyQualifiedJavaType listType = new FullyQualifiedJavaType(resultmap.getType().getSimpleName());
			method.setReturnType(listType);
		}
		
	}
	
	

}
