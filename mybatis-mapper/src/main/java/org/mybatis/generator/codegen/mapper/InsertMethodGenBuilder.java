package org.mybatis.generator.codegen.mapper;

import java.util.Set;
import java.util.TreeSet;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.Table;

public class InsertMethodGenBuilder extends AbstractJavaMapperMethodGenBuilder{

	public InsertMethodGenBuilder(Configuration configuration, MappedStatement mst, Table introspectedTable) {
		super(configuration, mst, introspectedTable);
	}

	@Override
	public void addInterfaceElements(Interface interfaze) {
       Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
		
		

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        paramterTypes(importedTypes, method);
        method.setName(getId());



        //addMapperAnnotations(interfaze, method);

        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method); 
		
	}

}
