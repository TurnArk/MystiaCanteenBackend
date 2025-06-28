package com.javaweb.mystiacanteen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.javaweb.mystiacanteen.mapper")
@EnableScheduling // 启用定时任务
public class MystiaCanteenApplication {

    public static void main(String[] args) {
        SpringApplication.run(MystiaCanteenApplication.class, args);
    }

}
