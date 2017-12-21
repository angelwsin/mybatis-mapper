package org.mybatis.mapper.xml;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.codegen.Table;
import org.mybatis.generator.codegen.mapper.JavaMapperGenBuilder;
import org.mybatis.generator.codegen.model.JavaModelGenBuilder;
import org.mybatis.generator.codegen.xml.mapper.XMLMapperGenBuilder;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapperGenBuilderAssistant extends MapperBuilderAssistant {

	private static Logger logger = LoggerFactory.getLogger(MapperGenBuilderAssistant.class);

	private String currentTableName;

	private String currentCatalog;

	private String currentSchema;

	private String currentBean;

	JavaTypeResolver javaTypeResolver;

	private List<String> ids = new ArrayList<>();

	public MapperGenBuilderAssistant(Configuration configuration, String resource) {
		super(configuration, resource);
		javaTypeResolver = new JavaTypeResolverDefaultImpl();

	}

	public String getCurrentTableName() {
		return currentTableName;
	}

	public void setCurrentTableName(String currentTableName) {
		this.currentTableName = currentTableName;
	}

	public String getCurrentCatalog() {
		return currentCatalog;
	}

	public void setCurrentCatalog(String currentCatalog) {
		this.currentCatalog = currentCatalog;
	}

	public String getCurrentSchema() {
		return currentSchema;
	}

	public void setCurrentSchema(String currentSchema) {
		this.currentSchema = currentSchema;
	}

	public String getCurrentBean() {
		return currentBean;
	}

	public void setCurrentBean(String currentBean) {
		this.currentBean = currentBean;
	}

	public void addElement(String id, String list, Class<?> parameterClass) {
		ids.add(applyCurrentNamespace(id, false));
		if (Objects.nonNull(list) && !list.equals("")) {
			try {
				parameterMapElement(Arrays.asList(list.split(",")), parameterClass, id, colums());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public List<String> getIds() {
		return ids;
	}

	private void writeFile(String project,String dirFile,String wfile, String content, String fileEncoding) throws IOException {
		//if(!file.exists()) file.createNewFile();
		File dir = new File(project+dirFile);
		if(!dir.exists()) dir.mkdirs();
		File file = new File(dir,wfile);
		FileOutputStream fos = new FileOutputStream(file, false);
		OutputStreamWriter osw;
		if (fileEncoding == null) {
			osw = new OutputStreamWriter(fos);
		} else {
			osw = new OutputStreamWriter(fos, fileEncoding);
		}

		BufferedWriter bw = new BufferedWriter(osw);
		bw.write(content);
		bw.close();
	}

	public void buildMapper() {
		String project = configuration.getVariables().getProperty("project");
		String sqlMap = configuration.getVariables().getProperty("sqlMap");
		String src = configuration.getVariables().getProperty("src");
		Table table = new Table(getCurrentTableName(), colums());
		calculatePrimaryKey(table);
		String[] mapperInterface =  splitDirFile(getCurrentNamespace());
		table.setMapperPackage(mapperInterface[0]);
		table.setMapperInterfaceName(mapperInterface[1]);
		String[] beans =  splitDirFile(getCurrentBean());
		table.setBeanPackage(beans[0]);
		table.setBeanName(beans[1]);
		table.setRuntimeTableName(currentTableName);
		table.setMyBatisSqlMapNamespace(getCurrentNamespace());
		table.setBeanNamespace(getCurrentBean());
		XMLMapperGenBuilder xmlMapperBuilder = new XMLMapperGenBuilder(configuration, table);
		xmlMapperBuilder.setIds(getIds());
		xmlMapperBuilder.setIntrospectedTable(table);
		
		try {
			writeFile(project,sqlMap,table.getBeanName() + "Mapper.xml", xmlMapperBuilder.getDocument().getFormattedContent(), "utf-8");
		} catch (IOException e) {
			logger.error("xmlmapper fail", e);
		}

		JavaMapperGenBuilder javaMapper = new JavaMapperGenBuilder(configuration, ids, table);
		String map = table.getMapperPackage().replace('.', '/');
		try {
			writeFile(project,src+map,table.getMapperInterfaceName()+".java",javaMapper.getCompilationUnits().getFormattedContent(), "utf-8");
		} catch (IOException e) {
			logger.error("mapper fail", e);
		}

		JavaModelGenBuilder javaModel = new JavaModelGenBuilder(configuration, table);
		String pack = table.getBeanPackage().replace('.', '/');
		try {
			writeFile(project,src+pack,table.getBeanName()+".java", javaModel.getCompilationUnits().getFormattedContent(), "utf-8");
		} catch (IOException e) {
			logger.error("bean fail", e);
		}
	}
	
	private String[] splitDirFile(String key){
		   int last =  key.lastIndexOf('.');
		   String fileName = key.substring(last+1);
		   String dir = key.substring(0, last);
		   return new String[]{dir,fileName};
	}

	private List<IntrospectedColumn> colums() {
		Configuration conf = getConfiguration();
		DataSource dataSource = conf.getEnvironment().getDataSource();
		List<IntrospectedColumn> answer = new ArrayList<>();
		try (Connection con = dataSource.getConnection();) {
			ResultSet rs = con.getMetaData().getColumns(null, null, getCurrentTableName(), "%"); //$NON-NLS-1$

			boolean supportsIsAutoIncrement = false;
			boolean supportsIsGeneratedColumn = false;
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			for (int i = 1; i <= colCount; i++) {
				if ("IS_AUTOINCREMENT".equals(rsmd.getColumnName(i))) { //$NON-NLS-1$
					supportsIsAutoIncrement = true;
				}
				if ("IS_GENERATEDCOLUMN".equals(rsmd.getColumnName(i))) { //$NON-NLS-1$
					supportsIsGeneratedColumn = true;
				}
			}

			while (rs.next()) {
				IntrospectedColumn introspectedColumn = new IntrospectedColumn();

				// introspectedColumn.setTableAlias(tc.getAlias());
				introspectedColumn.setJdbcType(rs.getInt("DATA_TYPE")); //$NON-NLS-1$
				introspectedColumn.setLength(rs.getInt("COLUMN_SIZE")); //$NON-NLS-1$
				introspectedColumn.setActualColumnName(rs.getString("COLUMN_NAME")); //$NON-NLS-1$
				introspectedColumn.setNullable(rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable); //$NON-NLS-1$
				introspectedColumn.setScale(rs.getInt("DECIMAL_DIGITS")); //$NON-NLS-1$
				introspectedColumn.setRemarks(rs.getString("REMARKS")); //$NON-NLS-1$
				introspectedColumn.setDefaultValue(rs.getString("COLUMN_DEF")); //$NON-NLS-1$
				if (supportsIsAutoIncrement) {
					introspectedColumn.setAutoIncrement("YES".equals(rs.getString("IS_AUTOINCREMENT"))); //$NON-NLS-1$ //$NON-NLS-2$
				}

				if (supportsIsGeneratedColumn) {
					introspectedColumn.setGeneratedColumn("YES".equals(rs.getString("IS_GENERATEDCOLUMN"))); //$NON-NLS-1$ //$NON-NLS-2$
				}

				javabean(introspectedColumn);
				answer.add(introspectedColumn);

			}
		} catch (Exception e) {
			throw new BuilderException("查询表失败");
		}
		return answer;
	}
	private void calculatePrimaryKey(Table table) {
		Configuration conf = getConfiguration();
		DataSource dataSource = conf.getEnvironment().getDataSource();
		List<String> answer = new ArrayList<>();
		try (Connection con = dataSource.getConnection();) {
			ResultSet rs = con.getMetaData().getPrimaryKeys(null, null, getCurrentTableName()); //$NON-NLS-1$
			Map<Short, String> keyColumns = new TreeMap<Short, String>();
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME"); //$NON-NLS-1$
                short keySeq = rs.getShort("KEY_SEQ"); //$NON-NLS-1$
                keyColumns.put(keySeq, columnName);
            }
            
            for (String columnName : keyColumns.values()) {
            	answer.add(columnName);
            }
			
		} catch (Exception e) {
			throw new BuilderException("查询表失败");
		}
		table.setPrimaryKeyColumns(answer);
		
    }

	private void javabean(IntrospectedColumn introspectedColumn) {
		introspectedColumn.setJavaProperty(javaProperty(introspectedColumn.getActualColumnName()));
		introspectedColumn.setFullyQualifiedJavaType(javaTypeResolver.calculateJavaType(introspectedColumn));

	}

	private String javaProperty(String colums) {
		String[] c = colums.split("_");
		StringBuilder b = new StringBuilder(c[0]);
		for (int i = 1; i < c.length; i++) {
			b.append(c[i].substring(0, 1).toUpperCase()).append(c[i].substring(1));
		}
		return b.toString();
	}

	@SuppressWarnings("unchecked")
	private void parameterMapElement(List<String> list, Class<?> parameterClass, String id,
			List<IntrospectedColumn> columns) throws Exception {
		List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
		for (IntrospectedColumn column : columns) {
			for (String c : list) {
				if (column.getActualColumnName().equals(c) || column.getJavaProperty().equals(c)) {
					String property = column.getJavaProperty();
					String javaType = column.getFullyQualifiedJavaType().getShortName();
					int jdbcType = column.getJdbcType();
					Class<?> javaTypeClass = resolveClass(javaType);
					JdbcType jdbcTypeEnum = JdbcType.forCode(jdbcType);
					Class<? extends TypeHandler<?>> typeHandlerClass = (Class<? extends TypeHandler<?>>) resolveClass(
							null);
					ParameterMapping parameterMapping = buildParameterMapping(parameterClass, property, javaTypeClass,
							jdbcTypeEnum, null, null, typeHandlerClass, null);
					parameterMappings.add(parameterMapping);
					addParameterMap(id, parameterClass, parameterMappings);
				}
			}
		}

	}

}
