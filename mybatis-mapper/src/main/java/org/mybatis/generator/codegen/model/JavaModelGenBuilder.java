package org.mybatis.generator.codegen.model;



import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.Table;
import org.mybatis.generator.codegen.mapper.AbstractJavaGenBuilder;
import org.mybatis.generator.codegen.util.JavaBeansUtils;

public class JavaModelGenBuilder extends AbstractJavaGenBuilder{

	

	public JavaModelGenBuilder(Configuration configuration, Table introspectedTable) {
		super(configuration, introspectedTable, null);
	}

	@Override
	public CompilationUnit getCompilationUnits() {
		

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getBeanNamespace());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        //commentGenerator.addJavaFileComment(topLevelClass);

        /*FullyQualifiedJavaType superClass = getSuperClass();
        if (superClass != null) {
            topLevelClass.setSuperClass(superClass);
            topLevelClass.addImportedType(superClass);
        }*/

       // commentGenerator.addModelClassComment(topLevelClass, introspectedTable);
        
       /* List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();

        if (introspectedTable.isConstructorBased()) {
            addParameterizedConstructor(topLevelClass);

            if (!introspectedTable.isImmutable()) {
                addDefaultConstructor(topLevelClass);
            }
        }

        String rootClass = getRootClass();*/
        for (IntrospectedColumn introspectedColumn : introspectedTable.getColumns()) {
            /*if (RootClassInfo.getInstance(rootClass, warnings)
                    .containsProperty(introspectedColumn)) {
                continue;
            }*/

			Field field = JavaBeansUtils.getJavaBeansField(introspectedColumn, introspectedTable);

			topLevelClass.addField(field);
			topLevelClass.addImportedType(field.getType());

			Method getMethod = JavaBeansUtils.getJavaBeansGetter(introspectedColumn, introspectedTable);

			topLevelClass.addMethod(getMethod);

			Method setMethod = JavaBeansUtils.getJavaBeansSetter(introspectedColumn, introspectedTable);

			topLevelClass.addMethod(setMethod);
            
        }

		return topLevelClass;
	}

}
