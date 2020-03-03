package com.playmaker.shop.utils;

import com.playmaker.shop.exception.UserNotExistException;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.utils
 * @ClassName: ValidatorUtils
 * @Author: shangqianzhushang
 * @Description:hibernate-validator校验工具类
 * @Date: 2020/2/24 15:27
 * @Version: 1.0
 */
public class ValidatorUtils {

    /*
    验证器
     */
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static void validateEntity(Object object, Class<?>... groups) throws UserNotExistException{
        // 用验证器执行验证，返回一个违反约束的set集合
        Set<ConstraintViolation<Object>> violationSet = validator.validate(object, groups);
        // 判断是否为空，空：说明验证通过，否则就验证失败
        StringBuilder errorMessage = new StringBuilder();
        if(!violationSet.isEmpty()) {
            // 获取验证失败的属性
            int count = 0;
            for(ConstraintViolation<Object> violation:violationSet){
                errorMessage.append(violation.getMessage());
                count++;
                if(count!=violationSet.size()){
                    errorMessage.append(",");
                }
            }
        }

        if(!StringUtils.isEmpty(errorMessage.toString())){
            throw new ValidationException(errorMessage.toString());
        }

    }
}
