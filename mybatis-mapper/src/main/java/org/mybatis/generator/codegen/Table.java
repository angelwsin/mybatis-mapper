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
    private String beanName;
    
    private String mapperInterfaceName;
    
    /** The domain object sub package. */
    private String beanPackage;
    
    private String mapperPackage;

    /** The alias. */
    private String alias;
    
    private String myBatisSqlMapNamespace;
    
    private String beanNamespace;
    
    
    
    
    public String getBeanNamespace() {
		return beanNamespace;
	}


	public void setBeanNamespace(String beanNamespace) {
		this.beanNamespace = beanNamespace;
	}


	public String getBeanPackage() {
		return beanPackage;
	}


	public void setBeanPackage(String beanPackage) {
		this.beanPackage = beanPackage;
	}


	public String getMapperPackage() {
		return mapperPackage;
	}


	public void setMapperPackage(String mapperPackage) {
		this.mapperPackage = mapperPackage;
	}


	public String getBeanName() {
		return beanName;
	}


	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}


	public String getMapperInterfaceName() {
		return mapperInterfaceName;
	}


	public void setMapperInterfaceName(String mapperInterfaceName) {
		this.mapperInterfaceName = mapperInterfaceName;
	}

	private List<IntrospectedColumn> columns;
	
	protected List<String> primaryKeyColumns;


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


	public String getMyBatisJavaMapperType() {
		return className()+"Mapper";
	}
	
	public String  className(){
		String[] c =  runtimeTableName.split("_");
		StringBuilder b = new StringBuilder();
		for(int i=0;i<c.length;i++){
			b.append(c[i].substring(0, 1).toUpperCase()).append(c[i].substring(1));
		}
		return b.toString();
	 }


	public List<String> getPrimaryKeyColumns() {
		return primaryKeyColumns;
	}


	public void setPrimaryKeyColumns(List<String> primaryKeyColumns) {
		this.primaryKeyColumns = primaryKeyColumns;
	}


	
    public IntrospectedColumn getGeneratedKey(){
    	for(String key : primaryKeyColumns){
    		 for(IntrospectedColumn column : columns){
    			 if(key.equals(column.getActualColumnName())&&column.isAutoIncrement()){
    				 return column;
    			 }
    		 }
    	}
    	return null;
    }

	
	
    
    
    

}
