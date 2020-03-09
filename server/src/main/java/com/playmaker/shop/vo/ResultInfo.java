package com.playmaker.shop.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.vo
 * @ClassName: ResultInfo
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/3/7 1:56
 * @Version: 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)  //字段为null的不显示
public class ResultInfo<T> implements Serializable {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 描述
     */
    private String message;
    /**
     * 数据
     */
    private T data;



}
