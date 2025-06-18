package com.javaweb.mystiacanteen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.javaweb.mystiacanteen.mapper")
public class MystiaCanteenApplication {

    public static void main(String[] args) {
        SpringApplication.run(MystiaCanteenApplication.class, args);
    }

}
