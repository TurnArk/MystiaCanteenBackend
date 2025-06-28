package com.javaweb.mystiacanteen.service;

import com.javaweb.mystiacanteen.entity.CartData;
import com.javaweb.mystiacanteen.entity.Production;

import java.util.List;

public interface RedisService {
    // 获取缓存中的秒杀商品
    public List<Production> getAllProduct();

    // 将商品移除购物车
    public Boolean deleteProduct(String username, CartData cartData);

    // 将更新购物车商品信息
    public Boolean updateProduct(String username, CartData cartData);
}
