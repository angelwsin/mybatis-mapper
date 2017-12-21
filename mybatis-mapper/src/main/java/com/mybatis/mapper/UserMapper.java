package com.mybatis.mapper;

import com.mybatis.model.User;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findById(@Param("id") Integer id);

    Integer countId();

    List<User> findList(Map<String, Object> user);

    int update(User user);

    int delete(@Param("id") Integer id);

    int insert(User user);
}