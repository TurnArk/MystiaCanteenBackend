package com.javaweb.mystiacanteen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.mystiacanteen.entity.Order;
import com.javaweb.mystiacanteen.entity.OrderData;
import com.javaweb.mystiacanteen.entity.ProductionData;

import java.util.List;

public interface OrderService extends IService<Order> {
    public Boolean deleteOrder(String username, JsonNode message);
    public List<List<OrderData>> getOrder(String username);
    public Boolean addOrder(String username, JsonNode message);
    public Boolean buyOrder(int id);
    public Boolean retireOrder( int id);
}
