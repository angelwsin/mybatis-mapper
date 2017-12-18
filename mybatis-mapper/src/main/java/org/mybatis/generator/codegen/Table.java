package org.mybatis.generator.codegen;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;

public class Table {
	
	/** The introspected catalog. */
    private String introspectedCatalog;

    /** The introspected schema. */
    private String introspectedSchema;

    /** The introspected table name. */
    private String introspectedTableName;

    /** The runtime catalog. */
    private String runtimeCatalog;

    /** The runtime schema. */
    private String runtimeSchema;

    /** The runtime table name. */
    private String runtimeTableName;

    /** The domain object name. */
    private String domainObjectName;
    
    /** The domain object sub package. */
    private String domainObjectSubPackage;

    /** The alias. */
    private String alias;
    
    private String myBatisSqlMapNamespace;
    
    
    private List<IntrospectedColumn> columns;


	public Table( String runtimeTableName,
			List<IntrospectedColumn> columns) {
		super();
		this.runtimeTableName = runtimeTableName;
		this.columns = columns;
	}


	public String getRuntimeTableName() {
		return runtimeTableName;
	}


	public void setRuntimeTableName(String runtimeTableName) {
		this.runtimeTableName = runtimeTableName;
	}


	public List<IntrospectedColumn> getColumns() {
		return columns;
	}


	public void setColumns(List<IntrospectedColumn> columns) {
		this.columns = columns;
	}


	public String getMyBatisSqlMapNamespace() {
		return myBatisSqlMapNamespace;
	}


	public void setMyBatisSqlMapNamespace(String myBatisSqlMapNamespace) {
		this.myBatisSqlMapNamespace = myBatisSqlMapNamespace;
	}


	
	
    
    
    

}
