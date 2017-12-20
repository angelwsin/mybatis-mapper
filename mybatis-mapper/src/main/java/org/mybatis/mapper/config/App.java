package org.mybatis.mapper.config;

import java.io.InputStream;

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
      //GeneratorAntTask
    	
    	/*<mapper  tableName=''>

    	 <select id  where colums param orderBy >  支持自定义

    	  


    	</mapper>*/
       //DriverManager.getConnection(url, user, password)
    	try(InputStream is = App.class.getResourceAsStream("/mybatis-config.xml");) {
    		XMLGenConfigBuilder config = new XMLGenConfigBuilder(is);
    		config.parse();
    		/*SqlSessionManager mange = SqlSessionManager.newInstance(is);
    		UserMapper userMapper = mange.getMapper(UserMapper.class);
    		userMapper.findById(2);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	//Document document = 
    }
}
