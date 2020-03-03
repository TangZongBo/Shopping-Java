package com.playmaker.shop.dto;



import java.io.Serializable;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.dto
 * @ClassName: Result
 * @Author: shangqianzhushang
 * @Description:返回数据
 * @Date: 2020/2/23 15:09
 * @Version: 1.0
 */
public class Result implements Serializable {


    private ResultCode resultCode;
    //返回的数据
    private Object data;

    public Result() {

    }

    public Result(ResultCode resultCode, Object data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    //返回成功
    public static ResultCode success(){
        ResultCode resultCode = new ResultCode(200,"成功");
        //result.setResultCode(ResultCode.SUCCESS);
        return resultCode;
    }
    //返回成功
    public static Result success(Object data){
        Result result = new Result(new ResultCode(200,"成功"),data);
        return result;
    }

    //返回失败
    public static ErrorResult failure(Integer code,String message,Class clazz){
        ErrorResult errorResult = new ErrorResult(new ResultCode(code,message),clazz);
        return errorResult;
    }


    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
