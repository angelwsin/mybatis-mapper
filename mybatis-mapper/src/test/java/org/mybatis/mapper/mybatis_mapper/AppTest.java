package org.mybatis.mapper.mybatis_mapper;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionManager;
import org.junit.Test;

import com.mybatis.mapper.UserMapper;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	
    @Test
 	public void config(){
    	try(InputStream is = AppTest.class.getResourceAsStream("mybatis-config.xml");) {
    		SqlSessionManager mange = SqlSessionManager.newInstance(is);
    		mange.getMapper(UserMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}
 	
 	public void genJavaBean(){
 		
 	}
}
