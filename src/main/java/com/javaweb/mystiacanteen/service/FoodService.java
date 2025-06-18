package com.javaweb.mystiacanteen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.mystiacanteen.entity.Food;
import com.javaweb.mystiacanteen.entity.Page;

import java.util.List;

public interface FoodService extends IService<Food> {
    public List<Food> getAllFood(Page page);
    public List<Food> getFoodByFoodName(String foodName);
    public void insertFood();
    public Food getFood(String name);
}
