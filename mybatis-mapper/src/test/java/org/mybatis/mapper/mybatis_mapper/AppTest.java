package org.mybatis.mapper.mybatis_mapper;

import java.io.InputStream;

import org.junit.Test;
import org.mybatis.mapper.xml.XMLGenConfigBuilder;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	
    //@Test	
 	public void config(){
    	try(InputStream is = AppTest.class.getResourceAsStream("mybatis-config.xml");) {
    		XMLGenConfigBuilder config = new XMLGenConfigBuilder(is);
    		config.getConfiguration().getEnvironment().getDataSource();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}
 	
 	public void genJavaBean(){
 		
 	}
}
