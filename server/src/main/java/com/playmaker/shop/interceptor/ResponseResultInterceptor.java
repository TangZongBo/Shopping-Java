package com.playmaker.shop.interceptor;

import com.playmaker.shop.annotation.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.interceptor
 * @ClassName: ResponseResultInterceptor
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/2/23 16:06
 * @Version: 1.0
 */
@Slf4j  //用它加上安装了L...就不用每个类写Logger创建
public class ResponseResultInterceptor implements HandlerInterceptor {
    //标记名称
    public static final String RESPONSE_RESULT_ANN = "RESPONSE-RESULT-ANN";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //请求方法
        if(handler instanceof HandlerMethod){
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            //判断是否在类对象上面加了注解
            if(clazz.isAnnotationPresent(ResponseResult.class)){
                //设置此请求返回体，需要包装，往下传递，在ResponseBodyAdvice接口进行判断
                request.setAttribute(RESPONSE_RESULT_ANN,clazz.getAnnotation(ResponseResult.class));
            }else if(method.isAnnotationPresent(ResponseResult.class)){//判断方法体上是否有注解
                //设置此请求返回体，需要包装，往下传递，在ResponseBodyAdvice接口进行判断
                request.setAttribute(RESPONSE_RESULT_ANN,method.getAnnotation(ResponseResult.class));

            }
        }
        return true;
    }
}
