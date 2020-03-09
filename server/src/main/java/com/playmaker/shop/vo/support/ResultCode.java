package com.playmaker.shop.vo.support;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.vo.support
 * @ClassName: ResultCode
 * @Author: shangqianzhushang
 * @Description:用户状态码
 * @Date: 2020/3/9 1:29
 * @Version: 1.0
 */
public enum ResultCode {
    /*未知错误*/
    NOT_ERROR(-1,"未知错误"),
    /*成功*/
    SUCCESS(1,"成功"),
    /*参数错误 1001-1999*/
    PARAM_IS_INVALID(1001,"参数无效"),
    PARAM_IS_BLANK(1002,"参数为空"),
    PARAM_TYPE_BIND_ERROR(1003,"参数类型错误"),
    PARAM_NOT_COMPLETE(1004,"参数缺失"),
    /*用户错误 2001-2999*/
    USER_NOT_LOGGED_IN(2001,"用户未登录,访问的路径需要验证,请登录"),
    USER_LOGIN_ERROR(2002,"账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(2003,"账号已被禁用"),
    USER_NOT_EXIST(2004,"用户不存在"),
    USER_HAS_EXISTED(2005,"用户已存在"),
    USER_PASSWORD_ERROR(2006,"用户密码错误");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
