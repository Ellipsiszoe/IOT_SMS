package com.example.iotdemo.mapper;

import com.example.iotdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository将类标识为bean
@Repository
@Mapper
public interface UserMapper {
    //向数据库中插入用户数据
    Integer insert(User user);
    //根据用户名查找用户
    User findByUsername(String username);
    //获取用户列表
    List<User> findAllUser();
    //修改权限
    void changeRole(@Param("id") String id, @Param("role") String role);
    //重置密码
    void resetPassword(@Param("id") String id, @Param("password") String password, @Param("salt") String salt);
}
