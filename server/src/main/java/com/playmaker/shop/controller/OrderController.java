package com.playmaker.shop.controller;

import com.playmaker.shop.annotation.ResponseResult;

import com.playmaker.shop.entity.Order;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ProjectName: Shopping-Java
 * @Package: com.playmaker.shop.controller
 * @ClassName: OrderController
 * @Author: shangqianzhushang
 * @Description:
 * @Date: 2020/2/23 15:14
 * @Version: 1.0
 */
@RestController
@RequestMapping("/orders")
@ResponseResult//被它修饰会被ResponseResult拦截器拦截处理
public class OrderController {

   /* @GetMapping("{id}")
    public Result getOrderById(@PathVariable("id") Integer id){

           Order order = new Order(id,"112","呵呵");
           return Result.success(order);
    }*/

    @GetMapping("{id}")
    public Order getOrderById(@PathVariable("id") Integer id){
        Order order = new Order(id,"112","呵呵");
        return order;
    }

    /*@PostMapping("save")
    public Result save(@RequestBody @Valid Order order){
        System.out.println(order);
        System.out.println("-------");
        return Result.success();
    }*/

    @PostMapping("save")
    public Order save(@RequestBody @Valid Order order){
        //服务器端验证
        //ValidatorUtils.validateEntity(order);

        return order;
    }
}

