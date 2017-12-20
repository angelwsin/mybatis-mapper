package org.mybatis.mapper.scripting.xmltags;

import java.util.Objects;

import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.mybatis.mapper.config.Const;

public class NoIfGenSqlNode implements SqlNode{
	
	private final String noif;
	
	

	public NoIfGenSqlNode(String noif) {
		this.noif = noif;
	}

	@Override
	public boolean apply(DynamicContext context) {
		if(Objects.nonNull(noif)){
			context.appendSql(String.format("%s:%s$", Const.NO_IF,noif));
		}
		return false;
	}

}
