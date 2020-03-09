package com.playmaker.shop.service;

import com.playmaker.shop.entity.User;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.service
 * @ClassName: UserService
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/2/6 20:18
 * @Version: 1.0
 */
public interface UserService {


    //检查用户名
    User checkUser(User user);
}
