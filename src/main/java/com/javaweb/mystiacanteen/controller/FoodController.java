package com.javaweb.mystiacanteen.controller;

import com.javaweb.mystiacanteen.entity.Food;
import com.javaweb.mystiacanteen.entity.Page;
import com.javaweb.mystiacanteen.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodController {
    @Autowired
    private FoodService foodService;

    @PostMapping("/food")
    public List<Food> getAllFood(@RequestBody Page page) {
        return foodService.getAllFood(page);
    }

    @GetMapping("/putFood")
    public void putFood() {
        foodService.insertFood();
    }

    @GetMapping("/getFood/{name}")
    public Food getFood(@PathVariable String name) {
        return foodService.getFood(name);
    }
}
