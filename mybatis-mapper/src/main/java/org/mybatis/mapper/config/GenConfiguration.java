package org.mybatis.mapper.config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.mapper.scripting.XmlGenLanguageDriver;

public class GenConfiguration extends Configuration{
	
	
	
	public GenConfiguration(){
		super();
		typeAliasRegistry.registerAlias("GenScript", XmlGenLanguageDriver.class);
	}

}
