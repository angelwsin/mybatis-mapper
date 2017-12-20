package org.mybatis.mapper.scripting.xmltags;

import java.util.Objects;

import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.mybatis.mapper.config.Const;

public class WhereGenSqlNode implements SqlNode{
	
	private final String whereColums ;
	
	

	public WhereGenSqlNode(String whereColums) {
		this.whereColums = whereColums;
	}



	@Override
	public boolean apply(DynamicContext context) {
		if(Objects.nonNull(whereColums)){
			context.appendSql(String.format("%s:%s$", Const.WHERE_COLUMS,whereColums));
		}
		return false;
	}

}
