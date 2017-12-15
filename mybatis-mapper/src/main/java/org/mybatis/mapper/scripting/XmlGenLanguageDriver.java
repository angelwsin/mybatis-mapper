package org.mybatis.mapper.scripting;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.mybatis.mapper.scripting.xmltags.XMLGenScriptBuilder;

public class XmlGenLanguageDriver  extends XMLLanguageDriver {

	@Override
	  public SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType) {
		XMLGenScriptBuilder builder = new XMLGenScriptBuilder(configuration, script, parameterType);
	    return builder.parseScriptNode();
	  }

}
