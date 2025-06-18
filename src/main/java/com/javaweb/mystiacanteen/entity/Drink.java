package com.javaweb.mystiacanteen.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.mystiacanteen.handler.JsonNodeTypeHandler;

public class Drink extends Production{
    private JsonNode tags;//饮品标签

    @Override
    public JsonNode getTags() {
        return tags;
    }

    @Override
    public void setTags(JsonNode tags) {
        this.tags = tags;
    }
}
