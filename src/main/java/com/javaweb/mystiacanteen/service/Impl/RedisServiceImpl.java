package com.javaweb.mystiacanteen.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaweb.mystiacanteen.dto.DishDTO;
import com.javaweb.mystiacanteen.entity.CartData;
import com.javaweb.mystiacanteen.entity.Production;
import com.javaweb.mystiacanteen.exception.RedisCartParseException;
import com.javaweb.mystiacanteen.service.CartService;
import com.javaweb.mystiacanteen.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public List<Production> getAllProduct()
    {
        try{
            Object object = redisTemplate.opsForValue().get("discountProduction");
            System.out.println("拿到数据: "+object);
            if(object instanceof List){
                System.out.println("列表转换成功");
                return (List<Production>) object;
            }
            if(object instanceof Production){
                DishDTO dishDTO = objectMapper.convertValue(object, DishDTO.class);
                System.out.println("对象转换成功");
                List< Production> list = new ArrayList();
                list.add(dishDTO);
                return list;
            }
            System.out.println("转换失败");
            return null;
        } catch (RedisCartParseException e){
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
