package com.javaweb.mystiacanteen.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaweb.mystiacanteen.entity.Cart;
import com.javaweb.mystiacanteen.entity.CartData;
import com.javaweb.mystiacanteen.entity.Production;
import com.javaweb.mystiacanteen.entity.ProductionData;
import com.javaweb.mystiacanteen.exception.RedisCartParseException;
import com.javaweb.mystiacanteen.mapper.*;
import com.javaweb.mystiacanteen.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DrinkMapper drinkMapper;
    @Autowired
    private FoodMapper foodMapper;
    @Autowired
    private KitchenwareMapper kitchenwareMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<ProductionData> getAllCart(String username){

        //从购物车表里选出该用户的购物车数据
        List<CartData> cartData = null;
        if(redisTemplate.opsForValue().get("cart"+username)==null)
            cartData = cartMapper.getAllProductName(username);
        else{
            try{
                Object cartDataObj = redisTemplate.opsForValue().get("cart:user123");
                ObjectMapper objectMapper = new ObjectMapper();
                if (cartDataObj instanceof String) {
                    String json = (String) cartDataObj;
                    cartData = objectMapper.readValue(json, new TypeReference<List<CartData>>() {});
                }
            }catch (JsonProcessingException e){
                // 处理 JSON 解析失败的情况
                log.error("Failed to parse cart data from Redis", e);
                throw new RedisCartParseException("Failed to parse cart data from Redis", e);
            }
        }
        List<ProductionData> productionDataList = new ArrayList<>();

        for (CartData item : cartData) {
            ProductionData productionData = new ProductionData();
            Production production=null;

            //根据类型查找对应的表，并返回该对象
            switch (item.getType()) {
                case "dish":
                    production = dishMapper.selectDishByName(item.getName());
                    break;
                case "drink":
                    production = drinkMapper.selectDrinkByName(item.getName());
                    break;
                case "food":
                    production = foodMapper.selectFoodByName(item.getName());
                    break;
                case "kitchenware":
                    production = kitchenwareMapper.selectKitchenwareByName(item.getName());
            }
            productionData.setProduction(production); //将对应商品的数据拿到并放入
            productionData.setNum(item.getNum());   //将商品数放入
            productionData.setCartId(item.getCartId());
            productionDataList.add(productionData); //加入列表
        }
        return productionDataList; //该数据最终返回到前端用于购物车数据渲染
    }

    @Override
    public Boolean RenewCart(String username, String name, int num,String type){
        Integer cartId= cartMapper.findCartId(username,name);
        if(cartId!=null){//判断是否找到cartId，找到则证明已存在该列表，修改num值即可，否则需要添加
            return cartMapper.updateNum(num,cartId);
        }
        return cartMapper.addCart(username,name,num,type);
    }

    @Override
    public Boolean DeleteCart(String username, String name){
        return cartMapper.deleteCart(username,name);
    }
}
