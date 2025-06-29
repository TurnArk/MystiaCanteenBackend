package com.javaweb.mystiacanteen.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
            Object jsonStr = redisTemplate.opsForValue().get("discountProduction");
            if(jsonStr == null){
                return "商品不存在";
            }
            ObjectMapper objectMapper = new ObjectMapper();
            if(jsonStr instanceof String){
                String json = (String) jsonStr;
                JsonNode jsonNode = objectMapper.readTree(json);
                int stock = jsonNode.has("number")?jsonNode.get("number").asInt():0;
                if(stock <= 0){
                    return "商品已售罄";
                }

                // 自减
                ((ObjectNode) jsonNode).put("number", stock - 1);
                // 序列化回 JSON
                String updateJson = objectMapper.writeValueAsString(jsonNode);
                redisTemplate.opsForValue().set("discountProduction", updateJson);

                return redisService.updateProduct(username, cartData) ?"秒杀成功！库存剩余：" + (stock - 1):"更新失败";
            }else{
                return "数据格式错误";
            }

        }catch (Exception e){
            e.printStackTrace();
            return "系统异常，请稍后再试";
        }
        finally {
            redisTemplate.delete(lockKey);
        }
    }

    @PostMapping("/delete/{username}")
    public String delete(@PathVariable String username,@RequestBody CartData cartData){
        return redisService.deleteProduct(username, cartData) ? "删除成功" : "删除失败";
    }
}
