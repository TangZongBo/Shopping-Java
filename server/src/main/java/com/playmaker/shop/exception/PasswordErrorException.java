package com.playmaker.shop.exception;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.exception
 * @ClassName: PasswordErrorException
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/2/24 17:12
 * @Version: 1.0
 */
public class PasswordErrorException extends RuntimeException {
    public PasswordErrorException(String message) {
        super(message);
    }
}
