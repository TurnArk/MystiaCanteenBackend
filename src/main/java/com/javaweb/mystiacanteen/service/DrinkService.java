package com.javaweb.mystiacanteen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.databind.JsonNode;
import com.javaweb.mystiacanteen.entity.Drink;
import com.javaweb.mystiacanteen.entity.Page;

import java.util.List;

public interface DrinkService extends IService<Drink> {
    public List<Drink> getAllDrink(Page page);
    public List<Drink> getDrinkByName(String name);
    public void insertDrink();
    public Drink getDrink(String name);
}
