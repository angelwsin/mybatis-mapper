package org.mybatis.mapper.scripting.xmltags;

import java.util.Objects;

import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.mybatis.mapper.config.Const;

public class TextGenSqlNode implements SqlNode{
	
	private final String text;
	
	

	public TextGenSqlNode(String text) {
		this.text = text;
	}



	@Override
	public boolean apply(DynamicContext context) {
		if(Objects.nonNull(text)){
			context.bind(Const.ORDER_BY, text);
		}
		return true;
	}

}
