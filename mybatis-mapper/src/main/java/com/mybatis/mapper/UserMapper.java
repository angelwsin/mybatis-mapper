package com.mybatis.mapper;

import com.mybatis.model.User;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findById(@Param("id") Integer id);

    List<User> findList(Map<String, Object> user);

    int update(@Param("id") Integer id);
}