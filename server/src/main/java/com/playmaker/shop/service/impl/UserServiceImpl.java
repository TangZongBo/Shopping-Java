package com.playmaker.shop.service.impl;

import com.playmaker.shop.dao.UserRepository;
import com.playmaker.shop.entity.User;
import com.playmaker.shop.exception.WebException;
import com.playmaker.shop.service.UserService;
import com.playmaker.shop.vo.support.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.service.impl
 * @ClassName: UserServiceImpl
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/2/6 20:18
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    public User checkUser(User user){

        User newuser = userRepository.findByUsername(user.getUsername());
        if(newuser==null){
            throw new WebException(ResultCode.USER_NOT_EXIST);
        }
        if(!user.getPassword().equals(newuser.getPassword())){
            throw new WebException(ResultCode.USER_PASSWORD_ERROR);
        }
        return newuser;
    }
}
