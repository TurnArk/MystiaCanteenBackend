package com.javaweb.mystiacanteen.service.Impl;

import com.javaweb.mystiacanteen.entity.CartData;
import com.javaweb.mystiacanteen.entity.Production;
import com.javaweb.mystiacanteen.exception.RedisCartParseException;
import com.javaweb.mystiacanteen.service.CartService;
import com.javaweb.mystiacanteen.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private CartService cartService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public List<Production> getAllProduct()
    {
        try{
            Object object = redisTemplate.opsForValue().get("discountProduction");
            if(object instanceof List){
                return (List<Production>) object;
            }
            return null;
        }catch (RedisCartParseException e){
            log.error("从Redis解析购物车数据失败", e);
            throw new RedisCartParseException("从Redis解析购物车数据失败", e);
        }
    }

    public Boolean updateProduct(String username, CartData cartData){
        return cartService.RenewCart(username,cartData.getName(),cartData.getNum(),cartData.getType());
    }

    public Boolean deleteProduct(String username, CartData cartData){
        return cartService.DeleteCart(username,cartData.getName());
    }

}
