package com.playmaker.shop.controller;

import com.playmaker.shop.annotation.ResponseResult;
import com.playmaker.shop.entity.User;
import com.playmaker.shop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.controller
 * @ClassName: UserController
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/2/6 20:13
 * @Version: 1.0
 */
@Api("用户操作类")
@RestController
@ResponseResult//它修饰才会执行返回值修饰
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value="登陆",notes = "不能重复用户名")
    @PostMapping("/login")
    public User login(@RequestBody User user){
           return userService.login(user);
    }
}
