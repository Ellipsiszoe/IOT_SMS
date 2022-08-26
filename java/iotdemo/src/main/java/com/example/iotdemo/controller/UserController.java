package com.example.iotdemo.controller;

import com.auth0.jwt.JWT;
import com.example.iotdemo.annotation.UserLoginToken;
import com.example.iotdemo.entity.User;
import com.example.iotdemo.service.TokenService;
import com.example.iotdemo.service.UserService;
import com.example.iotdemo.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public JsonResult<Void> login(String username, String password, HttpSession session) {
//        System.out.println(username);
//        System.out.println(password);
        // 调用业务对象的方法执行登录，并获取返回值
        User data = userService.login(username, password);
        //登录成功后，将uid和username存入到HttpSession中
        session.setAttribute("uid", data.getId());
        session.setAttribute("username", data.getUsername());
        // 将以上返回值和状态码OK封装到响应结果中并返回
        JsonResult<Void> result = new JsonResult<Void>(OK);
        String token = tokenService.getToken(data);
        result.setToken(token);
        return result;
    }

    @PostMapping("/regist")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<Void>(OK);
    }

    @UserLoginToken
    @RequestMapping("/getuser")
    public JsonResult<User> getUser(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        String username;
        username = JWT.decode(token).getAudience().get(0);
        User user = userService.findByUsername(username);
        user.setPassword(null);
        user.setSalt(null);
        return new JsonResult<User>(OK,user);
    }
    @RequestMapping("/show")
    public JsonResult<List<User>> getAllUser() {
        List<User> users = userService.findAllUser();
        for (User user : users){
            user.setPassword(null);
            user.setSalt(null);
        }
        return new JsonResult<List<User>>(OK, users);
    }
    // TODO
    @PostMapping("/changerole")
    public JsonResult<String> changeRole() {
        return new JsonResult<>(OK, "success");
    }

    @PostMapping("/reset")
    public JsonResult<String> resetPassword() {
        return new JsonResult<>(OK, "success");
    }

    @PostMapping("/delete")
    public JsonResult<String> deleteUser() {
        return new JsonResult<>(OK, "success");
    }

}
