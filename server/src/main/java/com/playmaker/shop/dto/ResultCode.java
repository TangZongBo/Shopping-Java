package com.playmaker.shop.dto;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.dto
 * @ClassName: ResultCode
 * @Author: shangqianzhushang
 * @Description:状态码,描述
 * @Date: 2020/2/23 14:57
 * @Version: 1.0
 */
public class ResultCode {

    private Integer code;

    private String message;

    public ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultCode() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
