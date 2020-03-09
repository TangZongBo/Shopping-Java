package com.playmaker.shop.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.entity
 * @ClassName: User
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/2/6 20:14
 * @Version: 1.0
 */
@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;

}
