package com.javaweb.mystiacanteen.task;

import com.javaweb.mystiacanteen.entity.CartData;
import com.javaweb.mystiacanteen.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class CartSyncTask {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private CartMapper cartMapper;

    @Scheduled(fixedRate = 5* 60 * 1000)//每五分钟
    public void syncCart(){
        System.out.println("开始同步购物车数据");
        Set<String> keys = redisTemplate.keys("cart_*");
        if(keys != null && keys.size() > 0){
            for(String key : keys){
                String username = key.split("_")[1];
                Object object = redisTemplate.opsForValue().get(key);
                if(object != null){
                    List<CartData> carts = (List<CartData>) object;

                    // 保存购物车数据并清除redis缓存
                    for(CartData cart : carts){
                        if(cartMapper.findCartId(username, cart.getName()) == cart.getCartId())
                            cartMapper.updateNum(cart.getNum(), cart.getCartId());
                        else
                            cartMapper.addCart(username, cart.getName(), cart.getNum(), cart.getType());
                    }
                    redisTemplate.opsForValue().getOperations().delete(key);
                }
            }
        }
        System.out.println("同步购物车数据结束");
    }
}
