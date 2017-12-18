package org.mybatis.mapper.xml;


import java.util.List;
import java.util.Locale;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.xml.XMLIncludeTransformer;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.mybatis.mapper.config.Const;

public class XMLGenStatementBuilder extends BaseBuilder {

	  private final MapperGenBuilderAssistant builderAssistant;
	  private final XNode context;
	  private final String requiredDatabaseId;

	  public XMLGenStatementBuilder(Configuration configuration, MapperGenBuilderAssistant builderAssistant, XNode context) {
	    this(configuration, builderAssistant, context, null);
	  }

	  public XMLGenStatementBuilder(Configuration configuration, MapperGenBuilderAssistant builderAssistant, XNode context, String databaseId) {
	    super(configuration);
	    this.builderAssistant = builderAssistant;
	    this.context = context;
	    this.requiredDatabaseId = databaseId;
	  }
      //where colums param orderBy
	  public void parseStatementNode() {
	    String id = context.getStringAttribute("id");
	    String databaseId = context.getStringAttribute("databaseId");

	    if (!databaseIdMatchesCurrent(id, databaseId, this.requiredDatabaseId)) {
	      return;
	    }

	    String parameterType = context.getStringAttribute("param");
	    String lang = "GenScript";
	    LanguageDriver langDriver = getLanguageDriver(lang);
        if("object".equalsIgnoreCase(parameterType)){
        	//对象
        }
	    Class<?> parameterTypeClass = resolveClass(parameterType);
	    String result = context.getStringAttribute("result");
	    StatementType statementType = StatementType.valueOf(context.getStringAttribute("statementType", StatementType.PREPARED.toString()));
	    Class<?> resultTypeClass = resolveClass(result);

	    String nodeName = context.getNode().getNodeName();
	    SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));

	    // Include Fragments before parsing
	    XMLIncludeTransformer includeParser = new XMLIncludeTransformer(configuration, builderAssistant);
	    includeParser.applyIncludes(context.getNode());

	    // Parse selectKey after includes and remove them.
	    //processSelectKeyNodes(id, parameterTypeClass, langDriver);
	    
	    // Parse the SQL (pre: <selectKey> and <include> were parsed and removed)
	    SqlSource sqlSource = langDriver.createSqlSource(configuration, context, parameterTypeClass);
	    String resultSets = context.getStringAttribute("resultSets");
	    String keyProperty = context.getStringAttribute("keyProperty");
	    String keyColumn = context.getStringAttribute("keyColumn");
	    KeyGenerator keyGenerator;
	    String keyStatementId = id + SelectKeyGenerator.SELECT_KEY_SUFFIX;
	    keyStatementId = builderAssistant.applyCurrentNamespace(keyStatementId, true);
	    if (configuration.hasKeyGenerator(keyStatementId)) {
	      keyGenerator = configuration.getKeyGenerator(keyStatementId);
	    } else {
	      keyGenerator = context.getBooleanAttribute("useGeneratedKeys",
	          configuration.isUseGeneratedKeys() && SqlCommandType.INSERT.equals(sqlCommandType))
	          ? Jdbc3KeyGenerator.INSTANCE : NoKeyGenerator.INSTANCE;
	    }
	    builderAssistant.addMappedStatement(id, sqlSource, statementType, sqlCommandType,
	        0, 0, null, parameterTypeClass, null, resultTypeClass,
	        null, false, false, false, 
	        keyGenerator, keyProperty, keyColumn, databaseId, langDriver, resultSets);
	    builderAssistant.addElement(id);
	  }

	  private void processSelectKeyNodes(String id, Class<?> parameterTypeClass, LanguageDriver langDriver) {
	    List<XNode> selectKeyNodes = context.evalNodes("selectKey");
	    if (configuration.getDatabaseId() != null) {
	      parseSelectKeyNodes(id, selectKeyNodes, parameterTypeClass, langDriver, configuration.getDatabaseId());
	    }
	    parseSelectKeyNodes(id, selectKeyNodes, parameterTypeClass, langDriver, null);
	    removeSelectKeyNodes(selectKeyNodes);
	  }

	  private void parseSelectKeyNodes(String parentId, List<XNode> list, Class<?> parameterTypeClass, LanguageDriver langDriver, String skRequiredDatabaseId) {
	    for (XNode nodeToHandle : list) {
	      String id = parentId + SelectKeyGenerator.SELECT_KEY_SUFFIX;
	      String databaseId = nodeToHandle.getStringAttribute("databaseId");
	      if (databaseIdMatchesCurrent(id, databaseId, skRequiredDatabaseId)) {
	        parseSelectKeyNode(id, nodeToHandle, parameterTypeClass, langDriver, databaseId);
	      }
	    }
	  }

	  private void parseSelectKeyNode(String id, XNode nodeToHandle, Class<?> parameterTypeClass, LanguageDriver langDriver, String databaseId) {
	    String resultType = nodeToHandle.getStringAttribute("resultType");
	    Class<?> resultTypeClass = resolveClass(resultType);
	    StatementType statementType = StatementType.valueOf(nodeToHandle.getStringAttribute("statementType", StatementType.PREPARED.toString()));
	    String keyProperty = nodeToHandle.getStringAttribute("keyProperty");
	    String keyColumn = nodeToHandle.getStringAttribute("keyColumn");
	    boolean executeBefore = "BEFORE".equals(nodeToHandle.getStringAttribute("order", "AFTER"));

	    //defaults
	    boolean useCache = false;
	    boolean resultOrdered = false;
	    KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
	    Integer fetchSize = null;
	    Integer timeout = null;
	    boolean flushCache = false;
	    String parameterMap = null;
	    String resultMap = null;
	    ResultSetType resultSetTypeEnum = null;

	    SqlSource sqlSource = langDriver.createSqlSource(configuration, nodeToHandle, parameterTypeClass);
	    SqlCommandType sqlCommandType = SqlCommandType.SELECT;

	    builderAssistant.addMappedStatement(id, sqlSource, statementType, sqlCommandType,
	        fetchSize, timeout, parameterMap, parameterTypeClass, resultMap, resultTypeClass,
	        resultSetTypeEnum, flushCache, useCache, resultOrdered,
	        keyGenerator, keyProperty, keyColumn, databaseId, langDriver, null);

	    id = builderAssistant.applyCurrentNamespace(id, false);

	    MappedStatement keyStatement = configuration.getMappedStatement(id, false);
	    configuration.addKeyGenerator(id, new SelectKeyGenerator(keyStatement, executeBefore));
	  }

	  private void removeSelectKeyNodes(List<XNode> selectKeyNodes) {
	    for (XNode nodeToHandle : selectKeyNodes) {
	      nodeToHandle.getParent().getNode().removeChild(nodeToHandle.getNode());
	    }
	  }

	  private boolean databaseIdMatchesCurrent(String id, String databaseId, String requiredDatabaseId) {
	    if (requiredDatabaseId != null) {
	      if (!requiredDatabaseId.equals(databaseId)) {
	        return false;
	      }
	    } else {
	      if (databaseId != null) {
	        return false;
	      }
	      // skip this statement if there is a previous one with a not null databaseId
	      id = builderAssistant.applyCurrentNamespace(id, false);
	      if (this.configuration.hasStatement(id, false)) {
	        MappedStatement previous = this.configuration.getMappedStatement(id, false); // issue #2
	        if (previous.getDatabaseId() != null) {
	          return false;
	        }
	      }
	    }
	    return true;
	  }

	  private LanguageDriver getLanguageDriver(String lang) {
	    Class<?> langClass = null;
	    if (lang != null) {
	      langClass = resolveClass(lang);
	    }
	    return builderAssistant.getLanguageDriver(langClass);
	  }

	}