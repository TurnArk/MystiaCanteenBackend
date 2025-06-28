package com.javaweb.mystiacanteen.controller;

import com.javaweb.mystiacanteen.entity.CartData;
import com.javaweb.mystiacanteen.entity.Production;
import com.javaweb.mystiacanteen.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;

    @GetMapping("/getAllProduct")
    public List<Production> getAllProduct()
    {
        return redisService.getAllProduct();
    }

    @PostMapping("/renew")
    public String renew(String username, CartData cartData)
    {
        return redisService.updateProduct(username, cartData) ? "更新成功" : "更新失败";
    }

    @PostMapping("/delete")
    public String delete(String username, CartData cartData){
        return redisService.deleteProduct(username, cartData) ? "删除成功" : "删除失败";
    }
}
