package com.javaweb.mystiacanteen.controller;

import com.javaweb.mystiacanteen.entity.Drink;
import com.javaweb.mystiacanteen.entity.Page;
import com.javaweb.mystiacanteen.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DrinkController {
    @Autowired
    DrinkService drinkService;

    @PostMapping("/drink")
    public List<Drink> getAllDrinks(@RequestBody Page page) {
        return drinkService.getAllDrink(page);
    }

    @GetMapping("/putDrink")
    public void putDrink() {
        drinkService.insertDrink();
    }

    @GetMapping("/getDrink/{name}")
    public Drink getDrink(@PathVariable String name) {
        return drinkService.getDrink(name);
    }
}
