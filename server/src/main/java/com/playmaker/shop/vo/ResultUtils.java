package com.playmaker.shop.vo;



import com.playmaker.shop.vo.support.ResultCode;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.vo
 * @ClassName: Result
 * @Author: shangqianzhushang
 * @Description:返回数据
 * @Date: 2020/2/23 15:09
 * @Version: 1.0
 */
public class ResultUtils {
    /**
     *
     * @param data 返回的数据
     * @return
     */
     public static ResultInfo success(Object data){
             ResultInfo resultInfo = new ResultInfo();
             resultInfo.setData(data);
             resultInfo.setCode(ResultCode.SUCCESS.getCode());
             resultInfo.setMessage(ResultCode.SUCCESS.getMessage());
             return resultInfo;
     }

     public static ResultInfo failure(ResultCode resultCode){
            ResultInfo resultInfo = new ResultInfo();
            resultInfo.setCode(resultCode.getCode());
            resultInfo.setMessage(resultCode.getMessage());
            return resultInfo;
     }

    public static ResultInfo failure(Integer code,String message){
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(code);
        resultInfo.setMessage(message);
        return resultInfo;
    }

}
