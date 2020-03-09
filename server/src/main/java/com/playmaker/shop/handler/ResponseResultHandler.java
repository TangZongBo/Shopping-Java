package com.playmaker.shop.handler;

import com.playmaker.shop.annotation.ResponseResult;
import com.playmaker.shop.vo.ResultInfo;
import com.playmaker.shop.vo.ResultUtils;
import com.playmaker.shop.vo.support.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.handler
 * @ClassName: ResponseResultHandler
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/2/23 16:14
 * @Version: 1.0
 */
@Slf4j
@ControllerAdvice //相当于增强Controller 异常处理 数据绑定 数据预处理
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {
    //标记名称
    public static final String RESPONSE_RESULT_ANN = "RESPONSE-RESULT-ANN";

    //是否去请求 包含了 包装注解 标记 没有就直接返回,不需要重写返回体
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        //判断请求 是否有包装标记
        ResponseResult responseResultAnn = (ResponseResult) request.getAttribute(RESPONSE_RESULT_ANN);

        return responseResultAnn == null ? false : true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter retureType,
                                  MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        log.info("进入 返回体 重写格式 处理中......");
        /*System.out.println(body+":ResponseResultHandler");
        if(body instanceof ErrorResult){
            logger.info("返回值 异常 作包装 处理中...");
            ErrorResult errorResult = (ErrorResult)body;
            return Result.failure(errorResult.getCode(),errorResult.getMessage(),errorResult.getErrors());
        }*/
        if(body instanceof ResultInfo){
            return body;
        }
        return ResultUtils.success(body);
    }


}
