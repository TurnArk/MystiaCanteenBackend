package com.javaweb.mystiacanteen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 全局跨域配置
                .allowedOrigins("http://localhost:5173","http://127.20.0.3:5173")  // 允许的前端地址
                .exposedHeaders("Authorization")
                .allowedMethods("GET", "POST", "PUT", "DELETE","OPTIONS","HEAD")  // 允许的请求方法
                .allowedHeaders("*")
                .allowCredentials(true);  // 允许的请求头
    }
}
