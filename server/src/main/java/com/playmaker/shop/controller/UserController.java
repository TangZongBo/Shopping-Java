package com.playmaker.shop.controller;

import com.playmaker.shop.entity.User;
import com.playmaker.shop.service.UserService;
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
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody User user){
           return userService.login(user);
    }
}
