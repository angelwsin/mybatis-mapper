package org.mybatis.mapper.scripting.xmltags;

import java.util.Objects;

import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.mybatis.mapper.config.Const;

public class ColumsGenSqlNode implements SqlNode{
	
	private final String colums;
	

	public ColumsGenSqlNode(String colums) {
		this.colums = colums;
	}


	@Override
	public boolean apply(DynamicContext context) {
		if(Objects.nonNull(colums)){
			context.appendSql(String.format("%s:%s", Const.COLUMS,colums));
		}
		return false;
	}

}
