package com.javaweb.mystiacanteen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.mystiacanteen.entity.Dish;
import com.javaweb.mystiacanteen.entity.Page;

import java.util.List;

public interface DishService extends IService<Dish> {
    public List<Dish> getDishByName(String name);
    public List<Dish> getAllDish(Page page);
    public void insertDish();
    public Dish getDish(String name);
}
