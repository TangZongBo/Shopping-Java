package com.playmaker.shop.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.entity
 * @ClassName: Order
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/2/23 15:54
 * @Version: 1.0
 */
public class Order {
    private Integer id;
    @NotNull(message = "名字不能为空")
    private String name;
    @NotNull(message = "描述不能为空")
    private String message;

    public Order(Integer id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
