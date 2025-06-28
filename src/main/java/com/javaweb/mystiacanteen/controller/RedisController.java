package com.javaweb.mystiacanteen.controller;

import com.javaweb.mystiacanteen.entity.CartData;
import com.javaweb.mystiacanteen.entity.Production;
import com.javaweb.mystiacanteen.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/getAllProduct")
    public List<Production> getAllProduct()
    {
        return redisService.getAllProduct();
    }

    @PostMapping("/renew/{username}")
    public String renew(@PathVariable String username,@RequestBody CartData cartData)
    {
        String limitKey = "qps:limit";
        Long count = redisTemplate.opsForValue().increment(limitKey);
        if(count == 1){
            redisTemplate.expire(limitKey, 1, TimeUnit.SECONDS);
        }
        if(count > 100){
            return "操作过于频繁";
        }
        String lockKey = "lock:id:2011";
        Boolean lock = redisTemplate.opsForValue()
                .setIfAbsent(lockKey, "1", 3, TimeUnit.SECONDS);

        if (!lock) {
            return "系统繁忙，请稍后再试";
        }
        try {
            Integer stock = (Integer) redisTemplate.opsForValue().get("id:2011");
            if (stock == null || stock <= 0) {
                return "库存不足";
            }

            Long newStock = redisTemplate.opsForValue().decrement("id:2011");
            if (newStock < 0) {
                // 回滚超减
                redisTemplate.opsForValue().increment("id:2011");
                return "库存不足";
            }
            // TODO：下单入库逻辑
            return redisService.updateProduct(username, cartData) ?"秒杀成功！库存剩余：" + newStock:"更新失败";

        } finally {
            redisTemplate.delete(lockKey);
        }
    }

    @PostMapping("/delete/{username}")
    public String delete(@PathVariable String username,@RequestBody CartData cartData){
        return redisService.deleteProduct(username, cartData) ? "删除成功" : "删除失败";
    }
}
