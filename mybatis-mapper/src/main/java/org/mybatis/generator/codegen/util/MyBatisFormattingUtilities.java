package org.mybatis.generator.codegen.util;

import org.mybatis.generator.api.IntrospectedColumn;

public class MyBatisFormattingUtilities {
	
	
	 public static String getParameterClause(
	            IntrospectedColumn introspectedColumn) {
	        return getParameterClause(introspectedColumn, null);
	    }
	
	public static String getParameterClause(
            IntrospectedColumn introspectedColumn, String prefix) {
        StringBuilder sb = new StringBuilder();

        sb.append("#{"); //$NON-NLS-1$
        sb.append(introspectedColumn.getJavaProperty(prefix));
        sb.append('}');

        return sb.toString();
    }
	
	

}
