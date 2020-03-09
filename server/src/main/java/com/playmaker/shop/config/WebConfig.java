package com.playmaker.shop.config;

import com.playmaker.shop.interceptor.ResponseResultInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.config
 * @ClassName: WebConfig
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/3/7 1:42
 * @Version: 1.0
 */
@Configuration
public class WebConfig  implements WebMvcConfigurer {
    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ResponseResultInterceptor()).addPathPatterns("/**");
    }
}
