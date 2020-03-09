package com.playmaker.shop.exception;

import com.playmaker.shop.vo.support.ResultCode;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.exception
 * @ClassName: WebRuntimeException
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/3/9 11:07
 * @Version: 1.0
 */
public class WebException extends RuntimeException {

    private Integer code;

    public WebException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
