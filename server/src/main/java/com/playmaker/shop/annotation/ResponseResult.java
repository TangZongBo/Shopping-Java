package com.playmaker.shop.annotation;

import java.lang.annotation.*;


/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.annotation
 * @ClassName: ResponseResult
 * @Author: shangqianzhushang
 * @Description:自定义返回值注解类 用来标记方法的返回值 是否需要包装
 * @Date: 2020/2/23 16:02
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME) //注解的注解 元注解
@Target({ElementType.TYPE, ElementType.METHOD})//方法
@Documented
public @interface ResponseResult {
}
