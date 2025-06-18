package com.javaweb.mystiacanteen.entity;

import lombok.Data;

@Data
public class Cart {
    private Integer cartId;
    private String username;
    private String name;//商品名
    private Integer num;//商品数量
    private String type;//类型
}
