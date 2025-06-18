package com.javaweb.mystiacanteen.controller;

import com.javaweb.mystiacanteen.entity.Dish;
import com.javaweb.mystiacanteen.entity.Page;
import com.javaweb.mystiacanteen.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping("/dish")
    public List<Dish> getAllDish(@RequestBody Page page) {
        return dishService.getAllDish(page);
    }

    @GetMapping("/putDish")
    public void putDish() {
        dishService.insertDish();
    }

    @GetMapping("/getDish/{name}")
    public Dish getDish(@PathVariable String name) {
        return dishService.getDish(name);
    }
}
