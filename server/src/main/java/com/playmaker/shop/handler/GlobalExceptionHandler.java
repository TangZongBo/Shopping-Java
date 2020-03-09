package com.playmaker.shop.handler;

import com.playmaker.shop.exception.WebException;
import com.playmaker.shop.vo.ResultUtils;
import com.playmaker.shop.vo.ResultInfo;
import com.playmaker.shop.vo.support.ResultCode;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.handler
 * @ClassName: GlobalExceptionHandler
 * @Author: shangqianzhushang
 * @Description: 全局异常处理器
 * @Date: 2020/3/7 2:26
 * @Version: 1.0
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*@ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResultInfo handleBindException(MethodArgumentNotValidException ex){
        log.error( "参数校验异常",ex);
        List<String> defaultMsg = ex.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return ResultUtils.failure(500,defaultMsg.get(0));
    }*/


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultInfo handleBindException(Exception ex){
        log.error("异常",ex);
        if(ex instanceof WebException){
            WebException webException = (WebException) ex;
            return ResultUtils.failure(webException.getCode(),webException.getMessage());
        }else if(ex instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            List<String> defaultMsg = methodArgumentNotValidException.getBindingResult().getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResultUtils.failure(500,defaultMsg.get(0));
        }else{
            return ResultUtils.failure(ResultCode.NOT_ERROR);
        }
    }
   /* @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResultBean handleException(Exception ex){
        if(ex instanceof BlogRuntimeException){
            log.error( "抛出自定义异常",ex);
        }else {
            log.error( "系统内部异常",ex);
        }
        return new ResultBean(BlogContant.FAIL,BlogContant.CODE_9999,ex.getMessage(),null);
    }*/


}
