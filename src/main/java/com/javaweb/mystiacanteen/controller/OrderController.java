package com.javaweb.mystiacanteen.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.mystiacanteen.entity.OrderCenter;
import com.javaweb.mystiacanteen.entity.OrderData;
import com.javaweb.mystiacanteen.entity.ProductionData;
import com.javaweb.mystiacanteen.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public String addOrder(@RequestBody OrderCenter orderCenter) {
        String username = orderCenter.getUsername();
        JsonNode message=orderCenter.getMessage();
        if(orderService.addOrder(username, message)) {
            return "success";
        }
        return "fail";
    }

    @PostMapping("/deleteOrder/{orderId}")
    public String deleteOrder(@PathVariable int orderId){
        if(orderService.retireOrder(orderId)) {
            return "success";
        }
        return "fail";
    }

    @PostMapping("/getOrder")
    public List<List<OrderData>> getOrders(@RequestParam String username){
        return orderService.getOrder(username);
    }

    @PostMapping("/buyOrder/{orderId}")
    public String buyOrder(@PathVariable int orderId){
        if(orderService.buyOrder(orderId)) {
            return "success";
        }
        return "fail";
    }
}
