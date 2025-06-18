package com.javaweb.mystiacanteen.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.mystiacanteen.handler.JsonNodeTypeHandler;
import lombok.Data;

@Data
public class Order {
    private int id;
    private String username;
    private JsonNode message;//每一个Json包含String name;int num;String type;int cartId
    private long createTime;
    private long outTime;
    private int status;//状态码，0表示待支付，1表示已完成，2表示取消支付
}
