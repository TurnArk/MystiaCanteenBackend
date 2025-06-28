package com.javaweb.mystiacanteen;

import com.javaweb.mystiacanteen.entity.Dish;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.javaweb.mystiacanteen.mapper")
@EnableScheduling // 启用定时任务
public class MystiaCanteenApplication {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MystiaCanteenApplication.class, args);
    }

    @Bean
    public ApplicationRunner RedisRunner(){
        return args->{
            Dish dish = new Dish();
            dish.setId(2011);
            dish.setName("燃尽布丁");
            dish.setDescription("一颗就调动起身上包括多巴胺和肾上腺激素等多种兴奋元素疯狂舞动的禁忌甜食。" +
                    "妖怪食用后可以疯狂舞蹈一整夜，但是兴奋过后会不由地感到“我燃尽了”");
            double buDing = 73*0.8;
            dish.setPrice((int)buDing);
            dish.setImage("/public/basic/dish.png");
            dish.setPosition("-4rem -20rem");
            dish.setClick(0);
            dish.setBuy(0);
            dish.setType("dish");
            redisTemplate.opsForValue().set("discountProduction", dish);
        };
    }

}
