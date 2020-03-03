package com.playmaker.shop.exception;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.exception
 * @ClassName: UserNotExistException
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/2/24 15:34
 * @Version: 1.0
 */
public class UserNotExistException extends RuntimeException {

    public UserNotExistException(String message) {
          super(message);
    }
}
