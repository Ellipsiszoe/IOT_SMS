package com.example.iotdemo.service;

import com.example.iotdemo.entity.User;
import com.example.iotdemo.mapper.UserMapper;
import com.example.iotdemo.service.ex.PasswordNotMatchException;
import com.example.iotdemo.service.ex.UserNotFoundException;
import com.example.iotdemo.service.ex.UsernameDuplicatedException;
import com.example.iotdemo.service.ex.insertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

@Service//将当前类的对象交给Spring来管理，自动创建对象以及对象的维护
public class UserService{
    @Autowired
    private UserMapper userMapper;

    public void reg(User user) {
        // 根据参数user对象获取注册的用户名
        String username = user.getUsername();
        // 调用持久层的User findByUsername(String username)方法，根据用户名查询用户数据
        // 判断查询结果是否不为null
        // 是：表示用户名已被占用，则抛出UsernameDuplicateException异常
        User result = userMapper.findByUsername(username);
        if (result != null) {
            // 是：表示用户名已被占用，则抛出UsernameDuplicateException异常
            throw new UsernameDuplicatedException("尝试注册的用户名[" + username + "]已经被占用");
        }

        // 补全数据：加密后的密码
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(user.getPassword(), salt);
        user.setPassword(md5Password);
        // 补全数据：盐值
        user.setSalt(salt);
        user.setRole("user");
        // 表示用户名没有被占用，则允许注册
        // 调用持久层Integer insert(User user)方法，执行注册并获取返回值(受影响的行数)
        Integer rows = userMapper.insert(user);
        // 判断受影响的行数是否不为1
        // 是：插入数据时出现某种错误，则抛出InsertException异常    // 根据参数user对象获取注册的用户名
        //    // 调用持久层的User findByUsername(String username)方法，根据用户名查询用户数据
        //    // 判断查询结果是否不为null
        //    // 是：表示用户名已被占用，则抛出UsernameDuplicateException异常
        if (rows != 1) {
            // 是：插入数据时出现某种错误，则抛出InsertException异常
            throw new insertException("添加用户数据出现未知错误，请联系系统管理员");
        }
    }

    public User login(String username, String password) {
        // 调用userMapper的findByUsername()方法，根据参数username查询用户数据
        User result = userMapper.findByUsername(username);
        // 判断查询结果是否为null
        if (result == null) {
            // 是：抛出UserNotFoundException异常
            throw new UserNotFoundException("用户数据不存在的错误");
        }
        // 从查询结果中获取盐值
        String salt = result.getSalt();
        // 调用getMd5Password()方法，将参数password和salt结合起来进行加密
        String md5Password = getMd5Password(password, salt);
        // 判断查询结果中的密码，与以上加密得到的密码是否不一致
        if (!result.getPassword().equals(md5Password)) {
            // 是：抛出PasswordNotMatchException异常
            throw new PasswordNotMatchException("密码验证失败的错误");
        }

        // 创建新的User对象
        User user = new User();
        // 将查询结果中的uid、username封装到新的user对象中
        user.setId(result.getId());
        user.setUsername(result.getUsername());
        user.setPassword(result.getPassword());
        user.setRole(result.getRole());
        // 返回新的user对象
        return user;
    }

    private String getMd5Password(String password, String salt) {
        /*
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }


    public User findByUsername(String username){
        return userMapper.findByUsername(username);
    }

    public List<User> findAllUser(){
        return userMapper.findAllUser();
    }

}
