package org.mybatis.generator.codegen.mapper;

import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.Table;

public class JavaMapperGenBuilder extends AbstractJavaGenBuilder{
	
	
	protected List<String>      ids;
	
	
	public JavaMapperGenBuilder(Configuration configuration,List<String> ids, Table introspectedTable) {
		super(configuration, introspectedTable, null);
		this.ids = ids;
	}

	@Override
    public CompilationUnit getCompilationUnits() {
       // CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getMyBatisSqlMapNamespace());
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        
        //commentGenerator.addJavaFileComment(interfaze);

        /*String rootInterface = introspectedTable
            .getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (!stringHasValue(rootInterface)) {
            rootInterface = context.getJavaClientGeneratorConfiguration()
                .getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(
                    rootInterface);
            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
        }*/
        
        

        for(String id : ids){
        	MappedStatement mst = 	getConfiguration().getMappedStatement(id);
        	switch (mst.getSqlCommandType()) {
			case SELECT:{
				select(getConfiguration(), mst,interfaze);
			   }
			   break;
			case UPDATE:{
				update(getConfiguration(), mst,interfaze);
			   }
			   break;

			default:
				break;
			}
        	
        }

        return interfaze;
    }

	private void update(Configuration configuration, MappedStatement mst, Interface interfaze) {
		UpdateMethodGenBuilder update = new UpdateMethodGenBuilder(configuration, mst, introspectedTable);
		update.addInterfaceElements(interfaze);
		
	}

	private void select(Configuration configuration, MappedStatement mst, Interface interfaze) {
		SelectMethodGenBuilder  select = new SelectMethodGenBuilder(configuration, mst,introspectedTable);
		select.addInterfaceElements(interfaze);
	}



}
