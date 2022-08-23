package com.blog.dao;

import com.blog.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    int insert(User user);



    User getUserByname(String userName);

    User findUserByNameAndPassword(@Param("userName") String userName,@Param("password") String password);

    int updatePassword(@Param("userName") String userName, @Param("password") String password);
}