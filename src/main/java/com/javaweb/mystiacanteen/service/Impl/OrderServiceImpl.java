package com.javaweb.mystiacanteen.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.mystiacanteen.entity.*;
import com.javaweb.mystiacanteen.mapper.*;
import com.javaweb.mystiacanteen.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DrinkMapper drinkMapper;
    @Autowired
    private FoodMapper foodMapper;
    @Autowired
    private KitchenwareMapper kitchenwareMapper;

    @Override
    public List<List<OrderData>> getOrder(String username){
        List<Order> list = orderMapper.findByUsername(username);
        List<List<OrderData>> result = new ArrayList<>();
        System.out.println("orderlist"+list);

        for(Order order : list){
            System.out.println(order);
            List<OrderData> data = new ArrayList<>();
            JsonNode item = order.getMessage();
            deleteOrder(username,item);
            System.out.println("item"+item);
// 获取 "type" 数组，并将每个元素转换为字符串
            List<String> type = new ArrayList<>();
            if (item.has("type") && item.get("type").isArray()) { // 确保 "type" 字段是数组
                for (JsonNode node : item.get("type")) {
                    type.add(node.asText()); // 转换为 String
                }
            }else{
                System.out.println("notTypes");
            }
// 获取 "name" 数组，并将每个元素转换为字符串
            List<String> name = new ArrayList<>();
            if (item.has("name") && item.get("name").isArray()) { // 确保 "name" 字段是数组
                for (JsonNode node : item.get("name")) {
                    name.add(node.asText()); // 转换为 String
                }
            }else{
                System.out.println("notName");
            }
// 获取 "num" 数组，并将每个元素转换为整数
            List<Integer> num = new ArrayList<>();
            if (item.has("num") && item.get("num").isArray()) { // 确保 "num" 字段是数组
                for (JsonNode node : item.get("num")) {
                    num.add(node.asInt()); // 转换为 Integer
                }
            }else{
                System.out.println("notNum");
            }
// 获取 "cartId" 数组，并将每个元素转换为整数
            List<Integer> cartId = new ArrayList<>();
            if (item.has("cartId") && item.get("cartId").isArray()) { // 确保 "cartId" 字段是数组
                for (JsonNode node : item.get("cartId")) {
                    cartId.add(node.asInt()); // 转换为 Integer
                }
            }else{
                System.out.println("notCartId");
            }
            for(int i=0;i<num.size();i++){
                OrderData orderData = new OrderData();
                Production production = null;
                System.out.println(num.get(i)+type.get(i)+cartId.get(i)+name.get(i));
                switch (type.get(i)) {
                    case "dish":
                        production=dishMapper.selectDishByName(name.get(i));
                        break;
                    case "drink":
                        production=drinkMapper.selectDrinkByName(name.get(i));
                        break;
                    case "food":
                        production=foodMapper.selectFoodByName(name.get(i));
                        break;
                    case "kitchenware":
                        production=kitchenwareMapper.selectKitchenwareByName(name.get(i));
                }
                orderData.setStatus(order.getStatus());
                orderData.setProduction(production);
                orderData.setNum(num.get(i));
                orderData.setCartId(cartId.get(i));
                orderData.setOrderId(order.getId());
                data.add(orderData);
            }
            result.add(data);
        }
        return result;
    }

    @Override
    public Boolean deleteOrder(String username, JsonNode message){
        long now_time=System.currentTimeMillis();
        orderMapper.clearOrder(now_time);
        return orderMapper.deleteById(orderMapper.findIdAsUser(username,message,now_time));
    }

    @Override
    public Boolean addOrder(String username, JsonNode message){
        long start_time=System.currentTimeMillis();
        long end_time=start_time+1000*60*15;
        return orderMapper.createOrder(username,message,start_time,end_time);
    }

    @Override
    public Boolean buyOrder( int id){
        long now_time=System.currentTimeMillis();
        orderMapper.clearOrder(now_time);
        return orderMapper.buyBuId(id);
    }
    @Override
    public Boolean retireOrder(int id){
        long now_time=System.currentTimeMillis();
        orderMapper.clearOrder(now_time);
        return orderMapper.deleteById(id);
    }

}
