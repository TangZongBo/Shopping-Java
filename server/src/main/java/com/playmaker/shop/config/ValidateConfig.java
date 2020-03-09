package com.playmaker.shop.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.config
 * @ClassName: ValidateConfig
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/3/7 2:10
 * @Version: 1.0
 */
@Configuration
public class ValidateConfig {

    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                //开启快速校验--默认校验所有参数，false校验全部
                .addProperty( "hibernate.validator.fail_fast", "true" )
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator;

    }
}
