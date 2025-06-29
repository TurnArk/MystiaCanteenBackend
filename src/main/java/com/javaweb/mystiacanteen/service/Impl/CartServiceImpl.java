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
        Object cartDataObj = redisTemplate.opsForValue().get("cart_"+username);
        if(cartDataObj==null) {//购物车数据不存在缓存中则从数据库中获取
            cartData = cartMapper.getAllProductName(username);
            redisTemplate.opsForValue().set("cart_"+username,cartData);
        }else{
            try{
                ObjectMapper objectMapper = new ObjectMapper();
                if (cartDataObj instanceof String) {
                    String json = (String) cartDataObj;
                    cartData = objectMapper.readValue(json, new TypeReference<List<CartData>>() {});
                }else if(cartDataObj instanceof List){
                    cartData = (List<CartData>) cartDataObj;
                }
            }catch (JsonProcessingException e){
                // 处理 JSON 解析失败的情况
                log.error("从Redis解析购物车数据失败", e);
                throw new RedisCartParseException("从Redis解析购物车数据失败", e);
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
        Integer cartId = cartMapper.findCartId(username,name);
        Object cartDataObj = redisTemplate.opsForValue().get("cart_"+username);
        if(cartId!=null){//判断是否找到cartId，找到则证明已存在该列表，修改num值即可，否则需要添加
            if(cartDataObj!=null){
                try{
                    ObjectMapper objectMapper = new ObjectMapper();
                    if (cartDataObj instanceof String) {
                        String json = (String) cartDataObj;
                        List<CartData> cartData = objectMapper.readValue(json, new TypeReference<List<CartData>>() {});
                        for (CartData item : cartData) {
                            if (item.getName().equals(name)) {
                                item.setNum(num+item.getNum());
                            }
                        }
                        redisTemplate.opsForValue().set("cart_"+username,cartData);
                    }else if(cartDataObj instanceof List){
                        List<CartData> cartData = (List<CartData>) cartDataObj;
                        for (CartData item : cartData) {
                            if (item.getName().equals(name)) {
                                item.setNum(num+item.getNum());
                            }
                        }
                        redisTemplate.opsForValue().set("cart_"+username,cartData);
                    }
                }catch (JsonProcessingException e){
                    log.error("从Redis解析购物车数据失败", e);
                    throw new RedisCartParseException("从Redis解析购物车数据失败", e);
                }
            }
            return cartMapper.updateNum(num,cartId);
        }else{
            redisTemplate.opsForValue().getOperations().delete("cart_"+username);
            cartMapper.addCart(username,name,num,type);
            redisTemplate.opsForValue().get("cart_"+username);
            return true;
        }
    }

    @Override
    public Boolean DeleteCart(String username, String name){
        if(redisTemplate.opsForValue().get("cart_"+username)!=null){
            redisTemplate.opsForValue().getOperations().delete("cart_"+username);
            cartMapper.deleteCart(username,name);
            redisTemplate.opsForValue().get("cart_"+username);
        }
        return cartMapper.deleteCart(username,name);
    }
}
