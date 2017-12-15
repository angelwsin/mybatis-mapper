package org.mybatis.mapper.config;

import java.io.InputStream;

import org.mybatis.mapper.mybatis_mapper.AppTest;
import org.mybatis.mapper.xml.XMLGenConfigBuilder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       //SqlSessionFactoryBuilder
      // GeneratorAntTask
    	
    	/*<mapper  tableName=''>

    	 <select id  where colums param orderBy >  支持自定义

    	  


    	</mapper>*/
       //DriverManager.getConnection(url, user, password)
    	
    	try(InputStream is = App.class.getResourceAsStream("/mybatis-config.xml");) {
    		System.out.println(is==null);
    		XMLGenConfigBuilder config = new XMLGenConfigBuilder(is);
    		config.parse();
    		config.getConfiguration().getEnvironment().getDataSource();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
