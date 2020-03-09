package com.playmaker.shop.config;

import com.playmaker.shop.interceptor.ResponseResultInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ProjectName: springboottest
 * @Package: com.playmaker.springboottest.config
 * @ClassName: CorsConfig
 * @Author: shangqianzhushang
 * @Description:跨域配置
 * @Date: 2020/2/1 22:40
 * @Version: 1.0
 */
@Configuration
public class CrossConfig implements WebMvcConfigurer{


    //跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");//跨域的请求头
    }


}
