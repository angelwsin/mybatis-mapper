package org.mybatis.mapper.config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.mapper.scripting.XmlGenLanguageDriver;

public class GenConfiguration extends Configuration{
	
	private Context  context;
	
	public GenConfiguration(){
		super();
		typeAliasRegistry.registerAlias("GenScript", XmlGenLanguageDriver.class);
		                                                                                    
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

    	
	

}
