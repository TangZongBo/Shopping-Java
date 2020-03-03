package com.playmaker.shop.dto;

import java.io.Serializable;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.dto
 * @ClassName: ErrorResult
 * @Author: shangqianzhushang
 * @Description:错误返回结果
 * @Date: 2020/2/24 10:41
 * @Version: 1.0
 */
public class ErrorResult implements Serializable {

    private ResultCode resultCode;

    private Class Error;

    public ErrorResult() {
    }

    public ErrorResult(ResultCode resultCode, Class error) {
        this.resultCode = resultCode;
        Error = error;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public Class getError() {
        return Error;
    }

    public void setError(Class error) {
        Error = error;
    }

}
