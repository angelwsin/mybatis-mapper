package org.mybatis.mapper.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSessionManager;

import com.mybatis.mapper.UserMapper;
import com.mybatis.model.User;

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
    		/*XMLGenConfigBuilder config = new XMLGenConfigBuilder(is);
    		config.parse();*/
    		SqlSessionManager mange = SqlSessionManager.newInstance(is);
    		UserMapper userMapper = mange.getMapper(UserMapper.class);
    		userMapper.delete(2);
    		User user = new User();
    		user.setId(1);
    		user.setUserName("zhangsan");
    		userMapper.insert(user);
    		User user1 = userMapper.findById(3);
    		user1.setUserName("zgha");
    		userMapper.update(user1);
    		List<User> list = userMapper.findList(new HashMap<>());
    		System.out.println(list.get(0).getUserName());
    		System.out.println(user1.getUserName());
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	//Document document = 
    }
}
