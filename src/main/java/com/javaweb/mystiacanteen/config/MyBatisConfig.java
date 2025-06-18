package com.javaweb.mystiacanteen.config;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.mystiacanteen.handler.JsonNodeTypeHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            configuration.getTypeHandlerRegistry().register(JsonNode.class, new JsonNodeTypeHandler());
        };
    }
}