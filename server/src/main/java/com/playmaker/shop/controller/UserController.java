package com.playmaker.shop.controller;

import com.playmaker.shop.annotation.ResponseResult;
import com.playmaker.shop.vo.ResultUtils;
import com.playmaker.shop.vo.ResultInfo;
import com.playmaker.shop.entity.User;
import com.playmaker.shop.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    /*@ApiOperation(value="登陆",notes = "不能重复用户名")
    @PostMapping("/login")
    public ResultInfo login(@RequestBody @Valid User user){
        User newuser = userService.checkUserName(user.getUsername());
        if(newuser==null){
            return ResultUtils.failure(500,"用户名不存在!");
        }

        if(!newuser.getPassword().equals(user.getPassword())){
            return ResultUtils.failure(500,"用户名密码错误!");
        }
        return ResultUtils.success(newuser);
    }*/

    @ApiOperation(value="登陆",notes = "不能重复用户名")
    @PostMapping("/login")
    public User login(@RequestBody @Valid User user){
        return userService.checkUser(user);
    }
}
